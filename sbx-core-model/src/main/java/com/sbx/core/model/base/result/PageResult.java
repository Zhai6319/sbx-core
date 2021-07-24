package com.sbx.core.model.base.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/9/7
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 704122492671023217L;
    private List<T> records;

    private Long current;

    private Long size;

    private Long total;

    private Long pages;

    public PageResult(Long current,Long size,Long total,Long pages,List<T> records){
        this.records = records;
        this.current = current;
        this.size = size;
        this.total = total;
        this.pages = pages;
    }

    public PageResult(){}

}
