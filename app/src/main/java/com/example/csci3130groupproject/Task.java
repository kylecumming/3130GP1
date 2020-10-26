package com.example.csci3130groupproject;

public class Task {

    private String title, description, price, username;

    public Task(String title, String description, String price, String username){
        this.title = title;
        this.description = description;
        this.price = price;
        this.username = username;
    }

    public Task(){ }

    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public String getPrice(){
        return this.price;
    }
    public String getUsername(){
        return this.username;
    }

}
