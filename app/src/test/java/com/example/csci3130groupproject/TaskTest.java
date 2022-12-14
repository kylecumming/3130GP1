package com.example.csci3130groupproject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TaskTest {

    @Test
    public void setAttributesTask(){
        Task task1 = new Task();
        task1.setTitle("TestTitle");
        task1.setDescription("Test description");
        task1.setTags("Construction");
        task1.setPrice("100");
        task1.setAuthor("user");

        assertEquals("TestTitle", task1.getTitle());
        assertEquals("Test description", task1.getDescription());
        assertEquals("Construction", task1.getTags());
        assertEquals("100", task1.getPrice());
        assertEquals("user", task1.getAuthor());

    }

}
