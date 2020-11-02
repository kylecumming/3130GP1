package com.example.csci3130groupproject;

public class TaskApplication {

    private String taskTitle, applicant, author;

    public TaskApplication(){}

    public TaskApplication(String taskTitle, String applicant, String author){
        this.taskTitle = taskTitle;
        this.applicant = applicant;
        this.author = author;
    }

    //Getters/Setters
    public void setTaskTitle(String taskTitle){this.taskTitle = taskTitle;}
    public void setApplicant(String applicant){this.applicant = applicant;}
    public void setAuthor(String author){this.author = author;}
    public String getTaskTitle(){return this.taskTitle;}
    public String getApplicant(){return this.applicant;}
    public String getAuthor(){return this.author;}

}
