package edu.univ.erp.data;

import edu.univ.erp.domain.Course;
import edu.univ.erp.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Course_Access {
    public void addCourse(Course c){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "insert into courses(title , credits) values (? ,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,c.getTitle());
            preparedStatement.setInt(2,c.getCredits());
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Course Added Successfully");
            }
            else{
                System.err.println("Error in Adding Course");
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public void deleteCourse(Course c){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "delete from courses where course_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,c.getCourse_id());
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Course Deleted Successfully");
            }
            else{
                System.err.println("Error in Deleting Course");
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }


    public void UpdateCourseCredits(int credits,int course_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "update courses set credits = ? where course_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,credits);
            preparedStatement.setInt(2,course_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Course Credits Updated Successfully");
            }
            else{
                System.err.println("Error in Updating Course Credits");
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public void UpdateCourseTitle(String title,int course_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "update courses set title = ? where course_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,title);
            preparedStatement.setInt(2,course_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Course Title Updated Successfully");
            }
            else{
                System.err.println("Error in Updating Course Title");
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }


}
