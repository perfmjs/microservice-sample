package com.github.dbutils.mybatis.extend;

import com.github.dbutils.mybatis.utils.BitUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.BitSet;


/**
 * BitFlagBaseTypeHandler
 * @project dal-common
 * @author tony.shen
 * @date 2012-5-27 下午5:59:13
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
public class BitFlagBaseTypeHandler<E extends BitSet> extends BaseTypeHandler<E> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, BitUtil.convert(parameter));
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.wasNull()) {
            return null;
        } else {
            return convert(rs.getLong(columnName));
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.wasNull()) {
            return null;
        } else {
            return convert(rs.getLong(columnIndex));
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.wasNull()) {
            return null;
        } else {
            return convert(cs.getLong(columnIndex));
        }
    }

    @SuppressWarnings({"unchecked" })
    private E convert(Long bitSetValue) {
        try {
            return (E) BitUtil.convert(bitSetValue);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + bitSetValue + " to BitSet by value:" + bitSetValue, ex);
        }
    }
 
}
