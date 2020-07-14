package com.sbx.core.security;

import lombok.Data;

/**
 * TokenInfo
 *
 * @author Z.jc
 */
@Data
public class TokenInfo {

	/**
	 * 令牌值
	 */
	private String token;

	/**
	 * 过期秒数
	 */
	private int expire;

}
