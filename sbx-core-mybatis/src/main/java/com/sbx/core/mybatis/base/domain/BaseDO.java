package com.sbx.core.mybatis.base.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>BaseEntity class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/3/20
 */
@Data
public class BaseDO implements Serializable {
    private static final long serialVersionUID = 1484433101138163250L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long createUserId;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    /**
     * 状态[0:未删除,1:删除]
     */
    @TableLogic
    private Boolean isDeleted;


}
