package edu.univ.erp.service;

import edu.univ.erp.auth.UserAuth;
import edu.univ.erp.auth.UserAuth_Access;
import edu.univ.erp.data.Course_Access;
import edu.univ.erp.data.Section_Access;
import edu.univ.erp.domain.Course;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Admin_Service {

    public Object[][] get_ALluser(){
        UserAuth_Access userAuthAccess = new UserAuth_Access();
        return userAuthAccess.getAllUser();
    }

    public Object[][] get_AllCourses(){
        Course_Access courseAccess = new Course_Access();
        System.out.println("courses delevered");
        return courseAccess.getAllCourse();
    }

    public Object[][] get_AllSections(){
        Section_Access sectionAccess = new Section_Access();
        return sectionAccess.getAllSections();

    }
    public int add_user(String username , String role , String status,String password){
        UserAuth u = new UserAuth(username,status,role,password, new Timestamp(System.currentTimeMillis()));
        UserAuth_Access userAuthAccess = new UserAuth_Access();
        return userAuthAccess.addUser(u);
    }

    public int update_user_withpassword( String role , String status,String password,String username){
        UserAuth_Access userAuthAccess = new UserAuth_Access();
        return userAuthAccess.updateuser_withpassword(role,status,password,username);
    }

    public int update_user_withoutpassword( String role , String status,String username){
        UserAuth_Access userAuthAccess = new UserAuth_Access();
        return userAuthAccess.updateuser_withoutpassword(role,status,username);
    }

    public int add_course(String course_code,String title, int credits , String dept){
        Course c = new Course(course_code,title,credits,dept);
        Course_Access courseAccess = new Course_Access();
        return courseAccess.addCourse(c);
    }

    public int update_course(String course_code,String title, int credits , String dept){
        Course_Access courseAccess = new Course_Access();
        return courseAccess.UpdateCourse(course_code,title,credits,dept);
    }

    public int delete_course(String course_code){
        Course_Access courseAccess = new Course_Access();
        return courseAccess.deleteCourse(course_code);
    }

//    public int remove_user(String username){
//        UserAuth_Access userAuthAccess = new UserAuth_Access();
//        userAuthAccess.
//    }

}
