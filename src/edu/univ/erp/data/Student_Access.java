package edu.univ.erp.data;

import edu.univ.erp.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student_Access {
    public void addStudent(Student s){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "insert into students(user_id , roll_no , program , year) values (? ,? ,? ,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,s.getUser_id());
            preparedStatement.setInt(2,s.getRoll_no());
            preparedStatement.setString(3,s.getProgram());
            preparedStatement.setInt(4,s.getYear());
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("User Added Successfully");
            }
            else{
                System.err.println("Error in Adding Student");
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public void getStudentbyId(int user_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "select * from students where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int roll_no = resultSet.getInt("roll_no");
                int year = resultSet.getInt("year");
                String program = resultSet.getString("program");
                System.out.println("Student_id: " + user_id  + "Student RollNo: " + roll_no + "Student Program: " + program + " Student year of enrollment: " + year);

            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public void getStudentbyRollNo(int roll_no){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "select * from students where roll_no = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,roll_no);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int user_id = resultSet.getInt("user_id");
                int year = resultSet.getInt("year");
                String program = resultSet.getString("program");
                System.out.println("Student_id: " + user_id  + "Student RollNo: " + roll_no + "Student Program: " + program + " Student year of enrollment: " + year);

            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public void getAllStudent(){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "select * from students";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int user_id = resultSet.getInt("user_id");
                int roll_no = resultSet.getInt("roll_no");
                int year = resultSet.getInt("year");
                String program = resultSet.getString("program");
                System.out.println("Student_id: " + user_id  + "Student RollNo: " + roll_no + "Student Program: " + program + " Student year of enrollment: " + year);

            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public void deleteStudentbyRollNo(int roll_no){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "delete from students where roll_no = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,roll_no);
            int result = preparedStatement.executeUpdate();
            if(result > 0 ){
                System.out.println("Student Deleted Successfully");
            }
            else{
                System.err.println("Error in Deleting");
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public void deleteStudentbyId(int user_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "delete from students where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0 ){
                System.out.println("Student Deleted Successfully");
            }
            else{
                System.err.println("Error in Deleting");
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }


}
