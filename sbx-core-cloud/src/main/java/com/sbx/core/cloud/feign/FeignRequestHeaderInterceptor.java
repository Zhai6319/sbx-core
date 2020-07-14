package com.sbx.core.cloud.feign;

import com.sbx.core.model.exception.BusinessException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author zhaijianchao
 * @since 2020/03/25
 */
@Slf4j
public class FeignRequestHeaderInterceptor implements RequestInterceptor {
	private static final String FEIGN_CLIENT_PREFIX = "/client-feign";

	@Override
	public void apply(RequestTemplate requestTemplate) {
		if (!requestTemplate.url().startsWith(FEIGN_CLIENT_PREFIX)) {
			throw new BusinessException("feign client interface please extents IBaseClient and request path prefix is '/client-feign'");
		}
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Enumeration<String> headerNames = request.getHeaderNames();
		if (Objects.nonNull(headerNames)) {
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement();
				String values = request.getHeader(name);
				requestTemplate.header(name, values);
			}
		}
		log.info("request template is{}",requestTemplate);
	}

}
