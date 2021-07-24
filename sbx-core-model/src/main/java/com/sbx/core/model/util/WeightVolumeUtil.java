package com.sbx.core.model.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/10
 */
public class WeightVolumeUtil {

    private WeightVolumeUtil(){}

    /**
     * 根据厘米计算体积重量
     * @param length    长cm
     * @param width 宽cm
     * @param height    高cm
     * @param heavyBubbleRatio  重泡比 kg*100
     * @return 返回重量（kg）
     */
    public static Long calculateVolumeWeightForCm(Long length,Long width,Long height,Long heavyBubbleRatio){
        BigDecimal conversionHeavyBubbleRatio = NumUtils.longToDecimal(heavyBubbleRatio);

        BigDecimal weight = new BigDecimal(volumeForCm(length,width,height))
                .multiply(conversionHeavyBubbleRatio)
                .divide(BigDecimal.valueOf(1000000L),0, RoundingMode.HALF_UP);
        return NumUtils.decimalToLong(weight);
    }

    /**
     * 根据长宽高获取体积
     * @param length    长 cm
     * @param width 宽 cm
     * @param height    高 cm
     * @return  返回体积 cm
     */
    public static Long volumeForCm(Long length,Long width,Long height){
        return length * width * height;
    }

    /**
     * 体积立方厘米转立方米
     * @param volumeCm  立方厘米
     * @return  返回立方米 保留2位小数
     */
    public static BigDecimal volumeCm2m(Long volumeCm){
        if (Objects.isNull(volumeCm)) {
            return null;
        }
        return BigDecimal.valueOf(volumeCm)
                .divide(BigDecimal.valueOf(1000000L),2, RoundingMode.HALF_UP);
    }

    /**
     * 体积立方米转立方厘米
     * @param volumeM   立方米
     * @return  返回立方厘米
     */
    public static Long volumeM2Cm(BigDecimal volumeM){
        if (Objects.isNull(volumeM)) {
            return null;
        }
        return volumeM.multiply(BigDecimal.valueOf(1000000L)).longValue();
    }

    /**
     * 根据长宽高获取体积
     * @param length    长 cm
     * @param width 宽 cm
     * @param height    高 cm
     * @return  返回体积 m
     */
    public static BigDecimal volumeForM(Long length,Long width,Long height){
        return volumeCm2m(volumeForCm(length, width, height));
    }


    /**
     * 计算重量价格
     * @param unitPrice 单价
     * @param weight    重量（kg*100）
     * @return  返回价格(分)
     */
    public static Long calculateWeightPrice(Long unitPrice,Long weight){
        BigDecimal weightDecimal = NumUtils.longToDecimal(weight);
        BigDecimal unitPriceDecimal = MoneyUtil.fen2Yuan(unitPrice);
        return MoneyUtil.yuan2Fen(unitPriceDecimal.multiply(weightDecimal));
    }

    /**
     * 立方米*100获取立方厘米
     * @param mX100 立方米*100
     * @return  返回立方厘米
     */
    public static Long mX100ToCm(Long mX100){
        if (Objects.isNull(mX100)) {
            return null;
        }
        BigDecimal m = NumUtils.longToDecimal(mX100);
        return volumeM2Cm(m);
    }




}
