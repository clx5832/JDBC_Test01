package com.clx.test.study;

import com.clx.test.utils.JDBC_Utils;
import com.mysql.jdbc.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class Select_Sql_Test {
    public static void main(String[] args) {
        testResultSet();
    }

    /**
     * ResultSet:结果集，封装了使用JDBC进行查询的结果
     * 1.调用Statement对象的executeQuery(sql)可以得到结果集
     * <p>
     * 2.ResultSet实际上就是一个数据表, 有一个指针指向数据表的第一行的前面
     * 可以调用next()检测下一行是否有效,若有效该方法返回true，且指针下移，相当于
     * Iterator 对象的 hasNext()和 next() 方法的结合体
     * <p>
     * 3.当指针定位到一行时，可以通过调用getXXX(index ) 或者 getXxx(columnName)获取每一列的值，
     * 例如：getInt(1),getString("name")
     * <p>
     * 4.ResultSet 当然也需要进行关闭
     */
    public static void testResultSet() {
        //获取数据表customters的id = 4的数据表的记录，并且打印出来
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;


        try {
            //1.获取connection
            connection = JDBC_Utils.getConnection();
            System.out.println(connection);

            //2.获取Statement
            statement = connection.createStatement();
            System.out.println(statement);
            //3.准备SQL

//            Scanner scanner = new Scanner(System.in);
//            System.out.println("请输入想要查询的信息：\n");
//            String info = scanner.next();
//            String info2 = scanner.next();
            String sql = "select FlowId , type , IDCard , ExamCard , StudentName , location , gradle  " + "from examstudent ";

            //4.执行查询，得到ResultSet
            resultSet = statement.executeQuery(sql);
            System.out.println(resultSet);
            //5.处理ResultSet
            while (resultSet.next()) {
//                int id = resultSet.getInt(1);
//                String name = resultSet.getString("name");
//                String emails = resultSet.getString(3);
//                Date birth = resultSet.getDate(4);

//                System.out.println(id);
//                System.out.println(name);
//                System.out.println(emails);
//                System.out.println(birth);
                int FlowId = resultSet.getInt(1);
                int type = resultSet.getInt(2);
                String IDCard = resultSet.getString(3);
                String ExamCard = resultSet.getString(4);
                String StudentName = resultSet.getString(5);
                String location = resultSet.getString(6);
                int gradle = resultSet.getInt(7);

                System.out.println("查询数据成功");
                System.out.println(FlowId);
                System.out.println(type);
                System.out.println(IDCard);
                System.out.println(ExamCard);
                System.out.println(StudentName);
                System.out.println(location);
                System.out.println(gradle);
            }

            //6.关闭数据库资源
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(resultSet, statement, connection);
        }

    }
}
