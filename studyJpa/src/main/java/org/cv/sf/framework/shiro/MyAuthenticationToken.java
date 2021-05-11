package org.cv.sf.framework.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class MyAuthenticationToken implements AuthenticationToken {
    private long userId;
    private String username;

    public MyAuthenticationToken(){}

    public MyAuthenticationToken(long userId,String username){
        this.userId = userId;
        this.username = username;
    }

    public void setUserId(long userId){
        this.userId = userId;
    }

    public long getUserId(){
        return this.userId;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }
    @Override
    public Object getPrincipal() {
        return this;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
