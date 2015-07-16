package com.github.dbutils.mybatis.extend;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class CalendarTypeHandler extends BaseTypeHandler<Calendar> {

	@Override
	public Calendar getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Calendar cdate = Calendar.getInstance();
		java.sql.Timestamp date = rs.getTimestamp(columnName);
		cdate.setTime(date);
		return cdate;
	}

	@Override
	public Calendar getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Calendar cdate = Calendar.getInstance();
		java.sql.Timestamp date = cs.getTimestamp(columnIndex);
		cdate.setTime(date);
		return cdate;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Calendar parameter,
			JdbcType jdbcType) throws SQLException {
		ps.setTimestamp(i, new java.sql.Timestamp((parameter).getTimeInMillis()));
	}

    @Override
    public Calendar getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Calendar cdate = Calendar.getInstance();
        java.sql.Timestamp date = rs.getTimestamp(columnIndex);
        cdate.setTime(date);
        return cdate;
    }

}
