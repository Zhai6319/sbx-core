package com.sbx.core.model.api;

import com.sbx.core.model.context.AppContext;
import com.sbx.core.model.exception.CustomException;
import com.sbx.core.model.base.IResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.sbx.core.model.enums.EResultCode;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Optional;

/**
 * 统一API响应结果封装
 *
 * @author Z.jc
 */
@Getter
@Setter
@ToString
@ApiModel(description = "返回信息")
@NoArgsConstructor
public class Response<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
	private static final String DEFAULT_FAILURE_MESSAGE = "操作失败";

	@ApiModelProperty(value = "状态码", required = true)
	private int code;
	@ApiModelProperty(value = "是否成功", required = true)
	private boolean success;
	@ApiModelProperty(value = "承载数据")
	private T data;
	@ApiModelProperty(value = "返回消息", required = true)
	private String msg;
	@ApiModelProperty(value = "跟踪id", required = true)
	private String traceId;

	private Response(IResultCode resultCode) {
		this(resultCode, null, resultCode.getMessage());
	}

	private Response(IResultCode resultCode, String msg) {
		this(resultCode, null, msg);
	}

	private Response(IResultCode resultCode, T data) {
		this(resultCode, data, resultCode.getMessage());
	}

	private Response(IResultCode resultCode, T data, String msg) {
		this(resultCode.getCode(), data, msg);
	}

	private Response(int code, T data, String msg) {
		this.code = code;
		this.data = data;
		this.msg = msg;
		this.success = EResultCode.SUCCESS.getCode() == code;
		if (AppContext.getContext()!=null) {
			this.traceId = AppContext.getContext().getTraceId();
		}
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	public static boolean isSuccess(@Nullable Response<?> result) {
		return Optional.ofNullable(result)
			.map(x -> ObjectUtils.nullSafeEquals(EResultCode.SUCCESS.getCode(), x.code))
			.orElse(Boolean.FALSE);
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	public static boolean isNotSuccess(@Nullable Response<?> result) {
		return !Response.isSuccess(result);
	}

	/**
	 * 返回R
	 *
	 * @param data 数据
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> data(T data) {
		return data(data, DEFAULT_SUCCESS_MESSAGE);
	}

	/**
	 * 返回R
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> data(T data, String msg) {
		return data(EResultCode.SUCCESS.getCode(), data, msg);
	}

	/**
	 * 返回R
	 *
	 * @param code 状态码
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> data(int code, T data, String msg) {
		return new Response<>(code, data, data == null ? "空数据" : msg);
	}

	/**
	 * 返回R
	 *
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> success(String msg) {
		return new Response<>(EResultCode.SUCCESS, msg);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 业务代码
	 * @param <T>        T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> success(IResultCode resultCode) {
		return new Response<>(resultCode);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 业务代码
	 * @param msg        消息
	 * @param <T>        T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> success(IResultCode resultCode, String msg) {
		return new Response<>(resultCode, msg);
	}

	/**
	 * 返回R
	 *
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> fail(String msg) {
		return new Response<>(EResultCode.FAILURE, msg);
	}


	/**
	 * 返回R
	 *
	 * @param code 状态码
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> fail(int code, String msg) {
		return new Response<>(code, null, msg);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 业务代码
	 * @param <T>        T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> fail(IResultCode resultCode) {
		return new Response<>(resultCode);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 业务代码
	 * @param msg        消息
	 * @param <T>        T 泛型标记
	 * @return R
	 */
	public static <T> Response<T> fail(IResultCode resultCode, String msg) {
		return new Response<>(resultCode, msg);
	}

	/**
	 * 返回R
	 *
	 * @param flag 成功状态
	 * @return R
	 */
	public static <T> Response<T> status(boolean flag) {
		return flag ? success(DEFAULT_SUCCESS_MESSAGE) : fail(DEFAULT_FAILURE_MESSAGE);
	}

	/**
	 * 成功就获取数据，失败就抛出异常
	 */
	public T computeDataOrFailThrow() {
		if (!isSuccess()) {
			throw new CustomException(code, msg);
		}
		return data;
	}

}
