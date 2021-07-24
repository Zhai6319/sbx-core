package com.sbx.core.map.gaode.result;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/30
 */
@Data
public class RoutePlanningResult implements Serializable {
    private static final long serialVersionUID = 4693607616836911667L;

    private RouteResult route;

    private Integer count;

}
