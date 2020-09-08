package com.sbx.core.model.exception;

import com.sbx.core.model.base.IResultCode;
import lombok.Getter;
import com.sbx.core.model.enums.EResultCode;

/**
 * <p>BusinessException class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/9
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = -8430217231142424338L;

    @Getter
    private Integer errCode;

    @Getter
    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
        this.errCode = EResultCode.FAILURE.getCode();
    }

    public CustomException(IResultCode resultCode, String msg) {
        String message = String.format(resultCode.getMessage(),msg);
        this.errCode = resultCode.getCode();
    }

    public CustomException(IResultCode resultCode, Throwable cause) {
        super(cause);
        this.errCode = EResultCode.FAILURE.getCode();
    }


    public CustomException(IResultCode resultCode) {
        this.message = resultCode.getMessage();
        this.errCode = resultCode.getCode();
    }


    public CustomException(int errCode, String message) {
        this.message = message;
        this.errCode = errCode;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.errCode = EResultCode.FAILURE.getCode();
    }

    public CustomException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
        this.errCode = EResultCode.FAILURE.getCode();
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = cause.getMessage();
        this.errCode = EResultCode.FAILURE.getCode();
    }

    /**
     * 提高性能
     *
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }

}
