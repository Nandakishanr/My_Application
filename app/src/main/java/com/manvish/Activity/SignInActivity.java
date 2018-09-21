package com.manvish.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.manvish.Interfaces.CommanInterface;
import com.manvish.Model.SignInModel;
import com.manvish.Presenter.SignInPresenter;
import com.manvish.my_application.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, CommanInterface.SignInMainView {

    private EditText emailID_tv, password_tv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailID_tv = findViewById(R.id.emailID_tv);
        password_tv = findViewById(R.id.password);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_reset_password = findViewById(R.id.btn_reset_password);
        TextView btn_signup = findViewById(R.id.btn_signup);
        progressBar = findViewById(R.id.progressBar);

        btn_login.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
        btn_reset_password.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String email = emailID_tv.getText().toString();
                final String password = password_tv.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                new SignInPresenter(SignInActivity.this, new SignInModel()).sendPasswordResetEmail(email, password);

                break;

            case R.id.btn_signup:
                startActivity(new Intent(SignInActivity.this, SignupActivity.class));
                break;

            case R.id.btn_reset_password:
                startActivity(new Intent(SignInActivity.this, ResetPasswordActivity.class));
                break;
        }
    }

    @Override
    public void showProgress(String progressbarStr) {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void returnString(String returnString) {

        if (returnString.equalsIgnoreCase("SUCCESS")) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(emailID_tv.getWindowToken(), 0);
            }

            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, returnString, Toast.LENGTH_SHORT).show();
        }
    }
}
