package com.clx.test.add_new_student;

import com.clx.test.utils.JDBC_Utils;

import java.util.Scanner;

public class AddNewStudent {
    public static void main(String[] args) {
        testAddNewStudent();
    }

    /**
     * 1.向数据表中插入一条Student记录
     * <p>
     * 1)新建一个Student对映examStudent数据表
     * <p>
     * class Student{
     * 流水号
     * int flowid
     * 考试类型
     * int type
     * 身份证号
     * String idCard
     * 准考证号
     * String examid
     * 学生名字
     * String studentName
     * 学生地址
     * String location
     * 学生成绩
     * int gradle
     * }
     * <p>
     * 2)z在测试方法testAddStudent()中
     * 1.获取从控制台输入的Student对象
     * Student student =getStudentFromConsole();
     * <p>
     * 2.调用addNewStudent（Student student）方法执行插入操作
     * <p>
     * 3）
     * 新建一个方法：void addNewStudent(Student student)把参数student对象插入到数据库中
     * 3）新建一个方法:void addStudent(Student student)把参数Student插入到数据库中
     * .addStudent(Student student){
     * //1准备一条SQL语句:
     * String sql = "insert into examstudent values(1,4,'','','','',99)"
     * <p>
     * String sql = "insert into examstudent values(student.getFlowId()+
     * ","+student.getType()+
     * ","+student.getIdcard()+
     * ","+student.getExamCaid()+
     * ","+student.getStudentName()+
     * ","+student.getLocation()+
     * ","+student.getGradle())"
     * <p>
     * //2.调用JDBCUtils类的update(Stirng sql)方法，执行插入
     * }
     */
    public static void AddNewStudent(Student student) {

//        //1.准备一条SQL语句
//        String sql = " insert into student " +
//                " values ( "
//                + student.getFlowid() +
//                "," + student.getType() +
//                "," + student.getIdCard() +
//                "," + student.getExamid() +
//                "," + "'" + student.getStudentName() + "'" +
//                "," + "'" + student.getLocation() + "'" +
//                "," + student.getGradle() + " )";
        String sql = " insert into student(FlowId, Type , IdCard , ExamCard , StudentName , Location ,gradle ) " +
                " values ( ? , ? , ? , ? , ? , ? , ? )";

        System.out.println(sql);

        //2.调用JDBCUtils类的update(sql)方法执行插入操作
        JDBC_Utils.update(sql, student.getFlowid(), student.getType(), student.getIdCard(),
                student.getExamId(), student.getStudentName(), student.getLocation(),
                student.getGradle());

    }

    //从控制台输入数据的方法
    public static void testAddNewStudent() {
        Student student = getStudentFromConsole();
        AddNewStudent(student);
    }

    //从控制台输入数据
    private static Student getStudentFromConsole() {
        Scanner scanner = new Scanner(System.in);

        Student student = new Student();

        System.out.print("Flowid:");
        student.setFlowid(scanner.nextInt());

        System.out.print("Type:");
        student.setType(scanner.nextInt());

        System.out.print("IDCard:");
        student.setIdCard(scanner.next());

        System.out.print("ExamCard:");
        student.setExamId(scanner.next());

        System.out.print("StudentName:");
        student.setStudentName(scanner.next());

        System.out.print("Location:");
        student.setLocation(scanner.next());

        System.out.print("Gradle:");
        student.setGradle(scanner.nextInt());

        return student;
    }
}
