package com.clx.test.main_key_values;

import com.clx.test.utils.JDBC_Utils;

import java.io.IOException;
import java.sql.*;

public class Main_Key_Values_Test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
        Auto_make_Key_Values();
    }

    /**
     * 取得数据库自动生成的主键
     */
    public static void Auto_make_Key_Values() throws ClassNotFoundException, SQLException, InstantiationException, IOException, IllegalAccessException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBC_Utils.getConnection();
            String sql = " insert into customer( name , emails , birth )" +
                    " values ( ? , ? , ?)";
//            preparedStatement = connection.prepareStatement(sql);
            //返回主键值
            //使用重载的方法prepareStatement(sql,flag)
            //来生成preparedStatement对象
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            preparedStatement.setObject(1,"11");

//            preparedStatement.setObject(1,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, "覃旭贤");
            preparedStatement.setObject(2, "123456789@qq.com");
            preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));

            preparedStatement.executeUpdate();
            //获取结果集
            //通过.getGeneratedKeys()获取了包含新生成的主键ResultSet 对象
            //在ResultSet中只有一列Generated_Key,用来存放新生成的主键值
             resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                System.out.println(resultSet.getObject(1));
            }
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                System.out.println(resultSetMetaData.getColumnName(i + 1));
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
