package com.sbx.core.tool.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sbx.core.model.base.result.PageResult;
import com.sbx.core.model.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


/**
 * zouli修改
 *
 * @ClassName ObjectUtils
 * @Description 对象相关工具类
 * @date Mar 1, 2017 3:36:11 PM
 */
@Slf4j
public class ObjectUtils {

    /**
     * 将一个对象中的属性跟值保存在Map<String, Object>中 如果值为null不保存
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> entityToMap(Object obj) throws Exception {
        Class clazz = obj.getClass();
        Field[] fs = clazz.getDeclaredFields();
        Map<String, Object> map = new HashMap<String, Object>();
        for (Field f : fs) {
            f.setAccessible(true); //设置些属性是可以访问的
            Object val = null;
            try {
                val = f.get(obj);//得到此属性的值
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("entityToMap error");
            }

            if (val != null && !f.getName().equals("serialVersionUID")) {
                map.put(f.getName(), val);
            }
        }

        return map;
    }

    /**
     * map 转化为对象
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T mapToEntity(Map<String, Object> map, Class clazz) throws Exception {
        T t = null;
        try {
            t = (T) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("mapToEntity error");
        }
        Field[] fs = clazz.getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);
            Object obj = map.get(f.getName());
            if (obj != null) {
                try {
                    f.set(t, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("mapToObject error");
                }
            }
        }

        return t;
    }


    public static Object getObjByBeanMap(Object map, String token) {
        if (StringUtils.isEmpty(token)) {
            return map;
        }
        if (token.contains(".")) {
            String t2 = token.substring(token.indexOf(".") + 1);
            token = token.substring(0, token.indexOf("."));
            if (map instanceof Map) {
                Object obj = ((Map) map).get(token);
                return getObjByBeanMap(obj, t2);
            } else {
                Object obj = BeanMap.create(map).get(token);
                return getObjByBeanMap(obj, t2);
            }
        }
        if (map instanceof Map) {
            return ((Map) map).get(token);
        } else {
            return BeanMap.create(map).get(token);
        }
    }

    public static Object getObjByJson(Object map, String token) {
        if (StringUtils.isEmpty(token)) {
            return map;
        }
        Map<String, Object> vMap;
        if (map instanceof Map) {
            vMap = (Map<String, Object>) map;
        } else {
            vMap = JSON.parseObject(JSON.toJSONString(map), new TypeReference<Map<String, Object>>() {
            });
        }
        if (token.contains(".")) {
            String t2 = token.substring(token.indexOf(".") + 1);
            token = token.substring(0, token.indexOf("."));
            Object obj = vMap.get(token);
            return getObjByJson(obj, t2);
        }
        return vMap.get(token);
    }


    public static Map<String, Object> setObj(Map<String, Object> map, String token, Object value) {
        if (token.contains(".")) {
            String t2 = token.substring(token.indexOf(".") + 1);
            token = token.substring(0, token.indexOf("."));
            if (map.get(token) == null) {
                map.put(token, new HashMap<String, Object>());
            }
            map.put(token, setObj((Map<String, Object>) map.get(token), t2, value));
            return map;
        }
        map.put(token, value);
        return map;
    }


    public static <S,T> List<T> copyList(List<S> sourceList, Class<T> targetClass) {
        List<T> targetList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sourceList)) {
            return targetList;
        }
        for (S source : sourceList) {
            targetList.add(copy(source, targetClass));
        }
        return targetList;
    }

    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> targetClass, CopyHandler<S, T> handler) {
        List<T> targetList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sourceList)) {
            return targetList;
        }
        for (S source : sourceList) {
            targetList.add(copy(source, targetClass, handler));
        }
        return targetList;
    }

    public static <S, T> PageResult<T> copyPage(PageResult<S> sourcePageResult, Class<T> targetClass) {
        List<T> targetList = new ArrayList<>();
        PageResult<T> targetPageResult = new PageResult<>();
        List<S> sourceList = sourcePageResult.getRecords();
        targetPageResult.setCurrent(sourcePageResult.getCurrent());
        targetPageResult.setPages(sourcePageResult.getPages());
        targetPageResult.setTotal(sourcePageResult.getTotal());
        targetPageResult.setSize(sourcePageResult.getSize());
        targetPageResult.setRecords(new ArrayList<>());
        if (CollectionUtils.isEmpty(sourceList)) {
            return targetPageResult;
        }
        for (S source : sourceList) {
            targetList.add(copy(source, targetClass));
        }
        targetPageResult.setRecords(targetList);
        return targetPageResult;
    }

    public static <S, T> PageResult<T> copyPage(PageResult<S> sourcePageResult, Class<T> targetClass, CopyHandler<S, T> handler) {
        List<T> targetList = new ArrayList<>();
        PageResult<T> targetPageResult = new PageResult<>();
        List<S> sourceList = sourcePageResult.getRecords();
        targetPageResult.setCurrent(sourcePageResult.getCurrent());
        targetPageResult.setPages(sourcePageResult.getPages());
        targetPageResult.setTotal(sourcePageResult.getTotal());
        targetPageResult.setSize(sourcePageResult.getSize());
        targetPageResult.setRecords(new ArrayList<>());
        if (CollectionUtils.isEmpty(sourceList)) {
            return targetPageResult;
        }
        for (S source : sourceList) {
            targetList.add(copy(source, targetClass, handler));
        }
        targetPageResult.setRecords(targetList);
        return targetPageResult;
    }

    public static <S, T> T copy(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("copy bean failed");
        }
    }


    public static <S, T> T copy(S source, Class<T> targetClass, CopyHandler<S, T> handler) {
        try {
            T target = copy(source, targetClass);
            if (source != null) {
                handler.execute(source, target);
            }
            return target;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("copy bean failed");
        }
    }

    public static <S, T> T copyDeep(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            String json = JSON.toJSONString(source);
            return JSON.parseObject(json, targetClass);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("copy bean failed");
        }
    }

    public static <S, T> T copyDeep(S source, Class<T> targetClass, CopyHandler<S, T> handler) {
        try {
            T target = copyDeep(source, targetClass);
            if (source != null) {
                handler.execute(source, target);
            }
            return target;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("copy bean failed");
        }
    }

    public static <S,T> List<T> copyListDeep(List<S> sourceList, Class<T> targetClass) {
        List<T> targetList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sourceList)) {
            return targetList;
        }
        for (S source : sourceList) {
            targetList.add(copyDeep(source, targetClass));
        }
        return targetList;
    }

    public static <S, T> List<T> copyListDeep(List<S> sourceList, Class<T> targetClass, CopyHandler<S, T> handler) {
        List<T> targetList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sourceList)) {
            return targetList;
        }
        for (S source : sourceList) {
            targetList.add(copyDeep(source, targetClass, handler));
        }
        return targetList;
    }

    public static <S, T> PageResult<T> copyPageDeep(PageResult<S> sourcePageResult, Class<T> targetClass) {
        List<T> targetList = new ArrayList<>();
        PageResult<T> targetPageResult = new PageResult<>();
        List<S> sourceList = sourcePageResult.getRecords();
        targetPageResult.setCurrent(sourcePageResult.getCurrent());
        targetPageResult.setPages(sourcePageResult.getPages());
        targetPageResult.setTotal(sourcePageResult.getTotal());
        targetPageResult.setSize(sourcePageResult.getSize());
        targetPageResult.setRecords(new ArrayList<>());
        if (CollectionUtils.isEmpty(sourceList)) {
            return targetPageResult;
        }
        for (S source : sourceList) {
            targetList.add(copyDeep(source, targetClass));
        }
        targetPageResult.setRecords(targetList);
        return targetPageResult;
    }

    public static <S, T> PageResult<T> copyPageDeep(PageResult<S> sourcePageResult, Class<T> targetClass, CopyHandler<S, T> handler) {
        List<T> targetList = new ArrayList<>();
        PageResult<T> targetPageResult = new PageResult<>();
        List<S> sourceList = sourcePageResult.getRecords();
        targetPageResult.setCurrent(sourcePageResult.getCurrent());
        targetPageResult.setPages(sourcePageResult.getPages());
        targetPageResult.setTotal(sourcePageResult.getTotal());
        targetPageResult.setSize(sourcePageResult.getSize());
        targetPageResult.setRecords(new ArrayList<>());
        if (CollectionUtils.isEmpty(sourceList)) {
            return targetPageResult;
        }
        for (S source : sourceList) {
            targetList.add(copyDeep(source, targetClass, handler));
        }
        targetPageResult.setRecords(targetList);
        return targetPageResult;
    }


    public interface CopyHandler<S,T>{
        public void execute(S source,T target);
    }




    public static Type getGenericInterfaceType(Class<?> clazz, Class<?> defType) {

        if (Objects.isNull(clazz) || clazz.getGenericInterfaces().length <= 0) {
            return defType;
        }

        for (Type t : clazz.getGenericInterfaces()) {
            if (t instanceof ParameterizedType) {
                Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                if (args[0] instanceof Class) {
                    return args[0];
                }
            }

        }

        return defType;
    }

}
