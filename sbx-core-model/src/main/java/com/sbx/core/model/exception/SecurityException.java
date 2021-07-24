package com.sbx.core.model.exception;

import com.sbx.core.model.base.IResultCode;
import lombok.Getter;
import com.sbx.core.model.enums.EResultCode;

/**
 * <p>SecurityException class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/6
 */
public class SecurityException extends CustomException {

    private static final long serialVersionUID = -6754294996042523074L;
    @Getter
    private IResultCode iResultCode;

    public SecurityException(String message) {
        super(EResultCode.UN_AUTHORIZED,message);
        this.iResultCode = EResultCode.UN_AUTHORIZED;
    }

    public SecurityException(int errCode, String message) {
        super(errCode,message);
    }

    public SecurityException(IResultCode resultCode) {
        super(resultCode);
        this.iResultCode = resultCode;
    }

    public SecurityException(IResultCode resultCode, Throwable cause) {
        super(resultCode,cause);
        this.iResultCode = resultCode;
    }


}
