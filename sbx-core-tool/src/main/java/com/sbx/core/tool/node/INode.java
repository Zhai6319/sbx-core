package com.sbx.core.tool.node;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Blade.
 *
 * @author Z.jc
 */
public interface INode extends Serializable {

	/**
	 * 主键
	 *
	 * @return Long
	 */
	Long getId();

	/**
	 * 父主键
	 *
	 * @return Long
	 */
	Long getParentId();

	/**
	 * 子孙节点
	 *
	 * @return List<INode>
	 */
	List<INode> getChildren();

	/**
	 * 是否有子孙节点
	 *
	 * @return Boolean
	 */
	default Boolean getHasChildren() {
		return false;
	}

}
