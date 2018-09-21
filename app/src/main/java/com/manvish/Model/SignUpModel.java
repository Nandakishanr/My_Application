package com.manvish.Model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manvish.Util.User;
import com.manvish.Interfaces.CommanInterface;

public class SignUpModel implements CommanInterface.SignUpModel {

    @Override
    public void createUserWithEmailAndPassword(String email, final String password, final OnFinishedListener onFinishedListener) {


        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            onFinishedListener.onFinished("SignUp failed");
                        } else {
                            AddUserInfo(task.getResult().getUser().getUid());
                            onFinishedListener.onFinished("SUCCESS");

                        }
                    }
                });


    }

    private void AddUserInfo(String uid) {
        User user = new User(uid);
        DatabaseReference mDB = FirebaseDatabase.getInstance().getReference();
        mDB.child("users").child(uid).setValue(user);
    }
}
