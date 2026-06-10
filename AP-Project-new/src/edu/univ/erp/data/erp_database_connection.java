package edu.univ.erp.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class erp_database_connection {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/ERP_DB";
    private static final String user_name  = "AP_Assignment";
    private static final String password = "1991";

    public static Connection getconnection(){

        try{
            return DriverManager.getConnection(url, user_name,password);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
