package edu.univ.erp.service;

import edu.univ.erp.auth.UserAuth_Access;
import edu.univ.erp.util.Hashing_Password;

public class Auth_Service {
    public int login(String password_hash , String username){
        UserAuth_Access userAuthAccess = new UserAuth_Access();
//        String password_hashed = Hashing_Password.hashPassword(password_hash);
        int verify = userAuthAccess.verify_user(password_hash,username);
        return verify;

    }
    public int update_lastlogin(String username){
        UserAuth_Access userAuthAccess = new UserAuth_Access();
        return userAuthAccess.updateLoginTime(username);
    }
}
