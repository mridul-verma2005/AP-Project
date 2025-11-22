package edu.univ.erp.data;

import edu.univ.erp.domain.Instructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Instructor_Access {

    public void AddInstructor(Instructor i){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "insert into students(user_id , department) values (? ,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,i.getUser_id());
            preparedStatement.setString(2,i.getDepartment());
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


    public void getInstructorbyID(int user_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "select * from instructors where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String department = resultSet.getString("department");
                System.out.println("Instructor_id: " + user_id  + "Department: " + department );

            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public void getAllInstructors(){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "select * from instructors";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int user_id = resultSet.getInt("user_id");
                String department = resultSet.getString("department");
                System.out.println("Instructor_id: " + user_id  + "Department: " + department );

            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }



    public void deleteInstructorById(int user_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "delete from instructors where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0 ){
                System.out.println("Instructor Deleted Successfully");
            }
            else{
                System.err.println("Error in Deleting");
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public void updateInstructorById(int user_id , String department){
        try(Connection connection  = erp_database_connection.getconnection()){
            String query = "update instructors set department = ? where user_id = ?(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,department);
            preparedStatement.setInt(2,user_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Department Updated Successfully");
            }
            else{
                System.err.println("Error in Updating");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
