package com.clx.test.reflect;

import com.clx.test.add_new_student.Student1;
import com.clx.test.add_new_student.Student;
import com.clx.test.reflect.reflect_utils.ReflectionUtils;
import com.clx.test.utils.JDBC_Utils;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Reflect_JDBC {
    public static void main(String[] args) {
        test();
    }

    public static Student getStudent(String sql, Object... args) {
        Student student = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = JDBC_Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                student = new Student();
                student.setFlowid(resultSet.getInt(1));
                student.setType(resultSet.getInt(2));
                student.setIdCard(resultSet.getString(3));
                student.setExamId(resultSet.getString(4));
                student.setLocation(resultSet.getString(5));
                student.setStudentName(resultSet.getString(6));
                student.setGradle(resultSet.getInt(7));
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

        return student;
    }

    public static void test() {
        String sql = " select flowid , type , idCard , ExamId , studentName , location , gradle " +
                " from student where flowid = ? ";
        Student1 student = get(Student1.class, sql, 1);
        System.out.println(student);
    }


    /**
     * 利用反射进行处理
     */
    public static <T> T get(Class<T> tClass, String sql, Object... args) {
        T entity = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            //1.得到ResultSet结果集
            connection = JDBC_Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();

            //2.得到ResultSetMetaData对象
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            //3.创建一个Map<String ,Object>对象，键：SQL查询的列的别名
            //值：列的值
            Map<String, Object> values = new HashMap<String, Object>();

            //4.处理结果集，填充3的对应的Map对象
            if (resultSet.next()) {
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    String columnLable = resultSetMetaData.getColumnLabel(i + 1);
                    Object columnValues = resultSet.getObject(columnLable);

                    values.put(columnLable, columnValues);
                }
            }

            //5.若Map不为空集,利用反射创建Class对应的对象
            if (values.size() > 0) {

                entity = tClass.newInstance();


                //6.遍历Map对象,利用反射为Class对象的对应的属性赋值
                for (Map.Entry<String, Object> entity1 : values.entrySet()) {

                    String filedName = entity1.getKey();
                    Object filedValue = entity1.getValue();
                    ReflectionUtils.setFieldValue(entity, filedName, filedValue);
                }

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


        return entity;
    }

    /**
     * ResultSetMetaData
     * 1)what:是描述ResultSet的元数据对象，即从中可以获取到结果集中有多少列，列名是什么
     * <p>
     * 2）how:
     * 1>得到ResultSetMeta 对象：调用 ResultSet 的 getMetaData()方法
     * 2>ResultSetMetaData有哪些好用的方法
     * 》int getColumnCount():SQL语句中包含哪些列
     * 》String getColumnLabel(int column):获取指定的列的别名，其中索引从下标1开始
     */

    public static void testResultSetMetaData() {

        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            String sql = "select flowid , type , idcard , examcard , studentname , location , gradle " +
                    "from student where flowid = ? ";

            connection = JDBC_Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, 1);
            resultSet = preparedStatement.executeQuery();
            Map<String, Object> values = new HashMap<String, Object>();


            //1.得到ResultSetMetaData对象
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                //2.打印每一列的列名
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    String columnLabel = resultSetMetaData.getColumnLabel(i + 1);//获取指定列的建议标题用于打印输出和显示

                    Object columnValue = resultSet.getObject(columnLabel);

                    values.put(columnLabel, columnValue);


                }

            }
            System.out.println(values);

            //利用反射创建对象
            Class clas = Student.class;
            Object object = clas.newInstance();
            //遍历Map
            for (Map.Entry<String, Object> entity : values.entrySet()) {
                String filedName = entity.getKey();
                Object filedValue = entity.getValue();

                System.out.println(filedName + ":" + filedValue);
                ReflectionUtils.setFieldValue(object, filedName, filedValue);
            }
            System.out.println(object);

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
