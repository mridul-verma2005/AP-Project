package edu.univ.erp.auth;
import edu.univ.erp.data.auth_database_connection;
import edu.univ.erp.util.Hashing_Password;

import java.sql.*;
import java.util.ArrayList;

public class UserAuth_Access {
    public int addUser(UserAuth u) {
        try (Connection connection = auth_database_connection.getconnection()) {
//            String password = Hashing_Password.hashPassword(u.getPassword_hash());
            String query = String.format("insert into user_auth (username , role , password_hash , status , last_login) values (?,?,?,?,?)");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, u.getUsername());
            preparedStatement.setString(2, u.getRole());
            preparedStatement.setString(3,u.getPassword_hash());
            preparedStatement.setString(4, u.getStatus());
            preparedStatement.setTimestamp(5, u.getLast_login());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("added successfully");
            } else {
                System.out.println("error in adding");
            }
            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
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

    public Object[][] getAllUser() {
        try (Connection connection = auth_database_connection.getconnection()) {
            String query = String.format("select username , role , status from user_auth");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Object[]> list = new ArrayList<>();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String role = resultSet.getString("role");
                String status = resultSet.getString("status");
                Object[] row  = {username,status,role,"Edit","Delete"};
                list.add(row);
            }
            Object[][] data = new Object[list.size()][];
            for (int i = 0; i < list.size(); i++) {
                data[i] = list.get(i);
            }
            return data;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public int updateLoginTime(String username) {
        try (Connection connection = auth_database_connection.getconnection()) {
            String query = String.format("update user_auth set last_login = NOW() where username = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("updated");
            } else {
                System.out.println("error in updating");
            }
            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int updateuser_withpassword( String role , String status,String password_hash,String username) {
        try (Connection connection = auth_database_connection.getconnection()) {
            Hashing_Password hashingPassword = new Hashing_Password();
//            String password_hashed = hashingPassword.hashPassword(password_hash);
            String query = String.format("update user_auth set password_hash = ?, status = ? , role = ? where username = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,password_hash);
            preparedStatement.setString(2, status);
            preparedStatement.setString(3,role);
            preparedStatement.setString(4,username);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("updated");
            } else {
                System.out.println("error in updating");
            }
            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public int updateuser_withoutpassword( String role , String status,String username) {
        try (Connection connection = auth_database_connection.getconnection()) {
            String query = String.format("update user_auth set status = ? , role = ? where username = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2,role);
            preparedStatement.setString(3,username);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("updated");
            } else {
                System.out.println("error in updating");
            }
            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }



    public int verify_user(String password_hash, String username) {
        try (Connection connection = auth_database_connection.getconnection()) {
            Hashing_Password hashingPassword = new Hashing_Password();
            String password_hashed = hashingPassword.hashPassword(password_hash);
            String query = String.format("select * from user_auth where password_hash = ? and username = ? ");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password_hash);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            String role;
            if (resultSet.next()) {
                role = resultSet.getString("role");
                if(role.equalsIgnoreCase("student")){
                    return 1;
                }
                else if(role.equalsIgnoreCase("instructor")){
                    return 2;
                }
                else if(role.equalsIgnoreCase("admin")){
                    return 3;
                }
            } else {
                return 0;
            }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;

        }
        return 0;
    }

    public int deleteUser(String username) {
        try (Connection connection = auth_database_connection.getconnection()) {
            String query = String.format("delete from user_auth where username = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("added successfully");
            } else {
                System.out.println("error in adding");
            }
            return result;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }


}

