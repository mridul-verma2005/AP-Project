package edu.univ.erp.domain;

public class Course {
    private int course_id;
    private String title;
    private int credits;

    public Course(int course_id, String title, int credits) {
        this.course_id = course_id;
        this.title = title;
        this.credits = credits;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
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
}
