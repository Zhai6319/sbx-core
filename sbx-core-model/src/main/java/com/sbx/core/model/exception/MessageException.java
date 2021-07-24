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
public class MessageException extends CustomException {

    private static final long serialVersionUID = -6754294996042523074L;
    @Getter
    private IResultCode iResultCode;

    public MessageException(String message) {
        super(String.format(EResultCode.MESSAGE_ERROR.getMessage(),message));
        this.iResultCode = EResultCode.MESSAGE_ERROR;
    }



    public MessageException(IResultCode resultCode, Throwable cause) {
        super(cause);
        this.iResultCode = resultCode;
    }


}
