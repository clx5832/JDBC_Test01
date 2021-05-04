package com.clx.test.exercise;

import com.clx.test.utils.JDBC_Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Query_Test {
    private static int input = 0;

    private static String sql = null;

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
        Query_Test();
    }

    public static void Query_Test() throws ClassNotFoundException, SQLException, InstantiationException, IOException, IllegalAccessException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        //1.获取Connection连接
        connection = JDBC_Utils.getConnection();

        //2.获取Statement
        statement = connection.createStatement();

        //3.准备SQL
        //1)//键盘输入的语句
        Scanner scanner = new Scanner(System.in);
        System.out.println("\t请输入您要输入的类型\t");
        System.out.println("\t\ta:准考证号\t\t");
        System.out.println("\t\tb:省份证号\t\t");
        String info = scanner.next();
        char info1 = info.charAt(0);

        int i = 1;
        while (i != 0) {
            if ((info1 == 'a' || info1 == 'b')) {

                switch (info) {
                    case "a":
                        System.out.println("请输入准考证号:\n");
                        input = scanner.nextInt();
                        break;
                    case "b":
                        System.out.println("请输入省份证号:\n");
                        input = scanner.nextInt();
                        break;
                }
                break;
            } else {
                System.out.println("您的输入有误！请重新进入程序...");
                break;
            }
        }
        resultSet = getResultSet(statement);


        //6.关闭数据库资源
        try {

        } catch (Exception e) {

        } finally {
            JDBC_Utils.releaseResouse(resultSet, statement, connection);
        }

    }

    private static ResultSet getResultSet(Statement statement) throws SQLException {
        ResultSet resultSet;//2)编写数据库查询语句
        sql = "select FlowId , type , IDCard , ExamCard , StudentName , location , gradle " + "from student where IDCard = " + input +" or ExamCard = " + input;
//        sql = "select FlowId , type , IDCard , ExamCard , StudentName , location , gradle " + " from student";

        //4.执行查询，得到ResultSet
        resultSet = statement.executeQuery(sql);

        //5.处理ResultSet
        while (resultSet.next()) {
            int FlowId = resultSet.getInt(1);
            int type = resultSet.getInt(2);
            String IDCard = resultSet.getString(3);
            String ExamCard = resultSet.getString(4);
            String StudentName = resultSet.getString(5);
            String location = resultSet.getString(6);
            int gradle = resultSet.getInt(7);

            System.out.println("查询数据成功");
            System.out.print("\t"+"id号：" + FlowId);
            System.out.print("\t"+"等级：" + type);
            System.out.print("\t"+"身份证号：" + IDCard);
            System.out.print("\t"+"准考证号：" + ExamCard);
            System.out.print("\t"+"学生名字：" + StudentName);
            System.out.print("\t"+"区域:" + location);
            System.out.print("\t"+"成绩:" + gradle);
            System.out.println("\n");
        }
        return resultSet;
    }
}
