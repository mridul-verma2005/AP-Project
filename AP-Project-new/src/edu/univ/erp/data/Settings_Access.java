package edu.univ.erp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Settings_Access {
    public int addsetting(String setting_key , int value){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("insert into settings (setting_key,value) values (?,?)");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,setting_key);
            preparedStatement.setInt(2,value);
            int result = preparedStatement.executeUpdate();
            return result;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public int editsetting(String setting_key , int value){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("update settings set value = ? where setting_key = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(2,setting_key);
            preparedStatement.setInt(1,value);
            int result = preparedStatement.executeUpdate();
            return result;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public int getsetting(String setting_key){
        try(Connection connection = erp_database_connection.getconnection()){
            String query = String.format("select value from settings where setting_key = ?");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,setting_key);
            ResultSet resultSet = preparedStatement.executeQuery();
            int value = 0;
            if(resultSet.next()){
                value = resultSet.getInt("value");
            }
            return value;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }

    }
}
