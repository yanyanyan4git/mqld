package com.mqld.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class JdbcUtil {
	
	public static void release(ResultSet rs,Statement statement,Connection conn) {
		if (rs!=null) {
			try {
				rs.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			rs=null;
		}
		if (statement!=null) {
			try {
				statement.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			statement=null;
		}
		if (conn!=null) {
			try {
				conn.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			conn=null;
		}
	}
}
