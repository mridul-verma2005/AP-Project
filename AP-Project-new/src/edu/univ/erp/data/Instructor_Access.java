package edu.univ.erp.data;

import edu.univ.erp.domain.Instructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Instructor_Access {

    public int AddInstructor(Instructor i){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "insert into instructors(username,department) values (? ,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,i.getUsername());
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
        return 0;
    }


    public Object[] getInstructorbyUsername(String username){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "select * from instructors where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            Object[] arr = new Object[2];
            if(resultSet.next()){
                String instructor_name = resultSet.getString("instructor_name");
                String dept = resultSet.getString("department");
                arr[0] = instructor_name;
                arr[1] = dept;
                return arr;
            }
            else {
               return null;
            }



        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;

        }
    }

}
