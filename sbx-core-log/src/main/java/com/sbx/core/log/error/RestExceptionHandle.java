package com.sbx.core.log.error;

import lombok.extern.slf4j.Slf4j;
import com.sbx.core.model.api.Response;
import com.sbx.core.model.enums.EResultCode;
import com.sbx.core.model.exception.BusinessException;
import com.sbx.core.model.exception.SecurityException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
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

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Response handleError(BusinessException e) {
		log.error("业务异常", e);
		return Response.fail(e.getIResultCode(), e.getMessage());
	}

	@ExceptionHandler(SecurityException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Response handleError(SecurityException e) {
		log.error("认证异常", e);
		return Response.fail(e.getIResultCode(), e.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Response handleError(AccessDeniedException e) {
		log.error("授权异常", e);
		return Response.fail(EResultCode.UN_AUTHORIZED, e.getMessage());
	}


	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Response handleError(Throwable e) {
		log.error("服务器异常", e);
		//发送服务异常事件
		return Response.fail(EResultCode.INTERNAL_SERVER_ERROR, (StringUtils.isEmpty(e.getMessage()) ? EResultCode.INTERNAL_SERVER_ERROR.getMessage() : e.getMessage()));
	}

}
