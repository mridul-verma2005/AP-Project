package edu.univ.erp.service;

import edu.univ.erp.data.Course_Access;
import edu.univ.erp.data.Enrollment_Access;
import edu.univ.erp.data.Section_Access;
import edu.univ.erp.data.Student_Access;
import edu.univ.erp.domain.Enrollment;
import edu.univ.erp.domain.Section;
import edu.univ.erp.domain.Student;

import java.sql.ResultSet;

public class Student_Service {
    Section_Access sectionAccess = new Section_Access();
    public ResultSet browseCatalog(){
        return sectionAccess.getCatalog();
    }

    public int registerCourses(int student_id ,int section_id ){
         Section_Access sectionAccess = new Section_Access();
        if(sectionAccess.currentCapacity(section_id) == 0){
            return 0;
        }
        else{
            int default_value = 0;
            Enrollment e = new Enrollment(default_value,student_id,section_id,"Active");
            Enrollment_Access enrollmentAccess = new Enrollment_Access();
            int result = enrollmentAccess.addEnrollment(e);
            sectionAccess.change_avalible_seats(section_id,-1);
            return result;
        }

    }
    public int dropCourse(int student_id , int section_id){
        Enrollment_Access enrollmentAccess = new Enrollment_Access();
        int result = enrollmentAccess.deactivateEnrollment(student_id,section_id);
        Section_Access sectionAccess = new Section_Access();
        sectionAccess.change_avalible_seats(section_id ,1);
        return result;

    }

//    public ResultSet timetable(int student_id){
//
//    }
//



}
