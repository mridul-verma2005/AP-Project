package edu.univ.erp.data;

import edu.univ.erp.domain.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Grade_Access {


    public void addGrade(Grade g){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("insert into grades (enrollment_id , component , score , final_grade) values ( ?,?,?,?)");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,g.getEnrollment_id());
            preparedStatement.setString(2,g.getComponent());
            preparedStatement.setDouble(3,g.getScore());
            if(g.getFinal_grade() == null){
                preparedStatement.setString(4,null);
            }
            else{
                preparedStatement.setString(4,g.getFinal_grade());
            }
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Grade Added Successfully");
            }
            else{
                System.err.println("Error in Adding Grade");
            }

        }

        catch (SQLException e){
            e.getMessage();
        }
    }


    public void updateGradeComponent(int grade_id, String component){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update grade set component = ? where grade_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(2,grade_id);
            preparedStatement.setString(1,component);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Grade Component Updated Successfully");
            }
            else{
                System.err.println("Error in Updating Grade Component");
            }

        }

        catch (SQLException e){
            e.getMessage();
        }
    }


    public void updateGradescore(int grade_id, int score){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update grade set score = ? where grade_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(2,grade_id);
            preparedStatement.setInt(1,score);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Grade Score Updated Successfully");
            }
            else{
                System.err.println("Error in Updating Grade Score");
            }

        }

        catch (SQLException e){
            e.getMessage();
        }
    }

    public void updateGradeFinalgrade(int enrollment_id, String final_grade){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update grade set final_grade = ? where enrollment_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,final_grade);
            preparedStatement.setInt(2,enrollment_id);
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Final Grade Updated Successfully in the Enrollment ID: " + enrollment_id);
            }
            else{
                System.err.println("Error in Updating Final Grade in the Enrollment ID: " + enrollment_id);
            }

        }

        catch (SQLException e){
            e.getMessage();
        }
    }

    public void seeGradebyEnrollmentId(int enrollment_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select * from grades where enrollment_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,enrollment_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            // get the grades;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }




}
