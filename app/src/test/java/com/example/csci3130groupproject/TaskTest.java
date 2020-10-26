package com.example.csci3130groupproject;

import org.junit.Test;

public class TaskTest {

    @Test
    public void setAttributesTask(){
        Task task1 = new Task();
        task1.setTitle("TestTitle");
        task1.setDescription("Test description");
        task1.setPrice("100");

        assertEquals("TestTitle", task1.getTitle());
        assertEquals("Test description", task1.getDescription());
        assertEquals("100", task1.getPrice());

    }

}
