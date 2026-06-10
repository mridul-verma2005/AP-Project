package edu.univ.erp.util;


import org.mindrot.jbcrypt.BCrypt;

public class Hashing_Password {

    public  String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    public  boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
