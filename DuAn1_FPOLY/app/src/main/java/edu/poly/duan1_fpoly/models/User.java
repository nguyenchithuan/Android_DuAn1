package edu.poly.duan1_fpoly.models;

public class User {
    private int id;
    private String username;
    private String email;
    private String pass;
    private String phone;

    public User() {
    }

    public User(int id, String username, String email, String pass, String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
