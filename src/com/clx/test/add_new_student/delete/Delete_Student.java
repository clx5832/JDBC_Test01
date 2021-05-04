package com.clx.test.add_new_student.delete;

import com.clx.test.add_new_student.Student;
import com.clx.test.utils.JDBC_Utils;

import java.util.Scanner;

public class Delete_Student {
    public static void main(String[] args) {
        delete_Info();
    }

    public static void add_Delete_Info(Student student){
        String sql = "delete from student where FlowId = "+
                student.getFlowid();
        JDBC_Utils.update(sql);
    }

    public static void delete_Info(){
        Student student = getStudentFromConsole();
        add_Delete_Info(student);
    }
    //从控制台输入信息
    private static Student getStudentFromConsole() {
       Student student = new Student();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入FlowId:");
        student.setFlowid(scanner.nextInt());
        return student;
    }

}
