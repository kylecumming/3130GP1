package com.example.csci3130groupproject;

public class Task {

    private String title, description, tags, price, author;

    public Task(String title, String description, String tags, String price, String author){
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.price = price;
        this.author = author;
    }

    public Task(){ }

    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setTags(String tags){
        this.tags = tags;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public String getTags(){
        return this.tags;
    }
    public String getPrice(){
        return this.price;
    }
    public String getAuthor(){
        return this.author;
    }

}
