package com.sbx.core.log.util;


import com.sbx.core.model.util.StringPool;

import java.util.Properties;

/**
 * Elk配置工具
 *
 * @author Chill
 */
public class ElkPropsUtil {

	/**
	 * 获取elk服务地址
	 *
	 * @return 服务地址
	 */
	public static String getDestination() {
		Properties props = System.getProperties();
		return props.getProperty("sbx.log.elk.destination", StringPool.EMPTY);
	}

}
