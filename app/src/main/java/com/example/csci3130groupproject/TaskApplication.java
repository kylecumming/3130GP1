package com.example.csci3130groupproject;

public class TaskApplication {

    private String taskTitle, applicant;

    public TaskApplication(){}

    public TaskApplication(String taskTitle, String applicant){
        this.taskTitle = taskTitle;
        this.applicant = applicant;
    }

    //Getters/Setters
    public void setTaskTitle(String taskTitle){this.taskTitle = taskTitle;}
    public void setApplicant(String applicant){this.applicant = applicant;}
    public String getTaskTitle(){return this.taskTitle;}
    public String getApplicant(){return this.applicant;}

}
