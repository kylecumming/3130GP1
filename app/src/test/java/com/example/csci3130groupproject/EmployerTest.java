package com.example.csci3130groupproject;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmployerTest {
    @Test
    public void validUsernameTest(){
        Employer em1 = new Employer("test!@#@email.com", "testuser!@#","04/11/2004", "M", "sample" );
        Employer em2 = new Employer("test@email.com", "testuser123","04/11/2004", "M" , "sample");
        assertTrue(em2.usernameIsValid());
        assertFalse(em1.usernameIsValid());
    }
    @Test
    public void validEmailTest(){
        Employer em1 = new Employer("test!@#@email.com", "testuser","04/11/2004", "M", "sample" );
        Employer em2 = new Employer("test@email.com", "testuser","04/11/2004", "M", "sample" );
        assertTrue(em2.emailIsValid());
        assertFalse(em1.emailIsValid());
    }
    @Test
    public void validPasswordTest(){
        Employer em1 = new Employer("test!@#@email.com", "testuser","04/11/2004", "M", "sample123" );
        Employer em2 = new Employer("test@email.com", "testuser","04/11/2004", "M", "sample" );
        assertTrue(em1.passwordIsValid());
        assertFalse(em2.passwordIsValid());
    }
}
