package com.sbx.core.dubbo.filter;


import com.sbx.core.log.util.LogUtils;
import com.sbx.core.model.api.Response;
import com.sbx.core.model.context.AppContext;
import com.sbx.core.model.exception.CustomException;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


/**
 * @author guoluwei
 * @ClassName: GlobalDubboProviderFilter
 * @Description: 在dubbo调用中传递traceId
 * @date 2020/7/6 16:38
 */
public class GlobalDubboProviderFilter extends ListenableFilter {

    private final static Logger logger = LoggerFactory.getLogger(GlobalDubboProviderFilter.class);

    public GlobalDubboProviderFilter() {
        super.listener = new ExceptionCatchListener();
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            return invoker.invoke(invocation);
        } finally {
            AppContext.removeContext();
        }
    }

    static class ExceptionCatchListener implements Listener {
        @Override
        public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
            if (appResponse.hasException()) {
                MDC.put(AppContext.MDC_TRACE_ID_NAME, invocation.getAttachment(AppContext.TRACE_ID_NAME));
                MDC.put(AppContext.MDC_SPAN_ID_NAME, invocation.getAttachment(AppContext.SPAN_ID_NAME));
                Throwable t = appResponse.getException();
                if (t instanceof CustomException) {
                    CustomException e = (CustomException) t;
                    appResponse.setValue(Response.fail(e.getErrCode(), e.getMessage()));
                } else if (t instanceof NullPointerException) {
                    appResponse.setValue(Response.fail(100, "数据异常"));
                } else {
                    appResponse.setValue(Response.fail(100, "网络异常"));
                }
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

}
