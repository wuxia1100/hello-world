package org.cv.sf.framework.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.cv.sf.dto.entity.MUserEntity;
import org.cv.sf.service.PermissionService;
import org.cv.sf.service.RoleService;
import org.cv.sf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 1 用户的权限在哪里存入：不是存入shiro缓存,而是每次触发校验都查询数据库？
 * 2 用户的权限在哪里校验：shiro 提供了几个校验是否有权限的方法，只需要调用？
 * 3 用户的权限校验在哪里触发：调用校验，触发？
 *
 * //SimpleAuthenticationInfo
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;


    /**
     * 1 授权校验
     * 2 触发：1 代码中通过Subject对象主动调用权限校验
     *  subject.hasRole(“admin”);subject.isPermitted(“admin”);
     *  这种方式属于在代码中需要校验权限的时候主动调用，判断返回结果来确定是否通过。
     *  2 通过注解的形式检查对用的方法请求
     *  @RequiresRoles("admin")
     *  这种方式通常用在Controller的方法上。
     *  3 页面shiro标签
     *  针对jsp等页面，可直接在页面中使用标签来来标注对应的请求。进入该页面时扫描到对应的标签进行权限校验。
     *  <shiro:hasPermission name="item:update">
     *  如果是jsp页面，在使用Shiro标签库前，首先需要在JSP引入shiro标签：
     *  <%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
     *  当加上shiro标签后，会与后台代码结合使用：需要继承AuthorizingRealm，
     * 通过protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) 方法进行业务的处理。
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        MUserEntity userEntity = userService.findByUsername(
                (String) principalCollection.getPrimaryPrincipal());
        return getSimpleAuthorizationInfo(userEntity.getId());
    }

    /**
     * shiro 登录授权
     * 1 触发：subject.login();
     * 2 查询数据库用户数据
     * 3
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        //MyAuthenticationToken myAuthenticationToken = (MyAuthenticationToken)authenticationToken;
        MUserEntity user =  userService.findByUsername(username);
        if(user != null){

            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,user.getNickname(),getName());
            return authenticationInfo;
        }
        return null;
    }

    private SimpleAuthorizationInfo getSimpleAuthorizationInfo(long userId){
        SimpleAuthorizationInfo simpleInfo = new SimpleAuthorizationInfo();
        simpleInfo.addRoles(roleService.findRoleNamesByUserId(userId));
        simpleInfo.addStringPermissions(permissionService
                .findPermissionNamesByUserId(userId));
        return simpleInfo;
    }
}
