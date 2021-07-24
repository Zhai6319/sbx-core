package com.sbx.core.model.base.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/8/17
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -6534373050324941917L;


    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态[0:未删除,1:删除]
     */
    private Boolean isDeleted;
}
