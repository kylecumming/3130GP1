package com.example.csci3130groupproject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employer {
    private String email;
    private String username;
    private String dob;
    private String gender;

    public Employer(String email, String username, String dob, String gender){
        this.email = email;
        this.username = username;
        this.dob = dob;
        this.gender = gender;
    }
    public Boolean usernameIsValid(){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(username);
        if(matcher.matches()){
            return true;
        }
        else{
            return false;
        }
    }
    public Boolean emailIsValid(){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]+");
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()){
            return true;
        }
        else{
            return false;
        }
    }

    /* Getter / Setters */
    public String getDob() {return dob;}
    public String getEmail() {return email;}
    public String getGender() {return gender;}
    public String getUsername() {return username;}
    public void setDob(String dob) {this.dob = dob;}
    public void setEmail(String email) {this.email = email;}
    public void setGender(String gender) {this.gender = gender;}
    public void setUsername(String username) {this.username = username;}
}
