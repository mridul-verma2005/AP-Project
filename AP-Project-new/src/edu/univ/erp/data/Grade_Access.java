package edu.univ.erp.data;

import edu.univ.erp.domain.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Grade_Access {


    public void addGrade(Grade g){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("insert into grades (student_username, section_id) values ( ?,?)");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,g.getStudent_username());
            preparedStatement.setString(2,g.getSection_id());
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

    public void updateGradeFinalgrade(String enrollment_id, String final_grade){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update grade set final_grade = ? where enrollment_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,final_grade);
            preparedStatement.setString(2,enrollment_id);
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

    public Object[][] seeGradebyStudentUsername(String student_username){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select grades.quiz, grades.midsems, grades.endsems, grades.final, grades.grade, sections.course_code from grades join sections on sections.section_id = grades.section_id join enrollments on enrollments.section_id = grades.section_id where enrollments.student_username = ? and enrollments.status = 'Active'");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,student_username);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Object[]> datalist = new ArrayList<>();
            while(resultSet.next()){
                String course_code = resultSet.getString("course_code");
                int quiz = resultSet.getInt("quiz");
                int midsems = resultSet.getInt("midsems");
                int endsems = resultSet.getInt("endsems");
                int final_value = resultSet.getInt("final");
                String grade = resultSet.getString("grade");
                Object[] arr = {course_code,quiz,midsems,endsems,final_value,grade};
                datalist.add(arr);
            }
            Object[][] data = new Object[datalist.size()][];
            for (int i = 0; i < datalist.size(); i++) {
                data[i] = datalist.get(i);
            }
            return data;

            // get the grades;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    public int updateGrade(String student_username , String section_id, int quiz , int midsems,int endsems, int final_value,String grade){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update grades set quiz=?, midsems=?, endsems=? , final = ?, grade = ? where student_username=? AND section_id=?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,quiz);
            preparedStatement.setInt(2,midsems);
            preparedStatement.setInt(3,endsems);
            preparedStatement.setInt(4,final_value);
            preparedStatement.setString(5,grade);
            preparedStatement.setString(6,student_username);
            preparedStatement.setString(7,section_id);
            int result = preparedStatement.executeUpdate();
            return result;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public Object[][] getAllStudentGrades(String section_id){
        try (Connection connection = erp_database_connection.getconnection()) {

            String query = "select students.student_name, grades.student_username, grades.quiz, grades.midsems, grades.endsems, grades.final, grades.grade from grades join students on grades.student_username = students.username join enrollments on grades.student_username = enrollments.student_username and grades.section_id = enrollments.section_id where grades.section_id = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, section_id);

            ResultSet rs = ps.executeQuery();

            ArrayList<Object[]> datalist = new ArrayList<>();

            while (rs.next()) {
                Object[] row = new Object[]{
                        rs.getString("student_username"),
                        rs.getString("student_name"),
                        rs.getInt("quiz"),
                        rs.getInt("midsems"),
                        rs.getInt("endsems"),
                        rs.getInt("final"),
                        rs.getString("grade")
                };
                datalist.add(row);
            }

            return datalist.toArray(new Object[0][]);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteGrade(String student_username , String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("delete from grades where student_username = ? and section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,student_username);
            preparedStatement.setString(2,section_id);
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




}
