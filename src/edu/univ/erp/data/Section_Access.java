package edu.univ.erp.data;

import edu.univ.erp.domain.Section;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Section_Access {
    public void addSection(Section s){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("insert into sections (course_code , instructor_id  day_time , room, capacity , semester , year) values (?,?,?,?,?,?,?)");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,s.getCourse_id());
            preparedStatement.setInt(2,s.getInstructor_id());
            preparedStatement.setString(3,s.getDaytime());
            preparedStatement.setString(4,s.getRoom());
            preparedStatement.setInt(5,s.getCapacity());
            preparedStatement.setString(6,s.getSemester());
            preparedStatement.setInt(7,s.getYear());
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Sections Added Successfully");
            }
            else{
                System.out.println("Error in Adding Section");
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateSectionCoursecode(int course_code , int section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set course_code = ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,course_code);
            preparedStatement.setInt(2,section_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Course Code Updated Successfully");
            }
            else{
                System.out.println("Error in updating course_code");
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateSectionInstructor(String instructor_id , int section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set instructor_id = ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,instructor_id);
            preparedStatement.setInt(2,section_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Instructor id Updated Successfully");
            }
            else{
                System.out.println("Error in updating instructor");
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void updateSectionDaytime(String daytime , int section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set daytime = ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,daytime);
            preparedStatement.setInt(2,section_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("day, time Updated Successfully");
            }
            else{
                System.out.println("Error in updating day , time ");
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void updateSectionRoom(int room , int section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set room = ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,room);
            preparedStatement.setInt(2,section_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("room Updated Successfully");
            }
            else{
                System.out.println("Error in updating room");
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void updateSectionSemester(String semester , int section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set semester = ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,semester);
            preparedStatement.setInt(2,section_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("semester Updated Successfully");
            }
            else{
                System.out.println("Error in updating semester");
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void updateSectionYear(int year , int section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set year = ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,year);
            preparedStatement.setInt(2,section_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("year Updated Successfully");
            }
            else{
                System.out.println("Error in updating year");
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void updateSectionCapacity(int capacity , int section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set capacity = ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,capacity);
            preparedStatement.setInt(2,section_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("capacity Updated Successfully");
            }
            else{
                System.out.println("Error in updating capacity");
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteSection(int section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("delete from sections where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,section_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("current section deleted Successfully");
            }
            else{
                System.out.println("Error in deleting current section");
            }


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public ResultSet getAllSection(){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select * from sections");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResultSet getCatalog(){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select course_code, title, credits, capacity, instructor_name from sections join instructors on sections.instructor_id = instructors.user_id join courses on sections.course_code = courses.course_id");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    public int currentCapacity(int section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select current_avalible_seats from sections where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            int current_avalible_seats = resultSet.getInt("current_avalible_seats");
            return current_avalible_seats;

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public void change_avalible_seats(int section_id, int n){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update set current_avalible_seats = current_avalible_seats + ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,n);
            preparedStatement.setInt(2,section_id);
            int result = preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ResultSet getTimetable(int student_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select title, instructor_name from sections join instructors on sections.instructor_id = instructors.user_id join courses on sections.course_code = courses.course_id where student_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,student_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

    }
}
