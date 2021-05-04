package com.clx.test.jdbc_dao;

import com.clx.test.add_new_student.Student;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class BeanUtils_Mannage_JavaBean {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        testSetPreperty();
        testGetPreperty();
    }
    /**java类的属性：
     * 1）在JAVAEE中，java类中属性通过getter,setter,来定义：get(或set)方法，去除，get(或set)后
     * 后字母小写即为java类的属性
     *
     * 2)而以前叫的属性，即成员变量，称之为字段
     *
     * 3）一般情况下，字段名和属性名都一致
     *
     * 4)操作java类属性有一个工具包：beanutils
     * 》1搭建环境：需要同时加入：commons-logging-1.2.jar和 commons-beanutils-1.9.4.jar
     * 1>setPrperty
     *  BeanUtils.setProperty(object,"idCard","1235645465");
     *
     * 2>getPreperty()
     * Object values = BeanUtils.getProperty(object,"idCard");
     * */

    //赋值操作
    public static void testSetPreperty() throws InvocationTargetException, IllegalAccessException {
        Object object = new Student();


        BeanUtils.setProperty(object,"idCard","1235645465");
        System.out.println(object);
    }

    //取值操作,idCard要和Student中的idCard一致
    public static void testGetPreperty() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object object = new Student();


        BeanUtils.setProperty(object,"idCard","1235645465");
        System.out.println(object);

        Object values = BeanUtils.getProperty(object,"idCard");
        System.out.println(values);
    }


}
