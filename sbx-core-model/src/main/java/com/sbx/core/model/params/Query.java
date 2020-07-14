package com.sbx.core.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/4/10
 */
@Data
@ApiModel(value = "Query对象",description = "查询参数")
public class Query {

    @ApiModelProperty(value = "当前页")
    private Integer current = 1;

    @ApiModelProperty(value = "每页数据条数")
    private Integer size = 20;

    @ApiModelProperty(value = "顺序字段")
    private String ascS;

    @ApiModelProperty(value = "倒序字段")
    private String descS;



}
