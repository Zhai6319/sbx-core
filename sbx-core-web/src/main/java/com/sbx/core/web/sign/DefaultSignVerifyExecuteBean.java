package com.sbx.core.web.sign;

import com.sbx.core.web.EnableSign;
import org.aspectj.lang.JoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/12/30
 */
@Component
@ConditionalOnMissingBean(annotation = {EnableSign.class})
public class DefaultSignVerifyExecuteBean extends AbstractSignVerifyExecute {

    @Override
    public void verifySign(JoinPoint joinPoint, HttpServletRequest request) {

    }
}
