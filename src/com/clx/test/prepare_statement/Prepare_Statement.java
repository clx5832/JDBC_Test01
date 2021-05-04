package com.clx.test.prepare_statement;

import com.clx.test.utils.JDBC_Utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class Prepare_Statement {
    public static void main(String[] args) {
        PreparedStatement();
    }
    /**为什么？
     * 1)
     * 1>使用Statement需要拼写SQL语句，很辛苦，而且容易出错哦,不利于维护
     * 2>可以有效放置SQL注入：SQL注入攻击是黑客对数据库进行攻击的常用手段之一。
     * 随着B/S模式应用开发的发展，使用这种模式编写应用程序的程序员也越来越多。
     * 但是由于程序员的水平及经验也参差不齐，相当大一部分程序员在编写代码的时候，
     * 没有对用户输入数据的合法性进行判断，使应用程序存在安全隐患。用户可以提交一段数据库查询代码，
     * 根据程序返回的结果，获得某些他想得知的数据，这就是所谓的SQL Injection，
     * 即SQL注入
     *         String sql = " insert into student " +
     *                 " values ( "
     *                 + student.getFlowid() +
     *                 "," + student.getType() +
     *                 "," + student.getIdCard() +
     *                 "," + student.getExamid() +
     *                 "," + "'" + student.getStudentName() + "'" +
     *                 "," + "'" + student.getLocation() + "'" +
     *                 "," + student.getGradle() + " )";
     *
     * 2)PreparedStatement:是Statement的子接口，可以传入带占位符的SQL语句，并且提供了补充占位符变量的方法
     *
     * 3)使用PreparedStatement
     * String sql = "insert into student  values(?,?,?,?,?,?,?)"
     * 1>创建PreparedStatement
     * PreparedStatement prepareStatement(String sql)
     *                             throws SQLException
     * 创建一个PreparedStatement对象，用于将参数化的SQL语句发送到数据库。
     * PreparedStatement preparedstatement = connection.preparedStatement(sql)
     *
     * 2>调动PreparedStatement的setxxx(int index,Object val)设置占位符的值
     * index值从1下标开始的
     *
     * 3>执行SQL语句:executeQuery()或者executeUpdate(),注意：执行时，不需要传入SQL语句
     * */
    public static void PreparedStatement(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBC_Utils.getConnection();
            String sql = "insert into customer (id,name,emails,birth)" +
                    "values (?,?,?,?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,7);
            preparedStatement.setString(2,"黄祖鸿");
            preparedStatement.setString(3,"2586154625@qq.com");
            preparedStatement.setDate(4, new Date(new java.util.Date().getTime()));

            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            JDBC_Utils.releaseResouse(null,preparedStatement,connection);
        }
    }
}
