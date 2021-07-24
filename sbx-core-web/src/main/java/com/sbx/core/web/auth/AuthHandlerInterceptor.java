package com.sbx.core.web.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/12/29
 */
@Component
public class AuthHandlerInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private AbstractAuthExecute abstractAuthExecute;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        abstractAuthExecute.authHandler(request,response,handler);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handler, Exception ex) throws Exception {
        abstractAuthExecute.afterCompletion(request,response,handler,ex);
        super.afterCompletion(request, response, handler, ex);
    }
}
