package com.sbx.core.model.util;


import com.sbx.core.model.exception.CustomException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RatioUtil {

    private static final BigDecimal RATIO_MULTIPLE = new BigDecimal(100);
    private static final int RATIO_BIT = 2;

    /**
     * 百分比转Integer类型（存数据库）
     *
     * @param percent 百分比最多两位小数
     * @return
     */
    public static Integer ratioToInt(BigDecimal percent) {
        if (percent == null) {
            return null;
        }
        if (percent.scale() > RATIO_BIT) {
            throw new CustomException("百分比最多输入2位小数");
        }
        return percent.multiply(RATIO_MULTIPLE).intValue();
    }

    /**
     * 百分比转Integer类型（存数据库）
     *
     * @param percent 百分比最多两位小数
     * @return
     */
    public static Integer ratioToInt(String percent) {
        return ratioToInt(new BigDecimal(percent));
    }


    /**
     * Integer类型转百分比，两位小数（页面显示）
     *
     * @param percent
     * @return
     */
    public static BigDecimal intToRatio(Integer percent) {
        if (percent == null) {
            return null;
        }
        return BigDecimal.valueOf(percent).divide(RATIO_MULTIPLE, RATIO_BIT, RoundingMode.HALF_UP);
    }

    /**
     * Integer类型转百分比，两位小数（页面显示）
     *
     * @param percent
     * @return
     */
    public static String intToRatioStr(Integer percent) {
        return intToRatio(percent).toPlainString();
    }

    /**
     * 计算分成
     * @param totalFee  总费用
     * @param ratio 分成比率
     * @return  返回分成价格（分）
     */
    public static Long calculateShare(Long totalFee,Integer ratio){
        BigDecimal price = MoneyUtil.fen2Yuan(totalFee);
        BigDecimal ratioDecimal = intToRatio(ratio).divide(BigDecimal.valueOf(100),4,RoundingMode.HALF_UP);
        return MoneyUtil.yuan2Fen(price.multiply(ratioDecimal));
    }

    /**
     * 计算分成
     * @param totalFee  总费用
     * @param ratio 分成比率
     * @return  返回分成价格（分）
     */
    public static Long calculateShare(Long totalFee,BigDecimal ratio){
        BigDecimal price = MoneyUtil.fen2Yuan(totalFee);
        BigDecimal ratioDecimal = ratio.divide(BigDecimal.valueOf(100),4,RoundingMode.HALF_UP);
        return MoneyUtil.yuan2Fen(price.multiply(ratioDecimal));
    }

}
