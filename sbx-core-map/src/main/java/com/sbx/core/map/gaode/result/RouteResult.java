package com.sbx.core.map.gaode.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>说明：路线结果</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/30
 */
@Data
public class RouteResult implements Serializable {
    private static final long serialVersionUID = -458320939169885200L;

    /**
     * 起点坐标
     */
    private String origin;

    /**
     * 终点坐标
     */
    private String destination;

    private List<PathsResult> paths;
}
