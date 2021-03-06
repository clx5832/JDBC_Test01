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
     * ????????????????????????
     */
    public static <T> T get(Class<T> tClass, String sql, Object... args) {
        T entity = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            //1.??????ResultSet?????????
            connection = JDBC_Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();

            //2.??????ResultSetMetaData??????
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            //3.????????????Map<String ,Object>???????????????SQL?????????????????????
            //???????????????
            Map<String, Object> values = new HashMap<String, Object>();

            //4.????????????????????????3????????????Map??????
            if (resultSet.next()) {
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    String columnLable = resultSetMetaData.getColumnLabel(i + 1);
                    Object columnValues = resultSet.getObject(columnLable);

                    values.put(columnLable, columnValues);
                }
            }

            //5.???Map????????????,??????????????????Class???????????????
            if (values.size() > 0) {

                entity = tClass.newInstance();


                //6.??????Map??????,???????????????Class??????????????????????????????
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
     * 1)what:?????????ResultSet???????????????????????????????????????????????????????????????????????????????????????
     * <p>
     * 2???how:
     * 1>??????ResultSetMeta ??????????????? ResultSet ??? getMetaData()??????
     * 2>ResultSetMetaData????????????????????????
     * ???int getColumnCount():SQL????????????????????????
     * ???String getColumnLabel(int column):???????????????????????????????????????????????????1??????
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


            //1.??????ResultSetMetaData??????
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            while (resultSet.next()) {
                //2.????????????????????????
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    String columnLabel = resultSetMetaData.getColumnLabel(i + 1);//?????????????????????????????????????????????????????????

                    Object columnValue = resultSet.getObject(columnLabel);

                    values.put(columnLabel, columnValue);


                }

            }
            System.out.println(values);

            //????????????????????????
            Class clas = Student.class;
            Object object = clas.newInstance();
            //??????Map
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
