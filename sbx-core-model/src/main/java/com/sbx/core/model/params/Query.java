package com.sbx.core.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/4/10
 */
@Data
@ApiModel(value = "Query对象",description = "查询参数")
public class Query implements Serializable {
    private static final long serialVersionUID = 4762387989737502482L;


    @ApiModelProperty(value = "当前页")
    private Long current = 1L;

    @ApiModelProperty(value = "每页数据条数")
    private Long size = 20L;



}
