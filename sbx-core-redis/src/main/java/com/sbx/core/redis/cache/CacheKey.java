package com.sbx.core.redis.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.time.Duration;

/**
 * cache key 封装
 *
 * @author Z.jc
 */
@Getter
@ToString
@AllArgsConstructor
public class CacheKey {
	/**
	 * redis key
	 */
	private String key;
	/**
	 * 超时时间 秒
	 */
	@Nullable
	private Duration expire;

	public CacheKey(String key) {
		this.key = key;
	}

}
