package edu.univ.erp.service;

import edu.univ.erp.data.*;
import edu.univ.erp.domain.Enrollment;
import edu.univ.erp.domain.Grade;
import edu.univ.erp.domain.Student;

import java.sql.ResultSet;

public class Student_Service {

    public Object[][] browseCatalog(){
        Section_Access sectionAccess = new Section_Access();
        return sectionAccess.getCatalog();
    }

    public int registerCourses(String student_username ,String section_id){
         Section_Access sectionAccess = new Section_Access();
        if(sectionAccess.currentCapacity(section_id) == 0){
            return 2;
        }
        else{
            Enrollment_Access enrollmentAccess = new Enrollment_Access();
            if(enrollmentAccess.CheckCurrentEnrollment(student_username,section_id) == 2){
                int result = enrollmentAccess.reactivateEnrollment(student_username,section_id);
                int seat_change = sectionAccess.change_avalible_seats_reduceby_1(section_id);
                return result * seat_change;
            }
            else if( enrollmentAccess.CheckCurrentEnrollment(student_username,section_id) == 0){
                Enrollment e = new Enrollment(student_username,section_id,"Active");
                Grade g = new Grade(student_username,section_id);
                Grade_Access gradeAccess = new Grade_Access();
                gradeAccess.addGrade(g);
                int result = enrollmentAccess.addEnrollment(e);
                System.out.println("enrollment is done");
                int seat_change = sectionAccess.change_avalible_seats_reduceby_1(section_id);
                System.out.println("avalible seats reduced by 1");
                System.out.println(seat_change);
                return result * seat_change;

            }
//            else if( enrollmentAccess.CheckCurrentEnrollment(student_username,section_id) == 1){
//                return 0;
//            }
        }
        return  0;
    }
    public int dropCourse(String student_username , String section_id){
        Enrollment_Access enrollmentAccess = new Enrollment_Access();
        Grade_Access gradeAccess = new Grade_Access();
        gradeAccess.deleteGrade(student_username,section_id);
        int result = enrollmentAccess.deactivateEnrollment(student_username,section_id);
        Section_Access sectionAccess = new Section_Access();
        int seat_change = sectionAccess.change_avalible_seats_increase_by_1(section_id);
        return result * seat_change;

    }

    public ResultSet timetable(String student_username){
        Section_Access sectionAccess = new Section_Access();
        return sectionAccess.getTimetable(student_username);


    }
    public Object[][] get_grades(String student_username){
        Grade_Access gradeAccess = new Grade_Access();
        return gradeAccess.seeGradebyStudentUsername(student_username);
    }

    public int add_student(String username , String name , String program , int roll_no , int year){
        Student s = new Student(username,roll_no,name,program,year);
        Student_Access studentAccess = new Student_Access();
        int result =  studentAccess.addStudent(s);
        return result;

    }




}
