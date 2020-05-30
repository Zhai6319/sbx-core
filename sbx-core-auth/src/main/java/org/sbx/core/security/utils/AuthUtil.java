/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.sbx.core.security.utils;



import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.sbx.core.launch.constant.TokenConstant;
import org.sbx.core.security.AuthUser;
import org.sbx.core.security.helper.JwtHelper;
import org.sbx.core.tool.util.Func;
import org.sbx.core.tool.util.StringPool;
import org.sbx.core.tool.util.StringUtil;
import org.sbx.core.tool.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * Auth工具类
 *
 * @author Chill
 */
public class AuthUtil {
	private static final String SBX_USER_REQUEST_ATTR = "_SBX_USER_REQUEST_ATTR_";

	private final static String HEADER = TokenConstant.HEADER;
	private final static String USER_NAME = TokenConstant.USER_NAME;
	private final static String NICK_NAME = TokenConstant.NICK_NAME;
	private final static String USER_ID = TokenConstant.USER_ID;
	private final static String ROLE_ID = TokenConstant.ROLE_ID;
	private final static String ROLE_NAME = TokenConstant.ROLE_NAME;
	private final static String CLIENT_ID = TokenConstant.CLIENT_ID;
	private final static String AUTHORITY = TokenConstant.AUTHORITY;

	/**
	 * 获取用户信息
	 *
	 * @return AuthUser
	 */
	public static AuthUser getUser() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		// 优先从 request 中获取
		Object authUser = request.getAttribute(SBX_USER_REQUEST_ATTR);
		if (authUser == null) {
			authUser = getUser(request);
			if (authUser != null) {
				// 设置到 request 中
				request.setAttribute(SBX_USER_REQUEST_ATTR, authUser);
			}
		}
		return (AuthUser) authUser;
	}

	/**
	 * 获取用户信息
	 *
	 * @param request request
	 * @return AuthUser
	 */
	public static AuthUser getUser(HttpServletRequest request) {
		Map<String, Claim> claims = getClaims(request);
		if (claims == null) {
			return null;
		}
		String clientId = claims.get(AuthUtil.CLIENT_ID).asString();
		Long userId = Long.parseLong(claims.get(AuthUtil.USER_ID).asString());
		String roleId = claims.get(AuthUtil.ROLE_ID).asString();
		String roleName = claims.get(AuthUtil.ROLE_NAME).asString();
		String userName = claims.get(AuthUtil.USER_NAME).asString();
		String nickName = claims.get(AuthUtil.NICK_NAME).asString();
		String authority = claims.get(AuthUtil.AUTHORITY).asString();
		AuthUser authUser = new AuthUser();
		authUser.setClientId(clientId);
		authUser.setUserId(userId);
		authUser.setRoleId(roleId);
		authUser.setRoleName(roleName);
		authUser.setUserName(userName);
		authUser.setNickName(nickName);
		authUser.setAuthority(authority);
		return authUser;
	}

	/**
	 * 是否为超管
	 *
	 * @return boolean
	 */
	public static boolean isAdministrator() {
		return StringUtil.containsAny(getUserRole(), "administrator");
	}

	/**
	 * 获取用户id
	 *
	 * @return userId
	 */
	public static Long getUserId() {
		AuthUser user = getUser();
		return (null == user) ? null : user.getUserId();
	}

	/**
	 * 获取用户id
	 *
	 * @param request request
	 * @return userId
	 */
	public static Long getUserId(HttpServletRequest request) {
		AuthUser user = getUser(request);
		return (null == user) ? null : user.getUserId();
	}



	/**
	 * 获取用户名
	 *
	 * @return userName
	 */
	public static String getUserName() {
		AuthUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取用户名
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserName(HttpServletRequest request) {
		AuthUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取昵称
	 *
	 * @return userName
	 */
	public static String getNickName() {
		AuthUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getNickName();
	}

	/**
	 * 获取昵称
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getNickName(HttpServletRequest request) {
		AuthUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getNickName();
	}


	/**
	 * 获取用户角色
	 *
	 * @return userName
	 */
	public static String getUserRole() {
		AuthUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}

	/**
	 * 获取用角色
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserRole(HttpServletRequest request) {
		AuthUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}


	/**
	 * 获取客户端id
	 *
	 * @return clientId
	 */
	public static String getClientId() {
		AuthUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}

	/**
	 * 获取客户端id
	 *
	 * @param request request
	 * @return clientId
	 */
	public static String getClientId(HttpServletRequest request) {
		AuthUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}

	/**
	 * 获取Claims
	 *
	 * @param request request
	 * @return Claims
	 */
	public static Map<String, Claim> getClaims(HttpServletRequest request) {
		String auth = request.getHeader(AuthUtil.HEADER);

		if (StringUtil.isNotBlank(auth)) {
			String token = JwtHelper.getToken(auth);
			if (StringUtil.isNotBlank(token)) {
				return AuthUtil.parseJWT(token);
			}
		} else {
			String parameter = request.getParameter(AuthUtil.HEADER);
			if (StringUtil.isNotBlank(parameter)) {
				return AuthUtil.parseJWT(parameter);
			}
		}
		return null;
	}

	/**
	 * 获取请求头
	 *
	 * @return header
	 */
	public static String getHeader() {
		return getHeader(Objects.requireNonNull(WebUtil.getRequest()));
	}

	/**
	 * 获取请求头
	 *
	 * @param request request
	 * @return header
	 */
	public static String getHeader(HttpServletRequest request) {
		return request.getHeader(HEADER);
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param jsonWebToken jsonWebToken
	 * @return Claims
	 */
	public static Map<String, Claim> parseJWT(String jsonWebToken) {
		return JWT.decode(jsonWebToken).getClaims();
	}

}
