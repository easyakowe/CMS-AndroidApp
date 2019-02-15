package com.example.precious_lord.aedc_cms;

public class Complaint {

    private int id;
    private String email;
    private String location;
    private String category;
    private String sub_category;
    private String complaint_type;
    private String complaint_nature;
    private String complaint_detail;
    private String complaint_reply;

    public Complaint(String email, String location, String category, String sub_category,
                     String complaint_type, String complaint_nature, String complaint_detail,
                     String complaint_reply) {
        this.email = email;
        this.location = location;
        this.category = category;
        this.sub_category = sub_category;
        this.complaint_type = complaint_type;
        this.complaint_nature = complaint_nature;
        this.complaint_detail = complaint_detail;
        this.complaint_reply = complaint_reply;
    }

    public Complaint(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getComplaint_type() {
        return complaint_type;
    }

    public void setComplaint_type(String complaint_type) {
        this.complaint_type = complaint_type;
    }

    public String getComplaint_nature() {
        return complaint_nature;
    }

    public void setComplaint_nature(String complaint_nature) {
        this.complaint_nature = complaint_nature;
    }

    public String getComplaint_detail() {
        return complaint_detail;
    }

    public void setComplaint_detail(String complaint_detail) {
        this.complaint_detail = complaint_detail;
    }

    public String getComplaint_reply() {
        return complaint_reply;
    }

    public void setComplaint_reply(String complaint_reply) {
        this.complaint_reply = complaint_reply;
    }
}
