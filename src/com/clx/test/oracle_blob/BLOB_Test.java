package com.clx.test.oracle_blob;

import com.clx.test.utils.JDBC_Utils;

import java.io.*;
import java.sql.*;

public class BLOB_Test {
    public static void main(String[] args) {
        read_Blob();
//        testInsertBlob();
    }

    /**
     * 插入BLOB 类型的数据必须使用PreparedStatement：因为BLOB类型的数据时无法使用字符串进行拼写的。
     * <p>
     * 调用setBlob(int index ,InputStream inputstream)
     */
    public static void testInsertBlob() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBC_Utils.getConnection();
            String sql = " insert into customer( name , emails , birth , picture)" +
                    " values ( ? , ? , ? , ? )";
            preparedStatement = connection.prepareStatement(sql);
            //返回主键值
            preparedStatement.setObject(1, "我是图片");
            preparedStatement.setObject(2, "123456789@qq.com");
            preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));

            InputStream inputStream = new FileInputStream("C:\\Users\\CLX\\Desktop\\Androdi_Studio快捷键大全.png");
            preparedStatement.setBlob(4, inputStream);
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



/**
 * 读取blob数据：
 * 1.使用getBlob 方法读取到Blob对象
 * 2.调用Blob的 getBinaryStream()方法得到输入流。在使用IO操作即可
 * */
    public static void read_Blob() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JDBC_Utils.getConnection();
            String sql = " select id , name , emails , birth , picture " +
                    " from customer where id = 15 ";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String emails = resultSet.getString(3);

                System.out.println(id+":"+name+"+"+emails);

                Blob picture = resultSet.getBlob(5);

                //输出流读取图片数据
                InputStream inputStream = picture.getBinaryStream();
                OutputStream outputStream = new FileOutputStream("C:\\Users\\CLX\\Desktop\\clx.jpg");

                byte[] buffer = new byte[1024];
                //	read(byte[] b)
                //从输入流读取一些字节数，并将它们存储到缓冲区 b
                int length = 0 ;
                while ((length = inputStream.read(buffer))!= - 1){
                    outputStream.write(buffer,0,length);
                }
                outputStream.close();
                inputStream.close();
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
