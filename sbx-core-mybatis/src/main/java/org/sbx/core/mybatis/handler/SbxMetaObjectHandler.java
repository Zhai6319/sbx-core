package org.sbx.core.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.sbx.core.security.utils.AuthUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/4/11
 */
@Slf4j
@Component
public class SbxMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject,"createTime", Date.class, new Date());
        this.strictInsertFill(metaObject,"updateTime", Date.class, new Date());
        if (Objects.nonNull(AuthUtil.getUserId())) {
            this.strictInsertFill(metaObject,"updateUserId", Long.class, AuthUtil.getUserId());
            this.strictInsertFill(metaObject,"createUserId", Long.class, AuthUtil.getUserId());
        }
        this.strictInsertFill(metaObject,"isDeleted",Boolean.class,false);
        // 也可以使用(3.3.0 该方法有bug请升级到之后的版本如`3.3.1.8-SNAPSHOT`)
//        this.fillStrategy(metaObject, "createTime", LocalDateTime.now());
        /* 上面选其一使用,下面的已过时(注意 strictInsertFill 有多个方法,详细查看源码) */
        //this.setFieldValByName("operator", "Jerry", metaObject);
        //this.setInsertFieldValByName("operator", "Jerry", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 起始版本 3.3.0(推荐使用)
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
        if (Objects.nonNull(AuthUtil.getUserId())) {
            this.strictInsertFill(metaObject,"updateUserId",Long.class, AuthUtil.getUserId());
        }
        // 也可以使用(3.3.0 该方法有bug请升级到之后的版本如`3.3.1.8-SNAPSHOT`)
//        this.fillStrategy(metaObject, "updateTime", LocalDateTime.now());
        /* 上面选其一使用,下面的已过时(注意 strictUpdateFill 有多个方法,详细查看源码) */
        //this.setFieldValByName("operator", "Tom", metaObject);
        //this.setUpdateFieldValByName("operator", "Tom", metaObject);
    }
}
