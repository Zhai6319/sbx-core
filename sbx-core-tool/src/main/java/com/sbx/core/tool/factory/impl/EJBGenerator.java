package com.sbx.core.tool.factory.impl;

import com.sbx.core.model.base.result.PageResult;
import com.sbx.core.tool.factory.IGenerator;
import org.dozer.Mapper;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>EJBGenerator class:</p>
 *
 * @author zhaijianchao
 * @version 1.0.0
 * @date 2020/4/4
 */
@Lazy
public class EJBGenerator implements IGenerator {

    @Resource
    protected Mapper dozerMapper;

    @Override
    public <T, S> T convert(S s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        return this.dozerMapper.map(s, clz);
    }


    @Override
    public <T, S> List<T> convert(List<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (S vs : s) {
            list.add(this.dozerMapper.map(vs, clz));
        }
        return list;
    }

    @Override
    public <T, S> Set<T> convert(Set<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        Set<T> set = new HashSet<>();
        for (S vs : s) {
            set.add(this.dozerMapper.map(vs, clz));
        }
        return set;
    }

    @Override
    public <T, S> PageResult<T> convert(PageResult<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        PageResult<T> page = new PageResult<>();
        List<T> records = this.convert(s.getRecords(),clz);
        page.setCurrent(s.getCurrent());
        page.setSize(s.getSize());
        page.setTotal(s.getTotal());
        page.setPages(s.getPages());
        page.setRecords(records);
        return page;
    }

    @Override
    public <T, S> T[] convert(S[] s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clz, s.length);
        for (int i = 0; i < s.length; i++) {
            arr[i] = this.dozerMapper.map(s[i], clz);
        }
        return arr;
    }


}
