package edu.univ.erp.domain;

public class Student {
    private int user_id;
    private int roll_no;
    private String program;
    private int year;

    Student(int user_id , int roll_no , String program , int year){
        this.user_id = user_id;
        this.roll_no  =roll_no;
        this.program = program;
        this.year = year;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(int roll_no) {
        this.roll_no = roll_no;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String displayStudentInfo(){
        return "Student_id: " + this.user_id + "Student rollno: " +this.roll_no + " Student program: " + this.program + "Student year: \n" + this.year;
    }
}