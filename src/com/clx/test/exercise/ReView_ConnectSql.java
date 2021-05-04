package com.clx.test.exercise;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ReView_ConnectSql {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
//        testConnection();
        testgetConnection();
    }

    public static void testgetConnection() throws IOException, ClassNotFoundException, SQLException {
        //1.四个字符串
        String jdbcUrl = null;
        String user = null;
        String password = null;
        String driverClass = null;
        //2.加载配置文件，属性文件中对应的java中的Perproties类
        //可以使用类加载器加载bin目录下的文件

        InputStream inputStream = ReView_ConnectSql.class.
                getClassLoader().getResourceAsStream("jdbc2.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        user  = properties.getProperty("user");
        password = properties.getProperty("password");
        jdbcUrl = properties.getProperty("jdbcUrl");
        driverClass = properties.getProperty("driverClass");
        //3.加载驱动注册
        Class.forName(driverClass);

        //4.获取连接
        Connection connection =DriverManager.getConnection(jdbcUrl,user,password);
        System.out.println(connection+"连接数据库成功了!!!");
    }

    public static void testConnection() throws ClassNotFoundException, SQLException {
        //1.获取四个字符串
        String jdbcUrl = "jdbc:mysql://localhost:3306/person";
        String user = "root";
        String password = "root";
        String driverClass = "com.mysql.jdbc.Driver";
        //2.加载驱动Class.formName(driverClass)
        Class.forName(driverClass);
        //3.获取数据库连接,DriverManager.getConnection(jdbcUrl,user,password)
        Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
        //4.返回连接的结果
        System.out.println(connection + "连接数据库成功了!!!!");
    }
}
