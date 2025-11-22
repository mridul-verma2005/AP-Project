package edu.univ.erp.domain;

public class Section {
    private int section_id;
    private int course_id;
    private int instructor_id;
    private String daytime;
    private String room;
    private int capacity;
    private int current_avalible_seats;
    private String semester;
    private int year;



    Section(int section_id , int course_id , int instructor_id, String daytime , String room, int capacity ,int current_avalible_seats, String semester, int year){
        this.section_id = section_id;
        this.course_id = course_id;
        this.instructor_id  = instructor_id;
        this.daytime = daytime;
        this.room = room;
        this.capacity = capacity;
        this.current_avalible_seats = current_avalible_seats;
        this.semester = semester;
        this.year = year;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
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
