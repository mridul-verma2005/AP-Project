package edu.univ.erp.domain;

public class Grade {
    private String student_username;
    private String section_id;

    public Grade(String student_username, String section_id) {
        this.student_username = student_username;
        this.section_id = section_id;
    }

    public String getStudent_username() {
        return student_username;
    }

    public void setStudent_username(String student_username) {
        this.student_username = student_username;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }
}
