package com.clx.test.dbcp;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class testDBCP {
    public static void main(String[] args) {

    }

    /**
     * 使用DBCP数据库连接池
     * 1.加入jar包
     * 2.创建数据库连接池
     * */
    public static void testDBCP(){
        DataSource dataSource = null;

        //1.创建DBPC数据源实例
        dataSource = new BasicDataSource();

        //2.为数据源实例指定必须的属性

    }
}
