package com.clx.test.add_new_student;

public class Student {
    //流水号
    private int flowid;
    // 考试类型
    private int type;
    // 身份证号
    private String idCard;
    // 准考证号
    private String ExamId;
    //学生名字
    private String studentName;
    // 学生地址
    private String location;
    // 学生成绩
    private int gradle;

    public Student(int flowid, int type, String idCard, String ExamId, String studentName, String location, int gradle) {
        this.flowid = flowid;
        this.type = type;
        this.idCard = idCard;
        this.ExamId = ExamId;
        this.studentName = studentName;
        this.location = location;
        this.gradle = gradle;
    }

    public Student() {

    }

    public int getFlowid() {
        return flowid;
    }

    public void setFlowid(int flowid) {
        this.flowid = flowid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getExamId() {
        return ExamId;
    }

    public void setExamId(String examId) {
        this.ExamId = examId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGradle() {
        return gradle;
    }

    public void setGradle(int gradle) {
        this.gradle = gradle;
    }

    @Override
    public String toString() {
        return "Student{" +
                "flowid=" + flowid +
                ", type=" + type +
                ", idCard='" + idCard + '\'' +
                ", examid='" + ExamId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", location='" + location + '\'' +
                ", gradle=" + gradle +
                '}';
    }
}
