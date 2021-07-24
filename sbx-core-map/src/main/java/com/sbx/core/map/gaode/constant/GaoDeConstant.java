package com.sbx.core.map.gaode.constant;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/8
 */
public interface GaoDeConstant {

    /**
     * 货车路线规划请求url
     */
    String TRUCK_ROUTE_PLANNING_URL = "https://restapi.amap.com/v4/direction/truck";

    String DRIVING_ROUTE_PLANNING_URL = "https://restapi.amap.com/v3/direction/driving";
    /**
     * 物流路线矩阵
     */
    String LOGISTICS_ROUTE_MATRIX  = "https://tsapi.amap.com/v1/logistics/route/matrix";

    /**
     * 测距
     */
    String DISTANCE = "https://restapi.amap.com/v3/distance";

    /**
     * 逆地理编码
     */
    String REGEO = "https://restapi.amap.com/v3/geocode/regeo";

}
