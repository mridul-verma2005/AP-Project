package edu.univ.erp.data;

import edu.univ.erp.domain.Enrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Enrollment_Access {
    public int addEnrollment(Enrollment e){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("insert into enrollments (student_username , section_id , status) values (?,?,?) ");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,e.getStudent_username());
            preparedStatement.setString(2,e.getSection_id());
            preparedStatement.setString(3,e.getStatus());
            int result = preparedStatement.executeUpdate();
            return result;

        }

        catch(SQLException ef){
            ef.printStackTrace();
            return 0;
        }
    }


    public int reactivateEnrollment(String student_username , String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update enrollments set status = 'Active' where student_username = ? and section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,student_username);
            preparedStatement.setString(2,section_id);
            int result = preparedStatement.executeUpdate();
            return result;

        }

        catch(SQLException ef){
            ef.printStackTrace();
            return 0;
        }
    }
    public int deactivateEnrollment(String student_username, String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update enrollments set status = 'deactive' where student_username = ? and section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,student_username);
            preparedStatement.setString(2,section_id);
            int result = preparedStatement.executeUpdate();
            return result;

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;

        }
    }









    public int CheckCurrentEnrollment(String student_username, String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select * from enrollments where student_username = ? and section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,student_username);
            preparedStatement.setString(2,section_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String enrollment_status = resultSet.getString("status");
                if(enrollment_status.equalsIgnoreCase("active")){
                    return 1;
                }
                else if(enrollment_status.equalsIgnoreCase("deactive")){
                    return 2;
                }
                else{
                    return 0;
                }
            }
            else{
                return 0;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;

        }

    }

//    String[] columns = {"Course Code","Section ID", "Timings" ,"Title", "Credits", "Instructor", "Action"};
    public Object[][] seeEnrollmentbyStudent_username(String student_username){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select sections.course_code , sections.section_id , sections.day_time from enrollments join sections on sections.section_id  = enrollments.section_id where student_username = ? and status ='Active'");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,student_username);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Object[]> datalist  = new ArrayList<>();
            while(resultSet.next()){
                String course_code = resultSet.getString("course_code");
                String section_id = resultSet.getString("section_id");
                String day_time = resultSet.getString("day_time");
//                String title = resultSet.getString("title");
//                int credits = resultSet.getInt("credits");
//                String instructor = resultSet.getString("instructor_name");
                Object[] row = {course_code,section_id,day_time,"drop"};
                datalist.add(row);
            }
            Object[][] data = new Object[datalist.size()][];
            for (int i = 0; i < datalist.size(); i++) {
                data[i] = datalist.get(i);
            }
            return data;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;

        }
    }



}
