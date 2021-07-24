package com.sbx.core.model.validator;


import com.sbx.core.model.base.IResultCode;
import com.sbx.core.model.enums.EResultCode;
import com.sbx.core.model.exception.ValidatorException;
import com.sbx.core.model.util.NumUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;
import org.springframework.util.CollectionUtils;

import javax.validation.Validation;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName: Validator
 * @Description: ${todo}
 * @date 2017/11/21 14:58
 */
public class Validator {

    protected Validator() {
        hibernateValidator = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory()
                .getValidator();
    }

    private final static Validator instance = new Validator();

    private final javax.validation.Validator hibernateValidator;

    public static Validator getInstance() {
        return instance;
    }

    public javax.validation.Validator getHibernateValidator() {
        return hibernateValidator;
    }

    public static <T> void validate(T verifiable, Class<?>... groups) {

        if (Objects.isNull(verifiable)) {
            throw new ValidatorException(EResultCode.PARAM_MISS);
        }

        Validator.getInstance().getHibernateValidator().validate(verifiable, groups).forEach((e) -> {
            throw new ValidatorException(EResultCode.PARAM_VALID_ERROR.getCode(), e.getMessage());
        });

    }

    public static <T> void validate(T verifiable) {

        validate(verifiable, Default.class);
    }

    /**
     * 是否Null
     *
     * @param val 校验值
     * @return
     */
    public Validator notNull(Object val) {
        if (val == null) {
            throw new ValidatorException(EResultCode.PARAM_MISS, StringUtils.EMPTY);
        }
        return this;
    }

    /**
     * 是否Null
     *
     * @param val  校验值
     * @param desc 校验不符提示语
     * @return
     */
    public Validator notNull(Object val, String desc) {
        if (val == null) {
            throw new ValidatorException(EResultCode.PARAM_MISS, desc);
        }
        return this;
    }


    /**
     * 是否Null
     *
     * @param val  校验值
     * @param desc 校验不符提示语
     * @return
     */
    public Validator notNull(boolean check, Object val, String desc) {
        if (check) {
            return notNull(val, desc);
        }
        return this;
    }


    public Validator notNull(Object val, String desc, IResultCode resultCode) {
        if (val == null) {
            throw new ValidatorException(resultCode, desc);
        }
        return this;
    }

    public Validator notNull(boolean check, Object val, String desc, IResultCode resultCode) {
        if (check) {
            return notNull(val, desc, resultCode);
        }
        return this;
    }

    /**
     * map 非空判断
     */
    public <K, V> Validator notEmpty(Map<K, V> val) {

        return notEmpty(val, StringUtils.EMPTY);
    }

    public <K, V> Validator notEmpty(Map<K, V> val, String desc) {
        if (CollectionUtils.isEmpty(val)) {
            throw new ValidatorException(EResultCode.PARAM_MISS, desc);
        }
        return this;
    }

    /**
     * 集合非空判断
     */
    public <T> Validator notEmpty(Collection<T> val) {

        return notEmpty(val, StringUtils.EMPTY);
    }

    public <T> Validator notEmpty(Collection<T> val, String desc) {
        if (CollectionUtils.isEmpty(val)) {
            throw new ValidatorException(EResultCode.PARAM_MISS, desc);
        }
        return this;
    }

    /**
     * 字符串非空判断
     */
    public Validator notBlank(String val) {

        return notBlank(val, StringUtils.EMPTY);
    }

    public Validator notBlank(String val, String desc) {
        if (StringUtils.isBlank(val)) {
            throw new ValidatorException(EResultCode.PARAM_MISS, desc);
        }
        return this;
    }

    /**
     * 建议使用 notEmpty
     */
    @Deprecated
    public Validator CollectionNotEmpty(Collection val, String desc) {
        if (CollectionUtils.isEmpty(val)) {
            throw new ValidatorException(EResultCode.PARAM_MISS, desc);
        }
        return this;
    }

    /**
     * 建议使用 notEmpty
     */
    @Deprecated
    public Validator CollectionNotEmpty(boolean check, Collection val, String desc) {
        if (check) {
            return CollectionNotEmpty(val, desc);
        }
        return this;
    }

    /**
     * 是否Empty
     *
     * @param val 校验值
     * @return
     */
    @Deprecated
    public Validator notEmpty(Object val) {
        if (val == null || val.toString().trim().length() <= 0) {
            throw new ValidatorException(EResultCode.PARAM_MISS, "参数未找到");
        }
        return this;
    }

