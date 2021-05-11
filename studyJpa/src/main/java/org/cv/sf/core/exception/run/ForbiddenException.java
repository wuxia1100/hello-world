package org.cv.sf.core.exception.run;

public class ForbiddenException extends BusinessException {
    public ForbiddenException(int code){
        this.code = code;
    }
}
