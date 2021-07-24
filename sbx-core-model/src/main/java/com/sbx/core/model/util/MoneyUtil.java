package com.sbx.core.model.util;


import com.sbx.core.model.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author guoluwei
 * @ClassName: MoneyUtil
 * @Description: 钱单位转换
 * @date 2020/7/25 20:24
 */
@Slf4j
public class MoneyUtil {

    public static Long yuan2Fen(String yuan) {
        if (yuan == null) {
            return null;
        }
        return (new BigDecimal(yuan)).setScale(2, 4).multiply(new BigDecimal(100)).longValue();
    }

    public static Long yuan2Fen(BigDecimal yuan) {
        if (yuan == null) {
            return null;
        }
        return yuan.setScale(2, 4).multiply(new BigDecimal(100)).longValue();
    }

    public static String yuan2FenStr(String yuan) {
        if (yuan == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat("");
        return format.format((new BigDecimal(yuan)).setScale(2, 4).multiply(new BigDecimal(100)));
    }

    public static String yuan2FenStr(BigDecimal yuan) {
        if (yuan == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat("");
        return format.format(yuan.setScale(2, 4).multiply(new BigDecimal(100)));
    }

    public static String fen2YuanStr(Long fen) {
        if (fen == null) {
            return null;
        }
        return BigDecimal.valueOf((double) fen / 100.0D).setScale(2, 2).toPlainString();
    }

    public static String fen2YuanStrStripTrailingZeros(Long fen) {
        if (fen == null) {
            return null;
        }
        return BigDecimal.valueOf((double) fen / 100.0D).setScale(2, 2).stripTrailingZeros().toPlainString();
    }

    public static BigDecimal fen2Yuan(Long fen) {
        if (fen == null) {
            return null;
        }
        return BigDecimal.valueOf((double) fen / 100.0D).setScale(2, 2);
    }

    public static String fen2YuanStr(String fen) {
        if (fen == null) {
            return null;
        }
        return new BigDecimal(fen).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).toString();
    }

    public static BigDecimal fen2Yuan(String fen) {
        if (fen == null) {
            return null;
        }
        return new BigDecimal(fen).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    /**
     * 百分比转long类型（存数据库）
     *
     * @param percent 百分比最多两位小数
     * @return
     */
    public static Long percentageToLong(BigDecimal percent) {
        return Long.valueOf(percentageToInteger(percent));
    }

    /**
     * 百分比转long类型（存数据库）
     *
     * @param percent 百分比最多两位小数
     * @return
     */
    public static Long percentageToLong(String percent) {
        return Long.valueOf(percentageToInteger(percent));
    }

    /**
     * long类型转百分比（页面显示）
     *
     * @param percent
     * @return
     */
    public static BigDecimal longToPercentage(Long percent) {
       return intToPercentage(percent.intValue());
    }

    /**
     * long类型转百分比（页面显示）
     *
     * @param percent
     * @return
     */
    public static String longToPercentageStr(Long percent) {
        return intToPercentageStr(percent.intValue());
    }

    /**
     * 计算提成
     *
     * @param price   订单价
     * @param percent 百分比
     * @return
     */
    public static BigDecimal getRoyalty(Integer price, Long percent) {
        return BigDecimal.valueOf(percent).divide(BigDecimal.valueOf(1000)).multiply(BigDecimal.valueOf(price)).setScale(1);
    }


    private static final BigDecimal PER_MULTIPLE = new BigDecimal(100);

    /**
     * 百分比转Integer类型（存数据库）
     *
     * @param percent 百分比最多两位小数
     * @return
     */
    public static Integer percentageToInteger(BigDecimal percent) {
        if (percent == null) {
            return null;
        }
        if (percent.scale() > 2) {
            log.error("百分比最多输入2位小数");
            throw new CustomException("百分比最多输入2位小数");
        }
        return percent.multiply(PER_MULTIPLE).intValue();
    }

    /**
     * 百分比转Integer类型（存数据库）
     *
     * @param percent 百分比最多一位小数
     * @return
     */
    public static Integer percentageToInteger(String percent) {
        if (percent == null) {
            return null;
        }
        return percentageToInteger(new BigDecimal(percent));
    }


    /**
     * Integer类型转百分比（页面显示）
     *
     * @param percent
     * @return
     */
    public static BigDecimal intToPercentage(Integer percent) {
        if (percent == null) {
            return null;
        }
        return BigDecimal.valueOf(percent).divide(PER_MULTIPLE, 2, RoundingMode.HALF_UP);
    }

    /**
     * Integer类型转百分比（页面显示）
     *
     * @param percent
     * @return
     */
    public static String intToPercentageStr(Integer percent) {
        if (percent == null) {
            return null;
        }
        return intToPercentage(percent).toPlainString();
    }

    /**
     * 确认费用
     * @param minFee    最小费用
     * @param maxFee    最大费用
     * @param currentFee    确认费用
     * @return
     */
    public static Long confirmFee(Long minFee,Long maxFee,Long currentFee){
        if (minFee > currentFee) {
            return minFee;
        }
        if (maxFee < currentFee) {
            return maxFee;
        }
        return currentFee;
    }

    /**
     * 确认费用
     * @param minFee    最小费用
     * @param maxFee    最大费用
     * @param currentFee    确认费用
     * @return
     */
    public static Long confirmFee(BigDecimal minFee,BigDecimal maxFee,Long currentFee) {
        Long minFeeLong = yuan2Fen(minFee);
        Long maxFeeLong = yuan2Fen(maxFee);
        return confirmFee(minFeeLong,maxFeeLong,currentFee);
    }

}
