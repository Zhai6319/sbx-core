package com.sbx.core.model.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/9
 */
public class MileageUtil {

    public static Long km2M(String km) {
        if (km == null) {
            return null;
        }
        return (new BigDecimal(km)).setScale(3, 4).multiply(new BigDecimal(1000)).longValue();
    }


    public static Long km2M(BigDecimal km) {
        if (km == null) {
            return null;
        }
        return km.setScale(3, 4).multiply(new BigDecimal(1000)).longValue();
    }





    public static String km2MStr(String km) {
        if (km == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat("");
        return format.format((new BigDecimal(km)).setScale(3, 4).multiply(new BigDecimal(1000)));
    }

    public static String km2MStr(BigDecimal km) {
        if (km == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat("");
        return format.format(km.setScale(3, 4).multiply(new BigDecimal(1000)));
    }

    public static String m2KmStr(Long m) {
        if (m == null) {
            return null;
        }
        return BigDecimal.valueOf((double) m / 1000.0D).setScale(3, 2).toPlainString();
    }

    public static String m2KmStrStripTrailingZeros(Long m) {
        if (m == null) {
            return null;
        }
        return BigDecimal.valueOf((double) m / 1000.0D).setScale(3, 2).stripTrailingZeros().toPlainString();
    }

    public static BigDecimal m2Km(Long m) {
        if (m == null) {
            return null;
        }
        return BigDecimal.valueOf((double) m / 1000.0D).setScale(3, 2);
    }

    public static String m2KmStr(String m) {
        if (m == null) {
            return null;
        }
        return new BigDecimal(m).divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP).toString();
    }

    public static BigDecimal m2Km(String m) {
        if (m == null) {
            return null;
        }
        return new BigDecimal(m).divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP);
    }

}
