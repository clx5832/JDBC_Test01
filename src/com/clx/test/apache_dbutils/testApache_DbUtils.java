package com.clx.test.apache_dbutils;

import com.clx.test.utils.JDBC_Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class testApache_DbUtils {
    public static void main(String[] args) {
//        testDBTils_QueryRuner();
//        testQuery();
//        testBeanHandler();
        testMapHandler();
    }

    /**
     * public class QueryRunner
     * extends AbstractQueryRunner
     * Executes SQL queries with pluggable strategies for handling ResultSets. This class is thread safe.
     * <p>
     * 测试QueryRuner类的update方法
     */
    public static void testDBTils_QueryRuner() {
        //1.创建QueryRuner类实现类
        QueryRunner queryRunner = new QueryRunner();

        //2.使用其update方法
        String sql = "";
//        sql = "delete from customer where id in (? , ?)";
//        sql = "update customer set name = ?,emails = ?,birth = ? where id = ?";
        sql = "insert  into  customer( id  , name , emails , birth ) values (? , ? , ?,?)";
        Connection connection = null;

        try {
            connection = JDBC_Utils.getConnection2();
            queryRunner.update(connection, sql, 2, "傻逼2", "694864544@dffa", "2000-11-4");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(null, null, connection);
        }
    }


    /**
     * 进行查询的语句
     * <p>
     * QueryRunner 的 query 方法的返回值取决于其 ResultSetHandler 参数的 handler返回值
     */
    public static void testQuery() {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = null;

        try {
            connection = JDBC_Utils.getConnection2();
            String sql = "select id ,name ,emails ,birth " + " from customer";
            Object object = queryRunner.query(connection, sql, new MyResultSetHandler());
            System.out.println(object);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(null, null, connection);
        }
    }

    static class MyResultSetHandler implements ResultSetHandler {

        @Override
        public Object handle(ResultSet resultSet) throws SQLException {
//            System.out.println("撤离现场录像陈亮相");
//            return "撤离现场录像陈亮相";

            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String emails = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                Customer customer = new Customer(id, name, emails, birth, null);
                customers.add(customer);
            }
            return customers;
        }
    }

    /***
     * BeanHandler：把结果集的第一条记录转为创建BeanHandler 对象时传入的Class参数对应的对象
     *
     * BeanListHandler:把结果集转为一个List,该List不为null,但可能为空集合(size()方法返回0)
     * */

    public static void testBeanHandler() {
        Connection connection = null;
        QueryRunner queryRunner = new QueryRunner();

        try {
            connection = JDBC_Utils.getConnection2();
//            String sql = "select id , name , emails , birth "+ " from customer where id = ? ";

            String sql = "select id , name , emails , birth " + " from customer  ";

//            Customer customer = (Customer) queryRunner.query(connection,sql,new BeanHandler(Customer.class),9);

            List<Customer> customer = (List<Customer>) queryRunner.query(connection, sql, new BeanListHandler(Customer.class));

            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(null, null, connection);
        }
    }

    /***
     * MapHandler：返回SQL对应的第一条记录对应的Map对象
     * 键： SQL 查询的列名（不是列的别名）
     * */
    public static void testMapHandler() {
        Connection connection = null;
        QueryRunner queryRunner = new QueryRunner();

        try {
            connection = JDBC_Utils.getConnection2();


            String sql = "select id , name , emails , birth " + " from customer  ";


            Map<String, Object> customer = (Map<String, Object>) queryRunner.query(connection, sql,
                    new MapHandler());

            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBC_Utils.releaseResouse(null, null, connection);
        }
    }
}
