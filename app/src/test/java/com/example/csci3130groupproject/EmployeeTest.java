package com.example.csci3130groupproject;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {
    @Test
    public void validUsernameTest(){
        Employee em1 = new Employee("test!@#@email.com", "testuser!@#","04/11/2004", "M" );
        Employee em2 =new Employee("test@email.com", "testuser123","04/11/2004", "M" );
        assertTrue(em2.usernameIsValid());
        assertFalse(em1.usernameIsValid());
    }
    @Test
    public void validEmailTest(){
        Employee em1 = new Employee("test!@#@email.com", "testuser","04/11/2004", "M" );
        Employee em2 =new Employee("test@email.com", "testuser","04/11/2004", "M" );
        assertTrue(em2.emailIsValid());
        assertFalse(em1.emailIsValid());
    }
}
