package com.example.csci3130groupproject;

import android.app.Application;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

    @Test
    public void setAttributesApplication(){
        TaskApplication newApp = new TaskApplication();
        newApp.setTaskTitle("TestTitle");
        newApp.setApplicant("Employee5");

        assertEquals("TestTitle", newApp.getTaskTitle());
        assertEquals("Employee5", newApp.getApplicant());
    }

    @Test
    public void constructorWithArguments(){
        TaskApplication newApp = new TaskApplication("TestTitle", "Employee5");

        assertEquals("TestTitle", newApp.getTaskTitle());
        assertEquals("Employee5", newApp.getApplicant());
    }

}
