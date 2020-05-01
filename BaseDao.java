package com.pb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class BaseDao {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/t111shop";
	public static final String USER = "root";
	public static final String PASSWORD = "mysql";
	
	//统一的加载驱动获得连接的方法
	public Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	//统一的关闭资源的方法
	public void closeResource(Connection conn,Statement stmt,ResultSet rs){
		try {
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//统一的增删改方法
	public int executeUpdate(String sql,Object[] objs){
		int row = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			if(objs!=null){
				for(int i=0;i<objs.length;i++){
					if(objs[i] instanceof java.util.Date){
						objs[i] = new Timestamp(((java.util.Date)objs[i]).getTime());
					}
					ps.setObject(i+1, objs[i]);
				}
			}
			row = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeResource(conn, ps, null);
		}
		return row;
	}
	
}










