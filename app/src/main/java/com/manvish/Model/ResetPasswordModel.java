package com.manvish.Model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.manvish.Interfaces.CommanInterface;

public class ResetPasswordModel implements CommanInterface.ResetPasswordModel {
    @Override
    public void sendPasswordResetEmail(String email, final CommanInterface.ResetPasswordModel.OnFinishedListener onFinishedListener) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onFinishedListener.onFinished("We have sent you instructions to reset your password!");
                        } else {
                            onFinishedListener.onFinished("Failed to send reset password!");
                        }
                    }
                });

    }

}
