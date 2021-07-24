package com.sbx.core.web.auth;

import com.sbx.core.web.EnableAuth;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/12/30
 */
@Component
@ConditionalOnMissingBean(annotation = {EnableAuth.class})
public class DefaultAuthExecuteBean extends AbstractAuthExecute{

    @Override
    public void authHandler(HttpServletRequest request, HttpServletResponse response, Object handler) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
