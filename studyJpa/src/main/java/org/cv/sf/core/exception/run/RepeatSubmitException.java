package org.cv.sf.core.exception.run;

public class RepeatSubmitException extends BusinessException {
    private static String MESSAGE = "客官，不要急";
    public RepeatSubmitException(int code){
        this.code = code;
        this.message = MESSAGE;
    }
}
