package com.github.dbutils.mybatis.extend;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.dbutils.mybatis.utils.ExtendFeaturesUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;


/**
 * ExtendFeaturesBaseTypeHandler
 * @project dal-common
 * @author tony.shen
 * @date 2012-5-27 下午6:41:21
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
public class ExtendFeaturesBaseTypeHandler extends BaseTypeHandler<ExtendFeaturesMap<?, ?>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ExtendFeaturesMap<?, ?> parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, ExtendFeaturesUtil.convert(parameter));
    }

    @Override
    public ExtendFeaturesMap<?, ?> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.wasNull()) {
            return null;
        } else {
            return convert(rs.getString(columnName));
        }
    }

    @Override
    public ExtendFeaturesMap<?, ?> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.wasNull()) {
            return null;
        } else {
            return convert(rs.getString(columnIndex));
        }
    }

    @Override
    public ExtendFeaturesMap<?, ?> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.wasNull()) {
            return null;
        } else {
            return convert(cs.getString(columnIndex));
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private ExtendFeaturesMap<?, ?> convert(String json) {
        try {
            if (json == null) {
                return null;
            }
            return new ExtendFeaturesMap(ExtendFeaturesUtil.convert(json));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert json to Map by value:" + json, ex);
        }
    }
    
}
