package com.sbx.core.model.base.result;

import lombok.Data;

import java.util.List;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/9/7
 */
@Data
public class PageResult<T> {

    private List<T> records;

    private Long current;

    private Long size;

    private Long total;

    private Long pages;

}
