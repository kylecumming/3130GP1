package com.example.csci3130groupproject;

import android.app.Application;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

    @Test
    public void setAttributesApplication(){
        TaskApplication newApp = new TaskApplication();
        newApp.setTaskTitle();
        newApp.setApplicant();

        assertEquals("TestTitle", newApp.getTaskTitle());
        assertEquals("Employee5", newApp.getApplicant());
    }

}
