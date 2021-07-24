package com.sbx.core.web.auth;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/12/29
 */
public abstract class AbstractAuthExecute {

    public abstract void authHandler(HttpServletRequest request, HttpServletResponse response, Object handler);

    public abstract void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handler, Exception ex);

}
