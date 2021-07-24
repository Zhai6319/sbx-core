package com.sbx.core.model.enums;

import com.sbx.core.model.base.IResultCode;
import lombok.Getter;

import java.util.Objects;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/4/9
 */
@Getter
public enum EResultCode implements IResultCode {

    /**
     * 请求成功
     */
    SUCCESS(0,"操作成功"),
    /**
     * 业务异常
     */
    FAILURE(400, "业务异常：%s"),

    /**
     * 请求未授权
     */
    UN_AUTHORIZED(401, "请求未授权：%s"),

    /**
     * 客户端请求未授权
     */
    CLIENT_UN_AUTHORIZED(401, "客户端请求未授权：%s"),

    /**
     * 404 没找到请求
     */
    NOT_FOUND(404, "404 没找到请求：%s"),

    /**
     * 消息不能读取
     */
    MSG_NOT_READABLE(400, "消息不能读取：%s"),

    /**
     * 不支持当前请求方法
     */
    METHOD_NOT_SUPPORTED(405, "不支持当前请求方法：%s"),

    /**
     * 不支持当前媒体类型
     */
    MEDIA_TYPE_NOT_SUPPORTED(415, "不支持当前媒体类型：%s"),

    /**
     * 请求被拒绝
     */
    REQ_REJECT(403, "请求被拒绝：%s"),

    /**
     * 服务器异常
     */
    INTERNAL_SERVER_ERROR(500, "服务器异常：%s"),

    /**
     * 缺少必要的请求参数
     */
    PARAM_MISS(400, "缺少必要的请求参数：%s"),

    /**
     * 请求参数类型错误
     */
    PARAM_TYPE_ERROR(400, "请求参数类型错误：%s"),

    /**
     * 请求参数绑定错误
     */
    PARAM_BIND_ERROR(400, "请求参数绑定错误：%s"),

    /**
     * 参数校验失败
     */
    PARAM_VALID_ERROR(400, "参数校验失败：%s"),

    /**
     * 消息异常
     */
    MESSAGE_ERROR(800,"message error：%s"),

    SYSTEM_BUSY(105, "系统繁忙"),

    IP_BAN(501, "访问过于频繁"),


    ;

    private int code;
    private String message;

    EResultCode(int code,String message){
        this.code = code;
        this.message = message;
    }

    public static EResultCode getByCode(String code) {
        EResultCode[] arg4;
        int arg3 = (arg4 = values()).length;
        for (int arg2 = 0; arg2 < arg3; ++arg2) {
            EResultCode e = arg4[arg2];
            if (Objects.equals(e.getCode(),code)) {
                return e;
            }
        }
        return null;
    }

}
