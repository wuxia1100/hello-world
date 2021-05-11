package org.cv.sf.core.exception.run;

public class TokenVerifyException extends BusinessException {
    public TokenVerifyException(int code,String message){
        this.code = code;
        this.message = message;
    }
}
