package com.example.csci3130groupproject;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {
    @Test
    public void validUsernameTest(){
        ArrayList<String> dob = new ArrayList<String>(){{add("asd");add("12");add("1999");}};
        User em1 = new User("test!@#@email.com", "testuser!@#","sample", dob, "M", false );
        User em2 =new User("test@email.com", "testuser123","sample123", dob, "M", false);
        assertTrue(em2.usernameIsValid());
        assertFalse(em1.usernameIsValid());
        assertFalse(em1.dobIsValid());
    }
    @Test
    public void validEmailTest(){
        ArrayList<String> dob = new ArrayList<String>(){{add("asd");add("12");add("1999");}};
        User em1 = new User("test!@#@email.com", "testuser!@#","sample", dob, "M", false );
        User em2 =new User("test@email.com", "testuser123","sample123", dob, "M", false);
        assertTrue(em2.emailIsValid());
        assertFalse(em1.emailIsValid());
    }
    @Test
    public void validPasswordTest(){
        ArrayList<String> dob = new ArrayList<String>(){{add("asd");add("12");add("1999");}};
        User em1 = new User("test!@#@email.com", "testuser!@#","sample", dob, "M", false );
        User em2 =new User("test@email.com", "testuser123","sample123", dob, "M", false);
        assertFalse(em1.passwordIsValid());
        assertTrue(em2.passwordIsValid());
    }
}