    /**
     * 是否Empty
     *
     * @param val  校验值
     * @param desc 校验不符提示语
     * @return
     * @Deprecated: 不建议使用
     */
    @Deprecated
    public Validator notEmpty(Object val, String desc) {
        if (val == null || val.toString().trim().length() <= 0) {
            throw new ValidatorException(EResultCode.PARAM_MISS, desc);
        }
        return this;
    }

    @Deprecated
    public Validator notEmpty(boolean check, Object val, String desc) {
        if (check) {
            return notEmpty(val, desc);
        }
        return this;
    }

    /**
     * 是否Blank
     *
     * @param val 校验值
     * @return
     */
    @Deprecated
    public Validator notBlank(Object val) {
        if (val == null || val.toString().length() <= 0) {
            throw new ValidatorException(EResultCode.PARAM_MISS, "参数未找到");
        }
        return this;
    }

    /**
     * 是否Blank
     *
     * @param val  校验值
     * @param desc 校验不符提示语
     * @return
     */
    @Deprecated
    public Validator notBlank(Object val, String desc) {
        if (val == null || val.toString().length() <= 0) {
            throw new ValidatorException(EResultCode.PARAM_MISS, desc);
        }
        return this;
    }

    @Deprecated
    public Validator notBlank(boolean check, Object val, String desc) {
        if (check) {
            return notBlank(val, desc);
        }
        return this;
    }

    public Validator notBlank(Object val, String desc, IResultCode resultCode) {
        if (val == null || val.toString().length() <= 0) {
            throw new ValidatorException(resultCode, desc);
        }
        return this;
    }

    public Validator notBlank(boolean check, Object val, String desc, IResultCode resultCode) {
        if (check) {
            return notBlank(val, desc, resultCode);
        }
        return this;
    }

    /**
     * 是否大于0
     *
     * @param val        校验值
     * @param desc       校验不符提示语
     * @param resultCode 校验不符结果码
     * @return
     */
    public Validator gtZero(BigDecimal val, String desc, IResultCode resultCode) {
        if (val.compareTo(BigDecimal.ZERO) > 0) {
            throw new ValidatorException(resultCode, desc);
        }
        return this;
    }

    public Validator gtZero(boolean check, BigDecimal val, String desc, IResultCode resultCode) {
        if (check) {
            return gtZero(val, desc, resultCode);
        }
        return this;
    }


    /**
     * 是否小于0
     *
     * @param val        校验值
     * @param desc       校验不符提示语
     * @param resultCode 校验不符结果码
     * @return
     */
    public Validator ltZero(BigDecimal val, String desc, IResultCode resultCode) {
        if (val.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidatorException(resultCode, desc);
        }
        return this;
    }

    public Validator ltZero(boolean check, BigDecimal val, String desc, IResultCode resultCode) {
        if (check) {
            return ltZero(val, desc, resultCode);
        }
        return this;
    }

    /**
     * 是否等于0
     *
     * @param val        校验值
     * @param desc       校验不符提示语
     * @param resultCode 校验不符结果码
     * @return
     */
    public Validator eqZero(BigDecimal val, String desc, IResultCode resultCode) {
        if (val.compareTo(BigDecimal.ZERO) == 0) {
            throw new ValidatorException(resultCode, desc);
        }
        return this;
    }

    public Validator eqZero(boolean check, BigDecimal val, String desc, IResultCode resultCode) {
        if (check) {
            return eqZero(val, desc, resultCode);
        }
        return this;
    }


    /**
     * 值1是否大于值2
     *
     * @param val1       校验值1
     * @param val2       校验值2
     * @param desc       校验不符提示语
     * @param resultCode 校验不符结果码
     * @return
     */
    public <T extends Number & Comparable<T>> Validator gt(T val1, T val2, String desc, IResultCode resultCode) {
        if (!NumUtils.gt(val1, val2)) {
            throw new ValidatorException(resultCode, desc);
        }
        return this;
    }

    public <T extends Number & Comparable<T>> Validator gt(boolean check, T val1, T val2, String desc, IResultCode resultCode) {
        if (check) {
            gt(val1, val2, desc, resultCode);
        }
        return this;
    }

    /**
     * 是否为真
     *
     * @return
     */
    public Validator isTrue(boolean expression) {
        if (!expression) {
            throw new ValidatorException(EResultCode.PARAM_VALID_ERROR.getCode(), "无效参数");
        }
        return this;
    }

    /**
     * 是否为真
     *
     * @param desc 校验不符提示语
     * @return
     */
    public Validator isTrue(boolean expression, String desc) {
        if (!expression) {
            throw new ValidatorException(EResultCode.PARAM_VALID_ERROR.getCode(), desc);
        }
        return this;
    }
}
