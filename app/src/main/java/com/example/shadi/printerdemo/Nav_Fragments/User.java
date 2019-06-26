package com.example.shadi.printerdemo.Nav_Fragments;

public class User {

    public String fname;
    public String lname;
    public String mobile;
    public String email;
    public String password;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fname,String lname, String email, String mobile, String password ) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.mobile = mobile;
        this.password = password;

    }

}