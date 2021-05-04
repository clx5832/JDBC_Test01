package com.clx.test.jdbc_yuanshuju;

import com.clx.test.utils.JDBC_Utils;

import java.io.IOException;
import java.sql.*;

public class DatabaseMetaData1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
        testResultSetMetaData();
    }

    /***
     * DataBaseMetaData是描述 数据库 的元数据对象
     * 可以由Connection得到
     * 了解：
     * */
    public static void testDataBaseMetaData() throws ClassNotFoundException, SQLException, InstantiationException, IOException, IllegalAccessException {
        Connection connection = null;
        java.sql.DatabaseMetaData databaseMetaData = null;

        ResultSet resultSet = null;

        try {
            connection = JDBC_Utils.getConnection();
            databaseMetaData = connection.getMetaData();

            //得以得到数据库本身的一些信息
            int version = databaseMetaData.getDatabaseMajorVersion();//得到数据库的版本号
            System.out.println(version);

            //得到连接到数据库的用户名
            String user = databaseMetaData.getUserName();
            System.out.println(user);

            //得到Mysql中有哪些数据库
            resultSet = databaseMetaData.getCatalogs();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
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
            JDBC_Utils.releaseResouse(resultSet, null, connection);
        }
    }


    /**
     * ResultSetMetaData描述结果集的元数据
     * 可以得到结果集中的基本信息：结果集中有哪些列，列名，列的别名
     *
     * 结合反射可以编写出通用的查询方法
     */
    public static void testResultSetMetaData() throws ClassNotFoundException, SQLException, InstantiationException, IOException, IllegalAccessException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        try {
            connection = JDBC_Utils.getConnection();
            String sql = " select id ID , name 名字 , emails 邮箱 , birth 生日 " +
                    " from customer ";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            //1.得到ResultSetMetaData对象
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            //2.得到列的个数
            int columnCount = resultSetMetaData.getColumnCount();
            System.out.println(columnCount);

            for (int i = 0; i < columnCount; i++) {
                //3.得到列名
                String columnName = resultSetMetaData.getColumnName(i + 1);

                //4.得到列的别名

                String columnLables = resultSetMetaData.getColumnLabel(i + 1);
                System.out.println(columnName + ":" + columnLables);
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

            JDBC_Utils.releaseResouse(resultSet, preparedStatement, connection);
        }

    }

}
