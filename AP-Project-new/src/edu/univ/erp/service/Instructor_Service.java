package edu.univ.erp.service;

import edu.univ.erp.data.*;
import edu.univ.erp.domain.Instructor;
import edu.univ.erp.domain.Student;



public class Instructor_Service {
    public Object[] get_details(String username){
        Instructor_Access instructorAccess = new Instructor_Access();
        return instructorAccess.getInstructorbyUsername(username);
    }

    public Object[][] get_AllStudentsOfSection(String section_id){
        Grade_Access gradeAccess = new Grade_Access();
        return gradeAccess.getAllStudentGrades(section_id);
    }

    public int add_instrutor(String username , String name , String dept){
     Instructor i = new Instructor(username,name,dept);
     Instructor_Access instructorAccess = new Instructor_Access();
     int result = instructorAccess.AddInstructor(i);
     return result;

    }
}
