package com.clx.test.study;

import com.mysql.jdbc.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTest {
    /**
     * 驱动Deiver是一个接口，数据库厂商必须提供的
     * 1.加入mysql驱动
     * 2.新建一个lib目录添加mysql.jar文件
     */
    public static void main(String[] args) {
        /**第一种方式
         * */
//        try {
//            //1.创建一个Driver实现类对象
//            Driver driver = new Driver();
////            Class.forName("com.mysql.jdbc.Driver");
//
//            //2.准备连接数据库的基本信息：url,user,password
//            String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&serverTimezone=UTC";//协议+子协议+子名称+端口号+数据库名字
//            Properties info = new Properties();
//            info.put("user", "root");
//            info.put("password", "root");
////            Connection connection = DriverManager.getConnection(url, info);
////            Connection connection = DriverManager.getConnection(url, "root", "root");
////            System.out.println(connection);
//
//
//            //3.调用Driver的Connecion接口的connect(url,info)获取数据库连接
//            Connection connection = (Connection) driver.connect(url, info);//driver.connect(url, info);可以得到connection对象
//
//            System.out.println(connection);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        /**第二种方式
         * */
//        try {
//            getConnection();
//            System.out.println(getConnection() + "连接数据库成功");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        /**第三种方式
         * */
//        try {
//            testDriverManager();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
        try {
            getConnection2();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 编写一个通用的方法，在不修改源程序的情况下，可以获取任何数据库的连接
     * 解决方案：把数据库驱动Driver实现类的全类名，url,user,password放入一个
     * 配置文件中，通过修改配置文件的方式实现和具体数据库解耦
     */

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

        return connection;
    }

    /**
     * DriverManager是驱动的管理类
     * 1.可以通过重载的getConnection()方法获取数据库连接，更为的方便
     * 2.可以同时管理多个驱动程序:若注册了多个数据库连接，则调用getConnect()方法时，则可以返回不同的数据库连接
     */
    public static  void testDriverManager() throws IOException, SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //准备连接数据库的Class
        //驱动的全类名
        String driverClass = null;
       //user
        String user = null;
        //password
        String password = null;
        //JDBC URL
        String jdbcUrl = null;

        //读取类路径下的jdbc.properties文件
        InputStream inputStream = JDBCTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();

        properties.load(inputStream);

        driverClass = properties.getProperty("driver");

        jdbcUrl = properties.getProperty("jdbcUrl");

        user = properties.getProperty("user");

        password = properties.getProperty("password");

        //加载数据库程序驱动程序（注册驱动）
        Class.forName(driverClass);
        //加载数据库程序驱动程序（注册驱动）第二种方式
//        DriverManager.registerDriver((java.sql.Driver) Class.forName(driverClass).newInstance());

        //通过DriverManager的getConnection()获取数据库的连接
        Connection connection = DriverManager.getConnection(jdbcUrl, user, password);

        System.out.println(connection);
    }

    public static void getConnection2() throws IOException, SQLException, ClassNotFoundException {
        //1.准备连接的数据库的四个字符串
        //准备连接数据库的Class
        //驱动的全类名
        String driverClass = null;
        //user
        String user = null;
        //password
        String password = null;
        //JDBC URL
        String jdbcUrl = null;

        //2)获取jdbc.properties对应的输入流
           InputStream inputStream = JDBCTest.class.getClassLoader().
                   getResourceAsStream("jdbc.properties");
        //1)创建Properties对象
        Properties properties = new Properties();
        //3)加载2）中对应的输入流
        properties.load(inputStream);
        //4)具体决定user,password等四个字符串
        driverClass = properties.getProperty("driver");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
        jdbcUrl = properties.getProperty("jdbcUrl");

        //2.加载数据库的驱动程序(对应的Driver实现类中有注册驱动的静态代码块)
        Class.forName(driverClass);

        //3.通过DriverManager的getConnection()方法获取数据库连接
        Connection connection = DriverManager.getConnection(jdbcUrl,user,password);
        System.out.println(connection+"连接数据库成功！！！");
    }

}
