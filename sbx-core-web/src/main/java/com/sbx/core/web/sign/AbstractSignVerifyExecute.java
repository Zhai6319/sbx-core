package com.sbx.core.web.sign;

import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/12/29
 */
public abstract class AbstractSignVerifyExecute {

    public abstract void verifySign(JoinPoint joinPoint,HttpServletRequest request);

}
