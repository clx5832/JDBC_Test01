package com.clx.test.transaction;

import com.clx.test.jdbc_dao.Jdbc_Dao_Design;
import com.clx.test.utils.JDBC_Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC_Transction {
    public static void main(String[] args) {
        test_Transcation();
    }


    /**
     * Tom给Jerry汇款500元
     * <p>
     * 关于事务：
     * 1.如果多个操作，每个操作使用的是自己单独的连接，则无法保证事务
     * 2.具体步骤：
     * 1）事务操作考试前，开始事务：取消Connection 的默认提交行为
     *   connection.setAutoCommit(false);
     *
     * 2）如果事务的操作都成功，则提交事务
     *  //提交事务
     *    connection.commit();
     *
     * 3）回滚事务：若出现异常，则在catch块中回滚事务
     */
    public static void test_Transcation() {
        Connection connection = null;


        try {
            connection = JDBC_Utils.getConnection();

            //1.开始事务：取消默认提交
            connection.setAutoCommit(false);
            String sql = "update users set balance = balance - 500 where id = 1";
            update(connection, sql);

//            int i = 10 / 0;
//            System.out.println(i);

            sql = "update users set balance = balance + 500 where id = 2";
            update(connection, sql);

            //提交事务
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();

            //回滚事务
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            JDBC_Utils.releaseResouse(null, null, connection);
        }

//        String sql = "update users set balance = balance - 500 where id = 1";
//        Jdbc_Dao_Design.update(sql);
//
//        int i = 10 / 0 ;
//        System.out.println(i);
//
//        sql = "update users set balance = balance + 500 where id = 2";
//        Jdbc_Dao_Design.update(sql);


//提交事务的模板
//        try {
//
//
//            //1.开始事务：取消默认提交
//            connection.setAutoCommit(false);
//           //....
//
//            //提交事务
//            connection.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            //回滚事务
//            //.....
//            connection.rollback();
//        } finally {
//            JDBC_Utils.releaseResouse(null, null, connection);
//        }
//    }
    }
    public static void update(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;

        try {
//            connection = JDBC_Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(1 + i, args[i]);
            }
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(null, preparedStatement, null);
        }
    }


}
