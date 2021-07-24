package com.sbx.core.tool.util;

import com.sbx.core.model.util.MileageUtil;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.math.BigDecimal;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/20
 */
public class GpsUtil {

    public static Long getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
        return new Double(geoCurve.getEllipsoidalDistance()).longValue();
    }

    public static Long getDistanceMeter(double startLatitude,double startLongitude,double endLatitude,double endLongitude){
        GlobalCoordinates source = new GlobalCoordinates(startLatitude, startLongitude);
        GlobalCoordinates target = new GlobalCoordinates(endLatitude, endLongitude);
        return getDistanceMeter(source, target, Ellipsoid.WGS84);
    }

    public static BigDecimal getDistanceMeterAsKm(double startLatitude,double startLongitude,double endLatitude,double endLongitude) {
        Long mileageM = getDistanceMeter(startLatitude,startLongitude,endLatitude,endLongitude);
        return MileageUtil.m2Km(mileageM);
    }


}
