package com.atguigu.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * �ĵã� sun��˾д����һ�����ݿ����ӳؽӿ�DataSource������������֯д������ʵ�֣�һ����C3P0��һ����DBCP��
 *      C3P0���ӳ�ʹ��CombopooledDataSource���������������DBCP���ӳ�ʹ��BasicDataSource���������������
 *      ���󴴽���֮���ⲻ��Ҫ���߳�����Ҫʹ���ĸ����ݿ⣿����û��������룿����������������С����������ʼ�������ȵȡ�
 *      Ȼ�������ʽ�����ӳػ�ȡһ�����ݿ�����connection��
 *      ����connection�����������Լ�����ִ��sql������Ҫͨ������ȡһ��ִ�������Statement��ͨ��statement����ִ��sql��
 *      ������Ȼ��һȺ�ڿ�����ţ�Ƶļ����ҵ���statement�����©����ʵ����sqlע�빥����
 *      û�취�������ɣ����Է���ֱ��ʹ��Statement����ȥ���ñ����������Ķ���PrepareStatement��ȥִ��sql��
 * @author SYQ
 *
 */
public class TestC3p0DataSource {

	public static void main(String[] args) {
		ComboPooledDataSource c3p0 = new ComboPooledDataSource();
		try {
			c3p0.setDriverClass("com.mysql.jdbc.Driver");
			c3p0.setJdbcUrl("jdbc:mysql://localhost:3306/heros");
			c3p0.setUser("root");
			c3p0.setPassword("root");
			c3p0.setMaxPoolSize(40);
			c3p0.setMinPoolSize(2);
			c3p0.setInitialPoolSize(10);
			
			Connection connection = c3p0.getConnection();
			System.out.println(connection);
			String sql = "update hero set hero_price = 16800 where caller = '��̫��'";
			Statement statement = connection.createStatement();
			int executeUpdate = statement.executeUpdate(sql);
			System.out.println(executeUpdate);
			statement.close();
			
			String sql2 = "update hero set hero_price = 168 where caller = '��̫��'";
			PreparedStatement prepareStatement = connection.prepareStatement(sql2);
			int executeUpdate2 = prepareStatement.executeUpdate(sql2);
			System.out.println(executeUpdate2);
			prepareStatement.close();
			
			String sql3 = "select caller,hero_name from hero";
			PreparedStatement prepareStatement2 = connection.prepareStatement(sql3);
			ResultSet rs = prepareStatement2.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString("caller"));
				System.out.println(rs.getString("hero_name"));
			}
			prepareStatement2.close();
			
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
