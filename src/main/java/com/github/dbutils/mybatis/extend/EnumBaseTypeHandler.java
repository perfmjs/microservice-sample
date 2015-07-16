package com.github.dbutils.mybatis.extend;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.dbutils.mybatis.enums.EnumWrapper;
import com.github.dbutils.mybatis.enums.IEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;


/**
 * EnumBaseTypeHandler
 * @project dal-common
 * @author tony.shen
 * @date 2012-5-27 下午2:32:30
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
public class EnumBaseTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private Class<E> enumClass;
    
    public EnumBaseTypeHandler(Class<E> enumClass) {
        this.enumClass = enumClass;
    }
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, ((IEnum) parameter).getIndex());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.wasNull()) {
            return null;
        } else {
            return convert(rs.getInt(columnName));
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.wasNull()) {
            return null;
        } else {
            return convert(rs.getInt(columnIndex));
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.wasNull()) {
            return null;
        } else {
            return convert(cs.getInt(columnIndex));
        }
    }

    @SuppressWarnings({"unchecked" })
    private E convert(int index) {
        try {
            return (E) EnumWrapper.newEnumWrapper(this.enumClass).getEnum(index);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + index + " to " + this.enumClass.getSimpleName() + " by index value:" + index, ex);
        }
    }
    
}
