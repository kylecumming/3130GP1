package com.example.csci3130groupproject;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
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
    public void validEmailTest() {
        ArrayList<String> dob = new ArrayList<String>(){{add("asd");add("12");add("1999");}};
        User em1 = new User("test!@#@email.com", "testuser!@#","sample", dob, "M", false );
        User em2 =new User("test@email.com", "testuser123","sample123", dob, "M", false);
        em1.addReview(4, "mow lawn","bad job");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference allUsers = database.getReference("Users");
        allUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userFinal = new User();
                for(DataSnapshot storedUser : snapshot.getChildren()){
                    User user = storedUser.getValue(User.class);
                    if(user.getEmail().equals("exampleEmployer@gmail.com"))
                        userFinal = user;
                }
                System.out.println(userFinal.getReviews());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println(em1.getReviews());
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
