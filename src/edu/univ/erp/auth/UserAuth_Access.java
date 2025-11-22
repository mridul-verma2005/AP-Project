package edu.univ.erp.auth;
import edu.univ.erp.data.auth_database_connection;

import java.sql.*;

public class UserAuth_Access {
    public void addUser(UserAuth u) {
        try (Connection connection = auth_database_connection.getconnection()) {
            String query = String.format("insert into user_auth (username , role , password_hash , status , last_login) values (?,?,?,?,?)");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, u.getUsername());
            preparedStatement.setString(2, u.getRole());
            preparedStatement.setString(3, u.getPassword_hash());
            preparedStatement.setString(4, u.getStatus());
            preparedStatement.setTimestamp(5, u.getLast_login());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("added successfully");
            } else {
                System.out.println("error in adding");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getUser(String username) {
        try (Connection connection = auth_database_connection.getconnection()) {
            String query = String.format("select * from user_auth where username = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //print the result set
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void updateLoginTime(Timestamp time, String username) {
        try (Connection connection = auth_database_connection.getconnection()) {
            String query = String.format("update user_auth set last_login = ? where username = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, time);
            preparedStatement.setString(2, username);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("updated");
            } else {
                System.out.println("error in updating");
            }

        } catch (SQLException e) {

        }
    }

    public void updatePassword(String password_hash, String username) {
        try (Connection connection = auth_database_connection.getconnection()) {
            String query = String.format("update user_auth set password_hhas = ? where username = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password_hash);
            preparedStatement.setString(2, username);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("updated");
            } else {
                System.out.println("error in updating");
            }

        } catch (SQLException e) {

        }
    }



    public boolean verify_user(String password_hash, String username, String role) {
        try (Connection connection = auth_database_connection.getconnection()) {
            String query = String.format("select * from user_auth where password_hash = ? and username = ? and role = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password_hash);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3,role);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        }
    }


}

