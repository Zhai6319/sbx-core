package com.sbx.core.mybatis.handler;

import com.baomidou.mybatisplus.core.toolkit.AES;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

///**
// * <p>说明：</p>
// *
// * @author Z.jc
// * @version 1.0.0
// * @since 2021/2/20
// */
//@Service
//@MappedTypes({Object.class})
//@MappedJdbcTypes({JdbcType.VARCHAR})
//public class EncryptionTypeHandler extends BaseTypeHandler<String> {
//
//    @Value("${data.encryption.secretKey}")
//    private String secretKey;
//
//
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
//        ps.setString(i, AES.encrypt(parameter,secretKey));
//    }
//    @Override
//    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        String columnValue = rs.getString(columnName);
//        return StringUtils.isBlank(columnValue) ? columnValue : AES.decrypt(columnValue,secretKey);
//    }
//    @Override
//    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        String columnValue = rs.getString(columnIndex);
//        return StringUtils.isBlank(columnValue) ? columnValue : AES.decrypt(columnValue,secretKey);
//    }
//    @Override
//    public String getNullableResult(CallableStatement cs, int columnIndex)
//            throws SQLException {
//        String columnValue = cs.getString(columnIndex);
//        return StringUtils.isBlank(columnValue) ? columnValue : AES.decrypt(columnValue,secretKey);
//    }
//}
