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
import com.manvish.Model.SignUpModel;
import com.manvish.Presenter.SignUpPresenter;
import com.manvish.my_application.R;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, CommanInterface.SignUpMainView {

    private EditText emailID_tv, password_tv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView sign_in_button = findViewById(R.id.sign_in_button);
        Button sign_up_button = findViewById(R.id.sign_up_button);
        emailID_tv = findViewById(R.id.emailID_tv);
        password_tv = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        Button btn_reset_password = findViewById(R.id.btn_reset_password);

        btn_reset_password.setOnClickListener(this);
        sign_in_button.setOnClickListener(this);
        sign_up_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_button:
                final String email = emailID_tv.getText().toString().trim();
                final String password = password_tv.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.email_empty_msg), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.pswrd_empty_msg), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), getString(R.string.pswrd_too_short_msg), Toast.LENGTH_SHORT).show();
                    return;
                }

                new SignUpPresenter(SignupActivity.this, new SignUpModel()).createUserWithEmailAndPassword(email, password);


                break;

            case R.id.sign_in_button:
                finish();
                break;

            case R.id.btn_reset_password:
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
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

        if (returnString.equalsIgnoreCase(getString(R.string.SUCCESS))) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(emailID_tv.getWindowToken(), 0);
            }

            startActivity(new Intent(SignupActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, returnString, Toast.LENGTH_SHORT).show();
        }
    }

}
