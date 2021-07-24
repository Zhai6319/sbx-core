package com.sbx.core.log.error;

import com.sbx.core.log.util.LogUtils;
import com.sbx.core.model.exception.AuthorityException;
import lombok.extern.slf4j.Slf4j;
import com.sbx.core.model.api.Response;
import com.sbx.core.model.enums.EResultCode;
import com.sbx.core.model.exception.CustomException;
import com.sbx.core.model.exception.SecurityException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 未知异常转译和发送，方便监听，对未知异常统一处理。Order 排序优先级低
 *
 * @author Z.jc
 */
@Slf4j
@Order
@Configuration
@RestControllerAdvice
public class RestExceptionHandle {

	@ExceptionHandler(CustomException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Response handleError(CustomException e) {
		try {
			return Response.fail(e.getErrCode(), e.getMessage());
		} finally {
			LogUtils.logGlobalException(e, log);
		}

	}

	@ExceptionHandler(SecurityException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Response handleError(SecurityException e) {
		try {
			return Response.fail(e.getIResultCode(), e.getMessage());
		} finally {
			LogUtils.logGlobalException(e, log);
		}

	}

	@ExceptionHandler(AuthorityException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Response handleError(AuthorityException e) {
		try {
			return Response.fail(e.getIResultCode(), e.getMessage());
		} finally {
			LogUtils.logGlobalException(e, log);
		}

	}


	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Response handleError(Throwable e) {
		try {
			return Response.fail(EResultCode.INTERNAL_SERVER_ERROR,"服务器异常");
		} finally {
			LogUtils.logGlobalException(e, log);
		}
	}

}
