package com.sbx.core.model.context;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;


public class AppContext {
    public static final String TRACE_ID_NAME = "X-B3-TraceId";
    public static final String SPAN_ID_NAME = "X-B3-SpanId";
    public static final String MDC_TRACE_ID_NAME = "traceId";
    public static final String MDC_SPAN_ID_NAME = "spanId";


    private static final ThreadLocal<AppContext> LOCAL = ThreadLocal.withInitial(AppContext::new);


    public static AppContext getContext() {
        return LOCAL.get();
    }

    public static void setContext(AppContext context) {
        LOCAL.set(context);
    }

    public static void removeContext() {
        LOCAL.remove();
        MDC.clear();
    }

    public String getTraceId() {
        initTraceId();
        return MDC.get(MDC_TRACE_ID_NAME);
    }

    public static void initTraceId() {
        if (MDC.get(MDC_TRACE_ID_NAME) == null) {
            String traceId = RandomStringUtils.randomAlphanumeric(15);
            MDC.put(AppContext.MDC_TRACE_ID_NAME, traceId);
        }
    }


}
