package org.cv.sf.framework.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.cv.sf.core.exception.run.TokenVerifyException;

import java.util.*;

public enum JwtToken {
    INSTANCE;

    //加密key
    private static final String TOKEN_KEY = "ASDFASDFSADF";
    //过期时间:秒
    private static final int EXPIRE_TIME = 30000000;

    /**
     * 获取token令牌
     */
    public String getToken(long uid){
        return createJwtToken(buildTokenParams(uid));
    }

    /**
     * 解析 token 获取参数
     * 1 获取token里claim 参数
     * 2 Optional 强制判断空值
     */
    public Optional<Map<String, Claim>> getTokenClaim(String token){
        DecodedJWT decodedJWT;
        try {
            decodedJWT = getJWTVerifier().verify(token);
        }catch (JWTVerificationException e){
            return Optional.empty();
        }
        return Optional.of(decodedJWT.getClaims());
    }

    /**
     * 核实token
     */
    public boolean verifyToken(String token){
        try {
            getJWTVerifier().verify(token);
        }catch (JWTVerificationException e){
            throw new TokenVerifyException(10003,e.getMessage());
        }
        return true;
    }

    /**
     * 组装创建token令牌所需要的参数
     */
    private Map<String,Object> buildTokenParams(long uid){
        Map<String,Object> params = new HashMap<>();
        params.put("uid",uid);

        //组装时间：使用同一个Calendar时间戳，保证时间不会因为执行时间发生偏移
        Calendar calendar = Calendar.getInstance();
        //先获取开始时间：保证增加的过期时间量，是在这个时间点开始的
        Date startTime = calendar.getTime();
        calendar.add(Calendar.SECOND,EXPIRE_TIME);
        params.put("startTime",startTime);
        params.put("expiresTime",calendar.getTime());

        return params;
    }

    /**
     * 创建token
     */
    private String createJwtToken(Map<String,Object> params){
        return JWT.create()
                //用户信息
            .withClaim("uid",(long) params.get("uid"))
                //开始时间
            .withIssuedAt((Date) params.get("startTime"))
                //过期时间
            .withExpiresAt((Date) params.get("expiresTime"))
                //加密算法
            .sign(getAlgorithm())
                ;
    }

    /**
     * Jwt: 加密算法
     * 1 使用HMAC256 算法
     * 2 key值
     */
    private Algorithm getAlgorithm(){
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_KEY);
        return algorithm;
    }

    /**
     * Jwt: 核实器
     * 1 核实token有效性
     * 2 解析token，获取参数
     */
    private JWTVerifier getJWTVerifier(){
        //加密算法
        Algorithm algorithm = getAlgorithm();
        //创建核实器
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier;
    }
}
