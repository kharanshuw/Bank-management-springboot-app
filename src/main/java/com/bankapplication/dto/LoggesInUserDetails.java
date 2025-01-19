package com.bankapplication.dto;


public class LoggesInUserDetails {

    private int id;
    private String email;
    private String fname;
    private String lname;
    private String gender;
    private String phoneno;
    private String rolename;

    public LoggesInUserDetails(int id, String email, String fname, String lname, String gender, String phoneno, String rolename) {
        this.id = id;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.phoneno = phoneno;
        this.rolename = rolename;
    }

    public LoggesInUserDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "LoggesInUserDetails{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneno='" + phoneno + '\'' +
                ", rolename =" + rolename +
                '}';
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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}