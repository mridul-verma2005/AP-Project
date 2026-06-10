package edu.univ.erp.domain;

public class Instructor {
    private String username;
    private String instructor_name;
    private String department;

    public Instructor(String username, String instructor_name, String department){
        this.department = department;
        this.username = username;
        this.instructor_name = instructor_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
