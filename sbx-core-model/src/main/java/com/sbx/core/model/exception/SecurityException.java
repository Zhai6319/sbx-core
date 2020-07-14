package com.sbx.core.model.exception;

import lombok.Getter;
import com.sbx.core.model.base.IResultCode;
import com.sbx.core.model.enums.EResultCode;

/**
 * <p>SecurityException class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/6
 */
public class SecurityException extends RuntimeException {

    private static final long serialVersionUID = -6754294996042523074L;
    @Getter
    private IResultCode iResultCode;

    public SecurityException(String message) {
        super(message);
        this.iResultCode = EResultCode.UN_AUTHORIZED;
    }

    public SecurityException(IResultCode resultCode) {
        super(resultCode.getMessage());
        this.iResultCode = resultCode;
    }

    public SecurityException(IResultCode resultCode, Throwable cause) {
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
