package com.atguigu.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 心得： sun公司写出了一套数据库连接池接口DataSource；有另两个组织写了两种实现，一个是C3P0，一个是DBCP。
 *      C3P0连接池使用CombopooledDataSource这个类来创建对象；DBCP连接池使用BasicDataSource这个类来创建对象。
 *      对象创建完之后免不了要告诉程序你要使用哪个数据库？你的用户名？密码？额外可以设置最大最小连接数、初始连接数等等。
 *      然后就是正式从连接池获取一个数据库连接connection。
 *      光有connection还不够，它自己不能执行sql，还需要通过它获取一个执行体对象Statement，通过statement才能执行sql。
 *      后来居然有一群黑客利用牛逼的技术找到了statement对象的漏洞，实现了sql注入攻击！
 *      没办法，升级吧，所以放弃直接使用Statement，而去利用比它更厉害的儿子PrepareStatement类去执行sql。
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
			String sql = "update hero set hero_price = 16800 where caller = '孙太和'";
			Statement statement = connection.createStatement();
			int executeUpdate = statement.executeUpdate(sql);
			System.out.println(executeUpdate);
			statement.close();
			
			String sql2 = "update hero set hero_price = 168 where caller = '孙太和'";
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
