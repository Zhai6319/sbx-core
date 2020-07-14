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
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -8430217231142424338L;
    @Getter
    private IResultCode iResultCode;

    public BusinessException(String message) {
        super(message);
        this.iResultCode = EResultCode.FAILURE;
    }

    public BusinessException(IResultCode resultCode) {
        super(resultCode.getMessage());
        this.iResultCode = resultCode;
    }

    public BusinessException(IResultCode resultCode, Throwable cause) {
        super(cause);
        this.iResultCode = resultCode;
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
