package org.sbx.core.model.base;

/**
 * <p>IResultCode class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/9
 */
public interface IResultCode {

    /**
     * 结果码
     * @return
     */
    int getCode();

    /**
     * 结果消息
     * @return
     */
    String getMessage();

}
