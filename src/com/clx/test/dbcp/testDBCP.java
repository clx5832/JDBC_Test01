package com.clx.test.dbcp;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class testDBCP {
    public static void main(String[] args) throws Exception {
//        testDBCPWithDataSourceFactory();
        testDBCP();
    }

    /**
     * 使用DBCP数据库连接池
     * 1.加入jar包(两个jar包)，依赖于Commons pool的Jar包
     * 2.创建数据库连接池
     * 3.为数据源实例指定必须的属性
     * 4.从数据源中获取数据库连接
     */
    public static void testDBCP() throws SQLException {
        BasicDataSource dataSource = null;

        //1.创建DBPC数据源实例
        dataSource = new BasicDataSource();

        //2.为数据源实例指定必须的属性
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://localhost:3306/person");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        //3.指定数据源的一些属性
        //1)指定数据库连接池初始化连接数的个数
        dataSource.setInitialSize(10);
        //2)指定最大的连接数:同一时刻可以同时向数据库申请的连接数
        //dataSource.setMaxActive(100)
        dataSource.setMaxIdle(10);
        //3)指定最小的连接数:在数据库连接池空闲状态下，连接池中最少有多少个连接的数量
        dataSource.setMinIdle(1);

        //4)等待数据库连接时分配连接的最长时间，单位为毫秒
        dataSource.setMaxWaitMillis(1000 * 5);


        //4.从数据源中获取数据库连接
        Connection connection = dataSource.getConnection();

        System.out.println(connection.getClass());

        //4.从数据源中获取数据库连接
        Connection connection2 = dataSource.getConnection();

        System.out.println(connection2.getClass());

        //4.从数据源中获取数据库连接
        Connection connection3 = dataSource.getConnection();

        System.out.println(connection3.getClass());

        //4.从数据源中获取数据库连接
        Connection connection4 = dataSource.getConnection();

        System.out.println("4444444444" + connection4.getClass());

        //4.从数据源中获取数据库连接
        Connection connection5 = dataSource.getConnection();

        System.out.println(connection5.getClass());

        /**获取连接在另外一个线程中
         * */
        BasicDataSource finalDataSource = dataSource;
        new Thread() {
            @Override
            public void run() {
                super.run();
                Connection connection1 = null;
                try {
                    connection1 = finalDataSource.getConnection();
                    System.out.println(connection1.getClass());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.start();

        //让其休眠3秒钟
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }

        //释放链接
        connection.close();
    }


    /**
     * 1.加载dbcp的properties 配置文件：配置文件中的键需要来自BasicDataSource的属性
     * <p>
     * 2.调用BasicDataSourceFactory。createDataSource方法创建DataSource实例
     * <p>
     * 3.从DataSource获取实例
     */
    public static void testDBCPWithDataSourceFactory() throws Exception {
        InputStream inputStream = testDBCP.class.getClassLoader().getResourceAsStream("dbcp.properties");

        Properties properties = new Properties();
        properties.load(inputStream);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);

        System.out.println(dataSource.getConnection());

        BasicDataSource basicDataSource = (BasicDataSource) dataSource;

        System.out.println(basicDataSource.getMaxWaitMillis());

        System.out.println(basicDataSource.getInitialSize());


    }
}