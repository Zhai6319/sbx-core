package com.sbx.core.model.exception;

import com.sbx.core.model.base.IResultCode;
import com.sbx.core.model.enums.EResultCode;
import lombok.Getter;

/**
 * <p>SecurityException class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/6
 */
public class AuthorityException extends CustomException {

    private static final long serialVersionUID = -6754294996042523074L;
    @Getter
    private IResultCode iResultCode;

    public AuthorityException(String message) {
        super(EResultCode.REQ_REJECT,message);
        this.iResultCode = EResultCode.REQ_REJECT;
    }

    public AuthorityException(int errCode, String message) {
        super(errCode,message);
    }

    public AuthorityException(IResultCode resultCode) {
        super(resultCode);
        this.iResultCode = resultCode;
    }

    public AuthorityException(IResultCode resultCode, Throwable cause) {
        super(resultCode,cause);
        this.iResultCode = resultCode;
    }


}
