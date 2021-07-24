package com.sbx.core.model.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @ClassName NumberUtils
 * @Description TODO
 *
 * @Date 24/06/2017 22:49
 */
public class NumUtils {

	/**
	 * 判断BigDecimal值是否大于0
	 *
	 * @param val
	 * @return
	 */
	public static boolean gt0(BigDecimal val) {
		return val != null && val.compareTo(BigDecimal.ZERO) > 0;
	}

	/**
	 * 判断BigDecimal值是否大于等于0
	 *
	 * @param val
	 * @return
	 */
	public static boolean gte0(BigDecimal val) {
		return val != null && val.compareTo(BigDecimal.ZERO) >= 0;
	}

	/**
	 * 判断BigDecimal值是否小于0
	 *
	 * @param val
	 * @return
	 */
	public static boolean lt0(BigDecimal val) {
		return val != null && val.compareTo(BigDecimal.ZERO) < 0;
	}

	/**
	 * 判断BigDecimal值是否小于等于0
	 *
	 * @param val
	 * @return
	 */
	public static boolean lte0(BigDecimal val) {
		return val != null && val.compareTo(BigDecimal.ZERO) <= 0;
	}

	/**
	 * 判断src是否大于tgt
	 *
	 * @param src
	 * @param tgt
	 * @return
	 */
	public static boolean gt(BigDecimal src, BigDecimal tgt) {
		return src != null && tgt != null && src.compareTo(tgt) > 0;
	}


	/**
	 * 判断src是否大于tgt
	 *
	 * @param src
	 * @param tgt
	 * @return
	 */
	public static <T extends Number & Comparable<T>> boolean gt(T src, T tgt) {
		return src != null && tgt != null && src.compareTo(tgt) > 0;
	}


	/**
	 * 判断src是否大于tgt
	 *
	 * @param src
	 * @param tgt
	 * @return
	 */
	public static boolean gte(BigDecimal src, BigDecimal tgt) {
		return src != null && tgt != null && src.compareTo(tgt) >= 0;
	}

	/**
	 * 判断src是否小于tgt
	 *
	 * @param src
	 * @param tgt
	 * @return
	 */
	public static boolean lt(BigDecimal src, BigDecimal tgt) {
		return src != null && tgt != null && src.compareTo(tgt) < 0;
	}

	/**
	 * 判断src是否小于tgt
	 *
	 * @param src
	 * @param tgt
	 * @return
	 */
	public static <T extends Number & Comparable<T>> boolean lt(T src, T tgt) {
		return src != null && tgt != null && src.compareTo(tgt) < 0;
	}

	/**
	 * 对数据库采用默认值 0或小于0 的id,视为空
	 */
	public static boolean idIsNull(Long id){

		return id == null || id <= 0;
	}

	public static Long decimalToLong(String decimal) {
		if (decimal == null) {
			return null;
		}
		return (new BigDecimal(decimal)).setScale(2, 4).multiply(new BigDecimal(100)).longValue();
	}

	public static Long decimalToLong(BigDecimal decimal) {
		if (decimal == null) {
			return null;
		}
		return decimal.setScale(2, 4).multiply(new BigDecimal(100)).longValue();
	}

	public static String decimalToLongStr(String decimal) {
		if (decimal == null) {
			return null;
		}
		DecimalFormat format = new DecimalFormat("");
		return format.format((new BigDecimal(decimal)).setScale(2, 4).multiply(new BigDecimal(100)));
	}

	public static String decimalToLongStr(BigDecimal decimal) {
		if (decimal == null) {
			return null;
		}
		DecimalFormat format = new DecimalFormat("");
		return format.format(decimal.setScale(2, 4).multiply(new BigDecimal(100)));
	}

	public static String longToDecimalStr(Long value) {
		if (value == null) {
			return null;
		}
		return BigDecimal.valueOf((double) value / 100.0D).setScale(2, 2).toPlainString();
	}

	public static String longToDecimalStrStripTrailingZeros(Long value) {
		if (value == null) {
			return null;
		}
		return BigDecimal.valueOf((double) value / 100.0D).setScale(2, 2).stripTrailingZeros().toPlainString();
	}

	public static BigDecimal longToDecimal(Long value) {
		if (value == null) {
			return null;
		}
		return BigDecimal.valueOf((double) value / 100.0D).setScale(2, 2);
	}

	public static String longToDecimalStr(String value) {
		if (value == null) {
			return null;
		}
		return new BigDecimal(value).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).toString();
	}

	public static BigDecimal longToDecimal(String value) {
		if (value == null) {
			return null;
		}
		return new BigDecimal(value).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
	}
}
