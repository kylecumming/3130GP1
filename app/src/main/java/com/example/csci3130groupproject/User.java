package com.example.csci3130groupproject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    protected String email;
    protected String username;
    protected String password;
    protected ArrayList<String> dob;
    protected String gender;
    protected Boolean type;

    public User(){};

    public User(String email, String username, String password, ArrayList<String> dob, String gender, Boolean type) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.gender = gender;
        this.type = type;
    }

    public Boolean passwordIsValid(){
        return password.length() >=8;
    }

    public Boolean usernameIsValid(){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(username);
        if (username.length() >=8){
            if(matcher.matches()){
                return true;
            }
        }
        return false;
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

    public Boolean dobIsValid(){
        return dobDayIsValid() && dobMonthIsValid() && dobYearIsValid();
    }
    public Boolean dobDayIsValid(){
        if (dob.size() ==3){
            Pattern pattern = Pattern.compile("[1-3][0-9]|[1-9]|[0][1-9]");
            Matcher matcher = pattern.matcher(dob.get(1));
            if(matcher.matches()){
                return true;
            }
        }
        return false;
    }
    public Boolean dobMonthIsValid(){
        if (dob.size() ==3){
            Pattern pattern = Pattern.compile("[0][1-9]|[1][0-2]|[1-9]");
            Matcher matcher = pattern.matcher(dob.get(0));
            if(matcher.matches()){
                return true;
            }
        }
        return false;
    }
    public Boolean dobYearIsValid(){
        if (dob.size() ==3){
            return dob.get(2).length()>3 && dob.get(2).length()<5 && Integer.parseInt(dob.get(2)) <2020
                    && Integer.parseInt(dob.get(2)) >1900;
        }
        return false;
    }
    public String toString(){
        return email + " " + username +" " + password + " " + dob + " " + gender + " " + type;
    }
    public Boolean isValid(){
        return this.emailIsValid() && this.usernameIsValid() && this.passwordIsValid() && this.dobIsValid();
    }

    /* Getter / Setters */
    public ArrayList<String> getDob() {return dob;}

    public String getEmail() {return email;}

    public Boolean getType() { return type; }

    public String getGender() {return gender;}

    public String getUsername() {return username;}

    public String getPassword() {return password;}

    public void setDob(ArrayList<String> dob) {this.dob = dob;}

    public void setEmail(String email) {this.email = email;}

    public void setGender(String gender) {this.gender = gender;}

    public void setUsername(String username) {this.username = username;}

    public void setPassword(String password) { this.password = password;}

    public void setType(Boolean type){this.type = type;}
}
