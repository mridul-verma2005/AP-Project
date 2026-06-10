package edu.univ.erp.domain;

public class Course {
    private String course_code;
    private String title;
    private int credits;
    private String dept;

    public Course(String course_code, String title, int credits,String dept) {
        this.course_code = course_code;
        this.title = title;
        this.credits = credits;
        this.dept = dept;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String  course_id) {
        this.course_code = course_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
