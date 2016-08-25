package com.atguigu.jdbc;

import java.sql.Connection;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;


public class TestDBCPDataSource {

	public static void main(String[] args) {
		BasicDataSource dbcp = new BasicDataSource();
		Connection connection = null;
		try {
			dbcp.setDriverClassName("com.mysql.jdbc.Driver");
			dbcp.setUrl("jdbc:mysql://localhost:3306/heros");
			dbcp.setUsername("root");
			dbcp.setPassword("root");
			dbcp.setInitialSize(10);
			dbcp.setMaxActive(40);
			dbcp.setMinIdle(2);
			
			connection = dbcp.getConnection();
			System.out.println(connection);
			System.out.println(connection.getClass());
			Statement statement = connection.createStatement();
			String sql = "insert into hero (caller,hero_name,primary_skill,hero_price) values ('ËïÌ«','Ëïë÷','Õð±¬µ¯',6800)";
			int executeUpdate = statement.executeUpdate(sql);
			System.out.println(executeUpdate);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally{
			try {
				connection.close();
			} catch (Exception e2) {
			}
		}
	}

}
