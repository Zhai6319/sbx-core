package com.sbx.core.redis.cache;


import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.time.Duration;

/**
 * cache key
 *
 * @author Z.jc
 */
public interface ICacheKey {

	/**
	 * 获取前缀
	 *
	 * @return key 前缀
	 */
	String getPrefix();

	/**
	 * 超时时间
	 *
	 * @return 超时时间
	 */
	@Nullable
	default Duration getExpire() {
		return null;
	}

	/**
	 * 组装 cache key
	 *
	 * @param suffix 参数
	 * @return cache key
	 */
	default CacheKey getKey(Object... suffix) {
		String prefix = this.getPrefix();
		// 拼接参数
		String key;
		if (ObjectUtils.isEmpty(suffix)) {
			key = prefix;
		} else {
			key = prefix.concat(StringUtils.join(suffix, ":"));
		}
		Duration expire = this.getExpire();
		return expire == null ? new CacheKey(key) : new CacheKey(key, expire);
	}

}
