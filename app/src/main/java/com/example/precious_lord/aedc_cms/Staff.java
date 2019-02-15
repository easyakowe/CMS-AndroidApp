package com.example.precious_lord.aedc_cms;

public class Staff {

    private int id;
    private String staff_id;
    private String fullname;
    private String email;
    private String phone;
    private String password;

    public Staff(String staff_id, String fullname, String email, String phone, String password) {
        this.staff_id = staff_id;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Staff(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }
}
