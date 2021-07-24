package com.sbx.core.model.util;

import com.sbx.core.model.exception.CustomException;
import org.springframework.lang.Nullable;

import java.util.Objects;


/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/4/9
 */
public class Assert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new CustomException(message);
        }
    }


    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new CustomException(message);
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new CustomException(message);
        }
    }

    public static <T> T requireNonNull(T obj,String message) {
        if (Objects.isNull(obj)) {
            throw new CustomException(message);
        }
        return obj;
    }

}
