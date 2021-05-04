package com.clx.test.batch_processing;

import com.clx.test.utils.JDBC_Utils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.io.IOException;
import java.sql.*;

public class TestBatch {
    public static void main(String[] args) {
        testBatchWithPrepareStatement();
    }

    /**
     * 向Oracle的customers数据表中插入10万条记录
     * 测试如何插入
     * 1.使用Statement
     */
    public static void testBatchWithStatement() {
        Connection connection = null;
        Statement statement = null;
        String sql = "";

        try {
            connection = JDBC_Utils.getConnection();
            statement = connection.createStatement();

            long begin = System.currentTimeMillis();
            for (int i = 0; i <= 10000; i++) {
                sql = " insert into customers values( " + (i + 1) + ", 'name_" + i + "','2021-8-9')";

                statement.executeUpdate(sql);
            }
            long end = System.currentTimeMillis();

            System.out.println("Time: " + (end - begin));

            JDBC_Utils.commit(connection);
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
            JDBC_Utils.releaseResouse(null, statement, connection);
        }
    }

    public static void testBatchWithPrepareStatement() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "";

        try {
            connection = JDBC_Utils.getConnection();
            sql = " insert into customers values( ? , ? , ? )";
            preparedStatement = connection.prepareStatement(sql);
            long begin = System.currentTimeMillis();

            System.out.println("begin:"+begin);
            for (int i = 0; i <= 100; i++) {

                preparedStatement.setInt(1, i + 1);
                preparedStatement.setString(2, "name:" + i);
                preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
//                preparedStatement.executeUpdate();

                //积攒SQL
                preparedStatement.addBatch();

                //当 ”积攒“ 到一定程度，被统一的执行一次，并且清空先前的 “积攒” 的SQL
                if (((i + 1) % 10) == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }

            //若总条数不是批量数据的整数倍，则还需要在额外
            if ((100 % 10) != 0) {
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }

            long end = System.currentTimeMillis();

            System.out.println("end:"+end);

            System.out.println("Time: " + (end - begin));

            JDBC_Utils.commit(connection);
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
            JDBC_Utils.releaseResouse(null, preparedStatement, connection);
        }
    }
}
