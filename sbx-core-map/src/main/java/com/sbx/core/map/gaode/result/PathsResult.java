package com.sbx.core.map.gaode.result;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>说明：具体方案</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/30
 */
@Data
public class PathsResult implements Serializable {
    private static final long serialVersionUID = -6571880235128191313L;

    /**
     * 距离
     */
    private Long distance;

    /**
     * 耗时
     */
    private Long duration;

    /**
     * 策略
     */
    private String strategy;

    /**
     * 收费金额
     */
    private BigDecimal tolls;

    /**
     * 收费距离长度
     */
    private Long toll_distance;

    /**
     * 限行结果
     */
    private Integer restriction;

    /**
     * 红绿灯数量
     */
    private Integer traffic_lights;

    private List<StepsResult> steps;
}
