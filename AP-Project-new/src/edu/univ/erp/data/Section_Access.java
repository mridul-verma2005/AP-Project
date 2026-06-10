package edu.univ.erp.data;

import edu.univ.erp.domain.Section;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Section_Access {
    public int addSection(Section s){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("insert into sections (section_id , course_code , instructor_username,  day_time , room, capacity ,current_avalible_seats, semester , year) values (?,?,?,?,?,?,?,?,?)");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,s.getSection_id());
            preparedStatement.setString(2,s.getCourse_code());
            preparedStatement.setString(3,s.getInstructor_username());
            preparedStatement.setString(4,s.getDaytime());
            preparedStatement.setString(5,s.getRoom());
            preparedStatement.setInt(6,s.getCapacity());
            preparedStatement.setInt(7,s.getCurrent_avalible_seats());
            preparedStatement.setString(8,s.getSemester());
            preparedStatement.setInt(9,s.getYear());
            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Sections Added Successfully");
            }
            else{
                System.out.println("Error in Adding Section");
            }
            return result;


        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void updateSection(String course_code, String instructor_username, String day_time, String room, int capacity, int current_available_seats, String semester, int year, String section_id
    ){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "update sections set course_code = ?, instructor_username = ?, day_time = ?, room = ?, capacity = ?, current_avalible_seats = ?, semester = ?, year = ? where section_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, course_code);
            preparedStatement.setString(2, instructor_username);
            preparedStatement.setString(3, day_time);
            preparedStatement.setString(4, room);
            preparedStatement.setInt(5, capacity);
            preparedStatement.setInt(6, current_available_seats);
            preparedStatement.setString(7, semester);
            preparedStatement.setInt(8, year);
            preparedStatement.setString(9, section_id);

            int result = preparedStatement.executeUpdate();
            if(result > 0){
                System.out.println("Course Code Updated Successfully");
            }
            else{
                System.out.println("Error in updating course_code");
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }


    public void updateSectionInstructor(String instructor_username , String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set instructor_username = ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,instructor_username);
            preparedStatement.setString(2,section_id);
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
    public void updateSectionDaytime(String daytime , String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set daytime = ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,daytime);
            preparedStatement.setString(2,section_id);
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
    public void updateSectionRoom(int room , String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set room = ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,room);
            preparedStatement.setString(2,section_id);
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
    public void updateSectionSemester(String semester , String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set semester = ? where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,semester);
            preparedStatement.setString(2,section_id);
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
    public void deleteSection(String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("delete from sections where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,section_id);
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



    public Object[][] getCatalog(){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select sections.course_code, sections.section_id, sections.day_time, courses.title, courses.credits, sections.capacity, instructors.instructor_name from sections join instructors on sections.instructor_username = instructors.username join courses on sections.course_code = courses.course_code");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Object[]> datalist  = new ArrayList<>();
            while(resultSet.next()){
                String course_code = resultSet.getString("course_code");
                String section_id = resultSet.getString("section_id");
                String day_time = resultSet.getString("day_time");
                String title = resultSet.getString("title");
                int credits = resultSet.getInt("credits");
                String instructor = resultSet.getString("instructor_name");
                Object[] row = {course_code,section_id,day_time, title, credits, instructor, "Register"};
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
    public int currentCapacity(String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            int current_avalible_seats = 0;
            String query = String.format("select current_avalible_seats from sections where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,section_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                current_avalible_seats = resultSet.getInt("current_avalible_seats");
            }

            return current_avalible_seats;

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int change_avalible_seats_reduceby_1(String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set current_avalible_seats = current_avalible_seats - 1 where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,section_id);
            int result = preparedStatement.executeUpdate();
            return result;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int change_avalible_seats_increase_by_1(String section_id){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update sections set current_avalible_seats = current_avalible_seats + 1 where section_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,section_id);
            int result = preparedStatement.executeUpdate();
            return result;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public ResultSet getTimetable(String student_username){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select title, instructor_name from sections join instructors on sections.instructor_id = instructors.user_id join courses on sections.course_code = courses.course_id where student_id = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,student_username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    public Object[][] getAllSectionByUsername(String username){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = "select courses.title, sections.semester, sections.capacity, sections.section_id ,sections.current_avalible_seats from sections join instructors on sections.instructor_username = instructors.username join courses on sections.course_code = courses.course_code where instructors.username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Object[]> datalist  = new ArrayList<>();
            while(resultSet.next()){
                String section_id = resultSet.getString("section_id");
                String title = resultSet.getString("title");
                String semester = resultSet.getString("semester");
                int capacity = resultSet.getInt("capacity");
                int current_avalible_seats = resultSet.getInt("current_avalible_seats");
                int enrolled = capacity - current_avalible_seats;
                Object[] row = {section_id,title,semester,enrolled,"Open Grades"};
//                System.out.println("Instructor_username: " + username  + "Department: " + department );
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
    public Object[][] getAllSections(){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select sections.course_code,sections.room, sections.year, sections.semester, sections.section_id, sections.day_time, sections.capacity, sections.current_avalible_seats,sections.instructor_username from sections");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Object[]> datalist  = new ArrayList<>();
            String[] cols = {
                    "Section ID","Course","Instructor","Time",
                    "Room","Capacity","Year","Semester",
                    "Edit","Delete"
            };
            while(resultSet.next()){
                String course_code = resultSet.getString("course_code");
                String section_id = resultSet.getString("section_id");
                String day_time = resultSet.getString("day_time");
                String instructor_id = resultSet.getString("instructor_username");
                int capacity = resultSet.getInt("capacity");
                int current_avalible_seats = resultSet.getInt("current_avalible_seats");
                String room = resultSet.getString("room");
                int year = resultSet.getInt("year");
                String semester = resultSet.getString("semester");

                Object[] row = {section_id,course_code,instructor_id,day_time,room,capacity,current_avalible_seats,year,semester ,"Edit","Delete"};
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
