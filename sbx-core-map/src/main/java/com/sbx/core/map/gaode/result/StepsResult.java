package com.sbx.core.map.gaode.result;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/30
 */
@Data
public class StepsResult implements Serializable {
    private static final long serialVersionUID = -1458487736008206148L;

    private String instruction;

    private String orientation;

    private String road;

    private String distance;

    private BigDecimal tolls;

    private Long toll_distance;

    private String toll_road;

    private Long duration;

    private String polyline;

    private String action;

    private String assistant_action;
}
