package com.manvish.Model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.manvish.Interfaces.CommanInterface;

import java.util.Objects;

public class SignInModel implements CommanInterface.SignInModel {

    @Override
    public void signInWithEmailAndPassword(String email, final String password, final OnFinishedListener onFinishedListener) {


        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            onFinishedListener.onFinished(Objects.requireNonNull(task.getException()).getMessage());
                        } else {
                            onFinishedListener.onFinished("SUCCESS");
                        }
                    }
                });

    }
}
