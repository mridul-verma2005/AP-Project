package edu.univ.erp.domain;

public class Section {
    private String section_id;
    private String course_code;
    private String instructor_username;
    private String daytime;
    private String room;
    private int capacity;
    private int current_avalible_seats;
    private String semester;
    private int year;

    public Section(String section_id, String course_code, String instructor_username, String daytime, String room, int capacity, int current_avalible_seats, String semester, int year) {
        this.section_id = section_id;
        this.course_code = course_code;
        this.instructor_username = instructor_username;
        this.daytime = daytime;
        this.room = room;
        this.capacity = capacity;
        this.current_avalible_seats = current_avalible_seats;
        this.semester = semester;
        this.year = year;


    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getInstructor_username() {
        return instructor_username;
    }

    public void setInstructor_username(String instructor_username) {
        this.instructor_username = instructor_username;
    }

    public String getDaytime() {
        return daytime;
    }

    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrent_avalible_seats() {
        return current_avalible_seats;
    }

    public void setCurrent_avalible_seats(int current_avalible_seats) {
        this.current_avalible_seats = current_avalible_seats;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}