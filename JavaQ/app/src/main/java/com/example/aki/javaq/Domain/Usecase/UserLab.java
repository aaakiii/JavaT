package com.example.aki.javaq.Domain.Usecase;

import com.example.aki.javaq.Domain.Entity.User;

/**
 * Created by MinaFujisawa on 2017/07/20.
 */

public class UserLab {
    public static final String USERS_CHILD = "users";

//    public void addNewUser(String userId, String userName, String userIcon) {
//        User user = new User(userName, userIcon);
//        FirebaseLab.getFirebaseDatabaseReference().child(USERS_CHILD).child(userId).setValue(user);
//    }
    public void newChild(){
        //New Child entries
        FirebaseLab.SetConfig();
        FirebaseLab.fetchConfig();
    }
    public void addNewUser(User user) {
        FirebaseLab.getFirebaseDatabaseReference().child(USERS_CHILD).child(user.getmUserId().toString()).setValue(user);
    }
}
