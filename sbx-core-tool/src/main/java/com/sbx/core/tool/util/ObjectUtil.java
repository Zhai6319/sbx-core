package com.sbx.core.tool.util;

import org.springframework.lang.Nullable;

/**
 * 对象工具类
 *
 * @author Z.jc
 */
public class ObjectUtil extends org.springframework.util.ObjectUtils {

	/**
	 * 判断元素不为空
	 * @param obj object
	 * @return boolean
	 */
	public static boolean isNotEmpty(@Nullable Object obj) {
		return !ObjectUtil.isEmpty(obj);
	}

}
