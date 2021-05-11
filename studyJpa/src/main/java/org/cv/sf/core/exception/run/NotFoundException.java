package org.cv.sf.core.exception.run;

public class NotFoundException extends BusinessException {
    private static String MESSAGE = "找不到数据";
    public NotFoundException(int code){
        this.code = code;
        this.message = MESSAGE;
    }
}
