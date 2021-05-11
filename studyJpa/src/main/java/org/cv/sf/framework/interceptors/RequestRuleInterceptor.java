package org.cv.sf.framework.interceptors;

import com.auth0.jwt.interfaces.Claim;
import org.cv.sf.core.annotation.ScopeLevel;
import org.cv.sf.core.exception.run.TokenVerifyException;
import org.cv.sf.framework.util.JwtToken;
import org.cv.sf.dto.bo.LocalUser;
import org.cv.sf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * 校验jwt令牌
 */
public class RequestRuleInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从head获取jwtToken
        String token = request.getHeader("token");
        if(token == null){
            throw new TokenVerifyException(10003,"请登录");
        }

        //校验token是否合法:解析token同样校验了token，就不需要再次校验
        //JwtToken.INSTANCE.verifyToken(token);

        //解析token，获取参数
        Optional<Map<String, Claim>> claimMap = JwtToken.INSTANCE.getTokenClaim(token);
        if(claimMap.get() != null){
            Map<String, Claim> map = claimMap.get();
            long userId = map.get("uid").asLong();
            LocalUser.set(userService.findById(userId).get());
            checkScope(getScopeLevel(handler),8);
        };
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LocalUser.clear();
        super.afterCompletion(request, response, handler, ex);
    }


    private void checkScope(ScopeLevel scopeLevel,int userLevel){
        if(scopeLevel != null){
            if(userLevel < scopeLevel.scopeLevel()){
                throw new TokenVerifyException(10006,"权限不够");
            }
        }
    }

    private ScopeLevel getScopeLevel(Object handel){
        if(handel instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handel;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            return scopeLevel;
        }
        return null;
    }

}
