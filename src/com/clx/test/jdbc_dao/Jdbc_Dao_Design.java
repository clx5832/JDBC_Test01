package com.clx.test.jdbc_dao;

import com.clx.test.reflect.reflect_utils.ReflectionUtils;
import com.clx.test.utils.JDBC_Utils;
import org.apache.commons.beanutils.BeanUtils;


import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jdbc_Dao_Design {
    public static void main(String[] args) {

    }

    /**
     * DAO :Data Access Object // 数据访问对象
     * <p>
     * what:访问数据信息的类，包含了对数据的CRUD（create,read,update,delete）
     * 而不包含任何业务相关的信息
     * <p>
     * why:实现功能的模块化,更有利于代码的维护和升级
     * DAO可以被子类继承或直接使用
     * <p>
     * how:使用JDBC编写DAO可能包含的方法
     * Insert ,update,delete操作可以包含在其中
     * void update(String sql,Object...args);
     * <p>
     * 查询一条记录，返回对应的对象
     * <T> T get(Class<T> clazz ,String sql,Object...args);
     * <p>
     * 查询多条记录，返回对应的对象的集合
     * <T>List<T> getForList(Class<T> clazz ,String sql ,Object...args)
     * <p>
     * 返回某条记录的某一个字段，或一个统计的值(一共有多少条记录等)
     * <E> E getForValue (String sql ,Object...args)
     */

    //Insert ,update,delete操作可以包含在其中
    public static void update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JDBC_Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(1 + i, args[i]);
            }
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(null, preparedStatement, connection);
        }
    }

    //查询一条记录，返回对应的对象
    public static <T> T get(Class<T> clazz, String sql, Object... args) {
        T entity = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //1.获取Connection
            connection = JDBC_Utils.getConnection();

            //2.获取获取PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            //4.进行查询，得到ResultSet
            resultSet = preparedStatement.executeQuery();

            //5.若ResultSet中有记录，准备Map<String,Object>：键：存放的列的名，值：存放的列的值
            if (resultSet.next()) {
                Map<String, Object> values = new HashMap<String, Object>();
                //6.得到ResultSetMeteData对象
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

                //7.处理ResultSet,把指针向下移动一个单位


                //8.由ResultSetMeteData 对象得到结果集中有多少列
                int columnCount = resultSetMetaData.getColumnCount();

                //9.由ResultSetMeteData 得到每一列的别名，由ResultSet得到具体每一列的值
                for (int i = 0; i < columnCount; i++) {
                    String columnLable = resultSetMetaData.getColumnLabel(i + 1);
                    Object columnValue = resultSet.getObject(i + 1);

                    //10.填充Map对象
                    values.put(columnLable, columnValue);
                }
                //11.用反射创建Class对应的对象
//                Object object = clazz.newInstance();
                entity = clazz.newInstance();

                //12.遍历Map对象，用反射填充对象的属性值：
                for (Map.Entry<String, Object> entry : values.entrySet()) {

                    //属性名为Map中的Key，属性值为Map中的Value
                    String filedName = entry.getKey();
                    Object filedValue = entry.getValue();

                    ReflectionUtils.setFieldValue(entity, filedName, filedValue);
//                    BeanUtils.setProperty(entity,filedName,filedValue);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(resultSet, preparedStatement, connection);
        }
        return entity;
    }

    // 查询多条记录，返回对应的对象的集合
    public static <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {

        List<T> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            //1.得到結果集
            connection = JDBC_Utils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            //填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(1 + i, args[i]);
            }

            resultSet = preparedStatement.executeQuery();
            //2.处理结果集,得到Map的List，其中一个Map对象就是一条记录
            //就是一条记录，Map的Key为ResultSet的别名，Map的Value为列的值
            List<Map<String, Object>> values = handleResultSetToMapList(resultSet);

            //3.把Map的List转为clazz的对应的List
            //其中Map的Key即为clazz对应的对象propertyName
            //其中Map的Value即为clazz对应的propertyValue
            list = transfterMapListToBeanList(clazz, values);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(resultSet, preparedStatement, connection);
        }


        return list;
    }

    private static <T> List<T> transfterMapListToBeanList(Class<T> clazz, List<Map<String, Object>> values) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        //12.判断List中是否为空集合，若不为空，则遍历List得到一个个的Map对象
        //在把一个Map对象转为一个Class参数对应的Object对象
        List<T> result = new ArrayList<>();

        T bean = null;

        if (values.size() > 0) {
            for (Map<String, Object> map1 : values) {

                bean = clazz.newInstance();

                for (Map.Entry<String, Object> entry : map1.entrySet()) {

                    String propertyName = entry.getKey();
                    Object propertyValue = entry.getValue();
                    BeanUtils.setProperty(bean, propertyName, propertyValue);
                }

                //13.把Object对象放入到list中
                result.add(bean);
            }
        }
        return result;
    }

    /**
     * 处理结果集得到Map的list，其中一个Map对象对应一条记录
     */
    private static List<Map<String, Object>> handleResultSetToMapList(ResultSet resultSet) throws SQLException {
        //5.准备一个List<Map<String ,Object>>;
        //键：存放列的别名，值：存放列的值，其中一个Map对象对应着一条记录
        List<Map<String, Object>> values = new ArrayList<>();

        List<String> columnLables = getColumnLabels(resultSet);
        Map<String, Object> map = null;

        //7.处理ResultSet,使用While循环
        while (resultSet.next()) {
            map = new HashMap<>();

            for (String columnLable : columnLables) {

                Object columnValues = resultSet.getObject(columnLable);

                map.put(columnLable, columnValues);
            }
            //11.把一条记录的一个Map对象的Map对象放入5准备的List中
            values.add(map);
        }
        return values;
    }

    /**
     * 获取结果集的ColumnLable对应的list
     */
    private static List<String> getColumnLabels(ResultSet resultSet) throws SQLException {
        List<String> labels = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
            labels.add(resultSetMetaData.getColumnLabel(i + 1));
        }

        return labels;
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

            //2.获取获取PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            //4.进行查询，得到ResultSet
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
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
