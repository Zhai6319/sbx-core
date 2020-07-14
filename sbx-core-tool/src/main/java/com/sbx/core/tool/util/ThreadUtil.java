package com.sbx.core.tool.util;

import lombok.experimental.UtilityClass;

/**
 * 多线程工具类
 *
 * @author Z.jc
 */
@UtilityClass
public class ThreadUtil {

	/**
	 * Thread sleep
	 *
	 * @param millis 时长
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
