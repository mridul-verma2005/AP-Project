package edu.univ.erp.domain;

public class Enrollment {
    private int enrollment_id;
    private String student_username;
    private String section_id;
    private String status;


    public Enrollment(String student_username, String section_id, String status) {
        this.enrollment_id  = 0 ;
        this.student_username = student_username;
        this.section_id = section_id;
        this.status = status;
    }

    public int getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(int enrollment_id) {
        this.enrollment_id = enrollment_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}