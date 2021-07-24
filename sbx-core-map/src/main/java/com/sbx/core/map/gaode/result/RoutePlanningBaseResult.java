package com.sbx.core.map.gaode.result;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明：规划路径基本返回参数</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/30
 */
@Data
public class RoutePlanningBaseResult implements Serializable {
    private static final long serialVersionUID = 8802405644949998590L;

    private Integer errcode;

    private String errmsg;

    private String errdetail;

    private RoutePlanningResult data;
}
