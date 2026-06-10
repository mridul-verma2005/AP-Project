package edu.univ.erp.data;

import edu.univ.erp.domain.Course;
import edu.univ.erp.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Course_Access {
    public int addCourse(Course c){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "insert into courses(course_code, title , credits, department) values (? ,?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,c.getCourse_code());
            preparedStatement.setString(2,c.getTitle());
            preparedStatement.setInt(3,c.getCredits());
            preparedStatement.setString(4,c.getDept());
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Course Added Successfully");
            }
            else{
                System.err.println("Error in Adding Course");
            }
            return result;

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return  0;
        }

    }

    public int deleteCourse(String course_code){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "delete from courses where course_code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,course_code);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Course Deleted Successfully");
            }
            else{
                System.err.println("Error in Deleting Course");
            }
            return result;

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public int UpdateCourse(String course_code,String title, int credits , String dept){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "update courses set title = ?, department = ? ,credits = ? where course_code = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,title);
            preparedStatement.setString(2,dept);
            preparedStatement.setInt(3,credits);
            preparedStatement.setString(4,course_code);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Course Title Updated Successfully");
            }
            else{
                System.err.println("Error in Updating Course Title");
            }
            return result;

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public Object[][] getAllCourse(){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "select title , course_code, credits , department from courses";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet= preparedStatement.executeQuery();
            ArrayList<Object[]> list = new ArrayList<>();
            while (resultSet.next()) {
                String course_code = resultSet.getString("course_code");
                String title = resultSet.getString("title");
                int credits = resultSet.getInt("credits");
                String department = resultSet.getString("department");
                Object[] row  = {course_code,title,credits,department,"Edit","Delete"};
                list.add(row);
            }
            Object[][] data = new Object[list.size()][];
            for (int i = 0; i < list.size(); i++) {
                data[i] = list.get(i);
            }
            return data;

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

    }


}
