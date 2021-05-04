package com.clx.test.utils;

import com.mysql.jdbc.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 工具的方法：
 * 操作JDBC 的工具类，其中封装了一些工具方法
 */
public class JDBC_Utils {

    /**
     * 通用的更新的方法：包括insert,update,delete,增加，修改，删除的功能
     * 版本2.
     * Object ... args是可变的参数
     * args是填写SQL占位符的可变参数
     */
    public static void update(String sql, Object... args) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBC_Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(null, preparedStatement, connection);
        }
    }

    /**
     * 通用的更新的方法：包括insert,update,delete,增加，修改，删除的功能
     * 版本1.
     */
    public static void update(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            //获取数据连接
            connection = getConnection();

            //调用Connection对象的createStatement()方法获取Statement
            statement = connection.createStatement();

            //发送SQL语句：调用Statement对象的executeUpdate（sql）方法
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(null, statement, connection);
        }
    }

    /**
     * 关闭数据库资源的
     * 关闭Statement和Connection
     */
    public static void releaseResouse(ResultSet resultSet, Statement statement, Connection connection) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取链接数据库的方法
     * 通过配置文件从数据库服务器获取一个链接
     */
    public static Connection getConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, IOException {
        String driverClass = null;
        String user = null;
        String password = null;
        String jdbcUrl = null;

        //读取类路径下的jdbc.properties文件
        InputStream inputStream = JDBC_Utils.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();

        properties.load(inputStream);

        driverClass = properties.getProperty("driver");

        jdbcUrl = properties.getProperty("jdbcUrl");

        user = properties.getProperty("user");

        password = properties.getProperty("password");

        //通过反射创建Driver对象
        Driver driver = (Driver) Class.forName(driverClass).newInstance();

        Properties info = new Properties();

        info.put("user", user);

        info.put("password", password);

        //通过Driver的connect获取数据库的连接
        Connection connection = driver.connect(jdbcUrl, info);
        System.out.println(connection + "连接数据库成功了！！！");

        return connection;
    }
}
