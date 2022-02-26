package com.sbx.core.dubbo.filter;


import com.sbx.core.log.util.LogUtils;
import com.sbx.core.model.api.Response;
import com.sbx.core.model.context.AppContext;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


/**
 * @author guoluwei
 * @ClassName: GlobalDubboConsumerFilter
 * @Description: 在dubbo调用中传递traceId
 * @date 2020/7/6 16:38
 */
public class GlobalDubboConsumerFilter implements Filter, Filter.Listener {

    private final static Logger logger = LoggerFactory.getLogger(GlobalDubboConsumerFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        invocation.setAttachment(AppContext.TRACE_ID_NAME,AppContext.getContext().getTraceId());
        return invoker.invoke(invocation);
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        if (appResponse.hasException()) {
            MDC.put(AppContext.MDC_TRACE_ID_NAME, invocation.getAttachment(AppContext.TRACE_ID_NAME));
            MDC.put(AppContext.MDC_SPAN_ID_NAME, invocation.getAttachment(AppContext.SPAN_ID_NAME));
            Throwable t = appResponse.getException();

            appResponse.setValue(Response.fail(100, t.getMessage()));
            appResponse.setException(null);

            LogUtils.logGlobalException(t, logger);
        }
    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {
        MDC.put(AppContext.MDC_TRACE_ID_NAME, invocation.getAttachment(AppContext.TRACE_ID_NAME));
        MDC.put(AppContext.MDC_SPAN_ID_NAME, invocation.getAttachment(AppContext.SPAN_ID_NAME));
        LogUtils.logGlobalException(t, logger);
    }


}
