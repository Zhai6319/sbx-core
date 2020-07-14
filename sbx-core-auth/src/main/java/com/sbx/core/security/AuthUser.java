package com.sbx.core.security;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体
 *
 * @author Z.jc
 */
@Data
public class AuthUser implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 客户端id
	 */
	@ApiModelProperty(hidden = true)
	private String clientId;

	/**
	 * 用户id
	 */
	@ApiModelProperty(hidden = true)
	private Long userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty(hidden = true)
	private String userName;
	/**
	 * 昵称
	 */
	@ApiModelProperty(hidden = true)
	private String nickName;

	/**
	 * 角色id
	 */
	@ApiModelProperty(hidden = true)
	private String roleId;
	/**
	 * 角色名
	 */
	@ApiModelProperty(hidden = true)
	private String roleName;

	/**
	 * 权限
	 */
	@ApiModelProperty(hidden = true)
	private String authority;


}
