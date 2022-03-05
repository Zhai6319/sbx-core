package com.sbx.core.map.gaode;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.sbx.core.map.autoconfigure.MapProperties;
import com.sbx.core.map.gaode.constant.GaoDeConstant;
import com.sbx.core.map.gaode.enums.DistanceTypeEnum;
import com.sbx.core.model.exception.CustomException;
import com.sbx.core.tool.util.StringUtil;
import com.sbx.core.map.gaode.result.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/8
 */
@Slf4j
@Component
public class GaoDeMapHelper {

    private static final String SUCCESS_CODE = "1";

    private static final Integer ERROR_CODE = 0;

    @Resource
    private MapProperties mapProperties;


    /**
     * 路线距离
     * @param origins    开始经纬度
     * @param destination   目的经纬度
     */
    public DistanceResult distance(String origins, String destination, DistanceTypeEnum distanceType){
        Map<String,Object> params = new HashMap<>(4);
        params.put("key",mapProperties.getGaoDe().getKey());
        params.put("origins",origins);
        params.put("destination",destination);
        params.put("type",distanceType.type+"");
        params.put("output","json");
        params.put("nosteps","1");
        try {
            String resultStr = HttpUtil.get(GaoDeConstant.DISTANCE,params);
            log.info("调用高德api计算距离响应：{}",resultStr);
            DistanceBaseResult result = JSONObject.parseObject(resultStr,DistanceBaseResult.class);
            if (Objects.equals(result.getStatus(),SUCCESS_CODE)) {
                return result.getResults().get(0);
            }
            return null;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * 返回规划路线线路坐标点
     * @param origins   起始坐标点
     * @param destination   目的坐标点
     * @param strategy 路线策略
     * @param waypoints 途经点
     * @return  规划路线详情
     */
    public DrivingRoutePlanningResult routePlanning(String origins, String destination, String strategy, String waypoints){
        Map<String,Object> params = new HashMap<>(4);
        params.put("key",mapProperties.getGaoDe().getKey());
//        params.put("key","034f90809fe890afd3707efc3a60e31c");
        params.put("origin",origins);
        params.put("destination",destination);
        params.put("strategy", StringUtil.isBlank(strategy)?"2":strategy);
        if (StringUtil.isNotBlank(waypoints)) {
            params.put("waypoints",waypoints);
        }
        try {
            String resultStr = HttpUtil.get(GaoDeConstant.DRIVING_ROUTE_PLANNING_URL,params);
            DrivingRoutePlanningResult result = JSONObject.parseObject(resultStr, DrivingRoutePlanningResult.class);
            if (Objects.equals(result.getStatus(),SUCCESS_CODE)) {
                return result;
            }
        } catch (Exception e) {
            log.error("获取路径规划错误",e);
        }
        return null;
    }

    /**
     * 返回规划路线线路坐标点
     * @param origins   起始坐标点
     * @param destination   目的坐标点
     * @param strategy 路线策略
     * @param waypoints 途经点
     * @return  返回路线坐标点
     */
    public List<PolylineModel> routePlanningPolyline(String origins, String destination, String strategy, String waypoints){
        DrivingRoutePlanningResult result = this.routePlanning(origins,destination,strategy,waypoints);
        if (Objects.isNull(result)) {
            return null;
        }
        List<String> polylineList = result.getRoute().getPaths().get(0).getSteps().stream().map(StepsResult::getPolyline).collect(Collectors.toList());
        List<PolylineModel> polylineModelList = new ArrayList<>();
        polylineList.forEach(polyline->{
            String [] polylineArray = polyline.split(";");
            for (String polylineStr : polylineArray) {
                String[] polylineCoordinate = polylineStr.split(",");
                PolylineModel polylineModel = new PolylineModel();
                polylineModel.setLongitude(polylineCoordinate[0]);
                polylineModel.setLatitude(polylineCoordinate[1]);
                polylineModelList.add(polylineModel);
            }
        });
        return polylineModelList;
    }

    /**
     * 逆地理编码
     * @param location   坐标点
     * @return  当前位置
     */
    public ReGeoCodeResult regeo(String location){
        Map<String,Object> params = new HashMap<>(4);
        params.put("key",mapProperties.getGaoDe().getKey());
//        params.put("key","034f90809fe890afd3707efc3a60e31c");
        params.put("location",location);
        params.put("extensions","base");
        params.put("batch", "false");
        try {
            String resultStr = HttpUtil.get(GaoDeConstant.REGEO,params);
            ReGeoCodeBase result = JSONObject.parseObject(resultStr, ReGeoCodeBase.class);
            if (Objects.equals(result.getStatus(),SUCCESS_CODE)) {
                return result.getRegeocode();
            }
        } catch (Exception e) {
            log.error("逆地理编码错误",e);
        }
        return null;
    }

}
