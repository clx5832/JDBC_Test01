package com.clx.test.transaction;

import com.clx.test.utils.JDBC_Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.clx.test.transaction.JDBC_Transction.update;

public class testTrancionIslation {
    public static void main(String[] args) {
        testTrancionIslationRead();

        testTrancionIslationUpdate();
    }

    /**
     * 测试事务的隔离级别
     * 在JDBC程序中
     * 尝试将此 Connection对象的事务隔离级别更改为给定的对象。
     * 通过Connection的 setTransactionIsolation设置事务的隔离级别
     */
    public static void testTrancionIslationUpdate() {
        Connection connection = null;

        try {
            connection = JDBC_Utils.getConnection();

            //设置不会自动提交事务
            connection.setAutoCommit(false);
            String sql = "update users set balance = balance - 500 where id = 1";
            update(connection, sql);

            connection.commit();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 读取数据,即读取数据库当前的隔离级别和相关的信息
     */
    public static void testTrancionIslationRead() {

        String sql = "select balance from users where id =1 ";
        Integer balance = getForValue(sql);
        System.out.println(balance);
    }


    //返回某条记录的某一个字段，或一个统计的值(一共有多少条记录等)
    public static <E> E getForValue(String sql, Object... args) {

        //1.得到结果集,该结果集应该只有一行，且只有一列
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //1.获取Connection
            connection = JDBC_Utils.getConnection();

            System.out.println(connection.getTransactionIsolation());//查询当前的隔离级别

            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED );//提交的

            //2.获取获取PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            //4.进行查询，得到ResultSet
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return (E) resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(resultSet, preparedStatement, connection);
        }
        //2.取得结果集
        return null;
    }
}
