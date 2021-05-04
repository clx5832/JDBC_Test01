package com.clx.test.dbcp;


import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class testDBCP {
    public static void main(String[] args) throws SQLException {
        testDBCP();
    }

    /**
     * 使用DBCP数据库连接池
     * 1.加入jar包
     * 2.创建数据库连接池
     * */
    public static void testDBCP() throws SQLException {
        BasicDataSource dataSource = null;

        //1.创建DBPC数据源实例
        dataSource = new BasicDataSource();

        //2.为数据源实例指定必须的属性
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://localhost:3306/person");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        //3.从数据源中获取数据库连接
        Connection connection = dataSource.getConnection();

        System.out.println(connection);
    }
}
