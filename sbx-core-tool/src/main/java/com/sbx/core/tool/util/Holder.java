package com.sbx.core.tool.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 一些常用的单利对象
 *
 * @author Z.jc
 */
public class Holder {

	/**
	 * RANDOM
	 */
	public final static Random RANDOM = new Random();

	/**
	 * SECURE_RANDOM
	 */
	public final static SecureRandom SECURE_RANDOM = new SecureRandom();
}
