package edu.univ.erp.domain;

public class Instructor {
    private int user_id;
    private String instructor_name;
    private String department;

    Instructor(int user_id ,String instructor_name, String department){
        this.user_id = user_id;
        this.department = department;
        this.instructor_name = instructor_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
