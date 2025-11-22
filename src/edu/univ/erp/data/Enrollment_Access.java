package edu.univ.erp.data;

import edu.univ.erp.domain.Enrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Enrollment_Access {
    public int addEnrollment(Enrollment e){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("insert into enrollments (student_id , section_id , status) values (?,?,?) ");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,e.getStudent_id());
            preparedStatement.setInt(2,e.getSection_id());
            preparedStatement.setString(3,e.getStatus());
            int result = preparedStatement.executeUpdate();
            return result;

        }

        catch(SQLException ef){
            System.out.println(ef.getMessage());
            return 0;
        }
    }
    public int deactivateEnrollment(int student_id , int section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update enrollments set status = deactive where student_id = ? and section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,student_id);
            preparedStatement.setInt(2,section_id);
            int result = preparedStatement.executeUpdate();
            return result;

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;

        }
    }

    public void deleteEnrollment(int enrollment_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("delete from enrollments where enrollment_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,enrollment_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0 ){
                System.out.println("Enrollment Deleted Successfully");
            }
            else{
                System.err.println("Error in Deleting Enrollment");
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public void seeEnrollmentbyStudentId(int student_id){
        try(Connection connection = erp_database_connection.getconnection()){
           String query = String.format("select * from enrollment where student_id = ?");
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setInt(1,student_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
               int enrollment_id =  resultSet.getInt("enrollment_id");
                int section_id = resultSet.getInt("section_id");
                String status = resultSet.getString("status");
                //print statement;

            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }

    }

    public void seeEnrollmentbySectionId(int section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select * from enrollment where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,section_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int enrollment_id =  resultSet.getInt("enrollment_id");
                int student_id = resultSet.getInt("student_id");
                String status = resultSet.getString("status");
                //print statement;

            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

    public void updateEnrollment(int enrollment_id , String status){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update enrollments set status = ? where enrollment_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,status);
            preparedStatement.setInt(2,enrollment_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0 ){
                System.out.println("Enrollment Updated Successfully");
            }
            else{
                System.err.println("Error in Updating Enrollment");
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

}
