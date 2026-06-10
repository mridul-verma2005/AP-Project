package edu.univ.erp.auth;

public class Current_session {
    private String password_hash;
    private String username;

    public Current_session(String password_hash, String username) {
        this.password_hash = password_hash;
        this.username = username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
