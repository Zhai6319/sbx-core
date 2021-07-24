package com.sbx.core.model.exception;


import com.sbx.core.model.api.Response;
import com.sbx.core.model.base.IResultCode;

public class ValidatorException extends CustomException {
	private static final long serialVersionUID = 6112473216401021085L;

    protected String message;
    protected Integer errcode;

    public ValidatorException(IResultCode resultCode) {
        super(resultCode.getMessage());
        this.message=resultCode.getMessage();
        this.errcode=resultCode.getCode();
    }

    public ValidatorException(IResultCode resultCode, String message) {
        super(String.format(resultCode.getMessage(), message == null ? "" : message));
        this.message=String.format(resultCode.getMessage(),message);
        this.errcode=resultCode.getCode();

    }

    public ValidatorException(Integer errcode, String message) {
        super(message);
        this.message = message;
        this.errcode = errcode;
    }


    public ValidatorException(Response response){
        super(response.getMsg());
        this.errcode=response.getCode();
        this.message=response.getMsg();
    }


}
