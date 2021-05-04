package com.clx.test.study;

import com.clx.test.utils.JDBC_Utils;
import com.mysql.jdbc.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Add_Record {
    public static void main(String[] args) {
        try {
            testStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通用的更新的方法：包括insert,update,delete,增加，修改，删除的功能
     * 版本1.
     */
    public void update(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = JDBC_Utils.getConnection();
//            connection = getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            JDBC_Utils.releaseResouse(,statement, connection);
        }
    }

    /**
     * 向数据库中插入一条数据
     * 1:Statement:用于执行SQL语句的对象
     * 1).通过Connection的CreateStatement()方法来获取
     * 2)通过executeUpdate(sql)可以执行SQL语句
     * 3)传入的SQL可以是insret,update,delete,但不能是select
     * <p>
     * <p>
     * 2.Connection和Statement都是应用程序和数据库服务器的连接资源，使用后一定要关闭
     * 需要在finally中关闭Connect和Statement对象，异常可以不关闭，但是连接一定要关闭
     * <p>
     * <p>
     * 3.关闭的顺序：先关闭后获取的，即先关闭Statement 后关闭Connection
     */
    public static void testStatement() throws SQLException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {  //1.获取数据库的连接
        Statement statement = null;
        Connection connection = null;
        try {
            //获取Connection链接
            connection = getConnection();
            //3.准备插入的SQL语句,或者删除,或者更新
            String sql = null;
             sql = "insert into customer(id,name,emails,birth) " + "values('4',\'宋东起\',\'444444444444@qq.com\',\'1998-9-9\')";

            //准备删除的SQL语句
//            sql = "delete from customer where id > 0 and id < 10";

            //准备更新的SQL语句
//            sql = "update customer set name = '陈良项' where id = 10 or id = 11";
            //4.执行插入
            //1)获取操作SQL语句的Statement对象，调用Connection的createStatement()方法来获取
            statement = connection.createStatement();//调用java.sql包下的
            //2)调用Statement对象的executeUpdate(sql)执行SQL语句进行插入
            statement.executeUpdate(sql);

            try {
                if (statement != null)
                    //关闭statement对象
                    statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (connection != null)
                //2.关闭连接
                connection.close();

        }


    }


    public static Connection getConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, IOException {
        String driverClass = null;
        String user = null;
        String password = null;
        String jdbcUrl = null;

        //读取类路径下的jdbc.properties文件
        InputStream inputStream = JDBCTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

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
