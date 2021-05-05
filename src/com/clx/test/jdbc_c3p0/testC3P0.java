package com.clx.test.jdbc_c3p0;

import com.clx.test.utils.JDBC_Utils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class testC3P0 {
    public static void main(String[] args) throws Exception {
//        C3P0();
//        testC3p0powithConfigFile();
        testJdbc_utils();
    }

    /**测试JDBC_Utils中的C3P0方法
     * */
    private static void testJdbc_utils() throws Exception {
        Connection connection = JDBC_Utils.getConnection2();
        System.out.println(connection);
    }


    /**
     * 1.创建c3p0-config.xml文件,参考帮助文档中的Appendix B: Configuation Files, etc.
     * 2.创建ComboPooledDataSource实例： DataSource dataSource = new ComboPooledDataSource("helloc3p0");
     * 3.从DataSource获取数据库链接
     * */
    private static void testC3p0powithConfigFile() throws SQLException {
        DataSource dataSource = new ComboPooledDataSource("helloc3p0");
        System.out.println(dataSource.getConnection()+"链接数据库成功了！！！");

        ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource) dataSource;

        System.out.println(comboPooledDataSource.getMaxStatements());
    }

    public static void C3P0() throws SQLException, PropertyVetoException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();

            cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver

        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/person");
        cpds.setUser("root");
        cpds.setPassword("root");

        System.out.println(cpds.getConnection()+"链接数据库成功了!!!!");

    }
}
