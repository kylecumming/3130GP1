package com.example.csci3130groupproject;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {
    @Test
    public void validUsernameTest(){
        Employee em1 = new Employee("test!@#@email.com", "testuser!@#","04/11/2004", "M", "sample" );
        Employee em2 =new Employee("test@email.com", "testuser123","04/11/2004", "M" , "sample");
        assertTrue(em2.usernameIsValid());
        assertFalse(em1.usernameIsValid());
    }
    @Test
    public void validEmailTest(){
        Employee em1 = new Employee("test!@#@email.com", "testuser","04/11/2004", "M", "sample" );
        Employee em2 =new Employee("test@email.com", "testuser","04/11/2004", "M", "sample" );
        assertTrue(em2.emailIsValid());
        assertFalse(em1.emailIsValid());
    }
    @Test
    public void validPasswordTest(){
        Employee em1 = new Employee("test!@#@email.com", "testuser","04/11/2004", "M", "sample123" );
        Employee em2 = new Employee("test@email.com", "testuser","04/11/2004", "M", "sample" );
        assertTrue(em1.passwordIsValid());
        assertFalse(em2.passwordIsValid());
    }
}
