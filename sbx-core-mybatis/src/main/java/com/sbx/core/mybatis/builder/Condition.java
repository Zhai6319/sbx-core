package com.sbx.core.mybatis.builder;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sbx.core.model.params.Query;
import org.apache.commons.lang3.StringUtils;
import com.sbx.core.tool.util.Func;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/4/10
 */
public class Condition {

    private Condition(){}

    public static Condition getInstance() {
        return new Condition();
    }

    /**
     * 获取page参数
     * @param query
     * @return
     */
    public IPage getPage(Query query){
        Page page = new Page(query.getCurrent(),query.getSize());
        if (StringUtils.isNotBlank(query.getAscS())){
            page.addOrder(OrderItem.ascs(Func.toStrArray(query.getAscS())));
        }
        if (StringUtils.isNotBlank(query.getDescS())) {
            page.addOrder(OrderItem.descs(Func.toStrArray(query.getAscS())));
        }
        return page;
    }


}
