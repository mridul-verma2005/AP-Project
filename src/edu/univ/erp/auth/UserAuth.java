package edu.univ.erp.auth;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserAuth {
    private String username;
    private String role;
    private String password_hash;
    private String status;
    private Timestamp last_login;

    public UserAuth(String username, String role, String password_hash,String status, Timestamp last_login) {
        this.username = username;
        this.role = role;
        this.password_hash = password_hash;
        this.last_login = last_login;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public Timestamp getLast_login() {
        return last_login;
    }

    public void setLast_login(Timestamp last_login) {
        this.last_login = last_login;
    }
}
