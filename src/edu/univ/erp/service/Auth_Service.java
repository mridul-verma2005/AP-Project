package edu.univ.erp.service;

import edu.univ.erp.auth.UserAuth_Access;

public class Auth_Service {
    public void login(String role , String username , String password_hash){
        UserAuth_Access userAuthAccess = new UserAuth_Access();
        boolean verify = userAuthAccess.verify_user(password_hash,username,role);
        if(verify == false){
            System.err.println("wrong password and/or wrong username and/or wrong role");
        }
        else{
            if(role.equalsIgnoreCase("student")){

            }
            else if(role.equalsIgnoreCase("instructor")){

            }
            else if(role.equalsIgnoreCase("admin")){

            }
            else{

            }
        }

    }
}
