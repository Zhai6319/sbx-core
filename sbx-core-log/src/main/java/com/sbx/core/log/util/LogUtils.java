package com.sbx.core.log.util;

import org.slf4j.Logger;
import org.slf4j.MDC;

public class LogUtils {


    /**
    * 全局异常打印，必须放在最后一行执行，因为打印之后会清除MDC
    */
    public static void logGlobalException(Throwable t, Logger logger) {
        MDC.put("class", t.getStackTrace()[0].getClassName());
        MDC.put("method", t.getStackTrace()[0].getMethodName());
        MDC.put("line", String.valueOf(t.getStackTrace()[0].getLineNumber()));
        MDC.put("file", t.getStackTrace()[0].getFileName());
        logger.error("{} {}", t.getMessage(), t.getStackTrace()[0], t);

        MDC.clear();
    }
}
