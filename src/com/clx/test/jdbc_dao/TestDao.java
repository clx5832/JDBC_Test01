package com.clx.test.jdbc_dao;

import com.clx.test.add_new_student.Student;
import com.clx.test.add_new_student.Student1;

import java.util.Date;
import java.util.List;

public class TestDao {

    public static void main(String[] args) {

//        update();
//        Query_Info();

        Query_GetList();

//        Select_One_loading();
    }

    private static void Select_One_loading() {
        String sql = "select idCard from student where flowid = ? ";
        String result = Jdbc_Dao_Design.getForValue(sql,1);
        System.out.println(result);

        sql = "select * from student ";
        int gradle = Jdbc_Dao_Design.getForValue(sql);
        System.out.println(gradle);
    }

    private static void Query_GetList() {
        String sql = " select flowid , type , idCard , ExamId , studentName , location , gradle " +
                " from student ";
        List<Student1> students = Jdbc_Dao_Design.getForList(Student1.class, sql);
        System.out.println(students + "\n");
    }

    private static void Query_Info() {
        String sql = " select flowid , type , idCard , ExamId , studentName , location , gradle " +
                " from student where flowid = ? ";
        Student student = Jdbc_Dao_Design.get(Student.class, sql, 1);
        System.out.println(student);
    }

    private static void update() {
        String sql = "insert into customer( id , name , emails , birth ) " +
                "values(?,?,?,?)";

        Jdbc_Dao_Design.update(sql, "10", "朱逸民", "131425689@qq.com", new Date(new Date().getTime()));
    }
}
