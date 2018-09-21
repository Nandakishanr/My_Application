package com.manvish.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.manvish.Interfaces.CommanInterface;
import com.manvish.Model.ResetPasswordModel;
import com.manvish.Presenter.ResetPasswordPresenter;
import com.manvish.my_application.R;

public class ResetPasswordActivity extends AppCompatActivity implements CommanInterface.ResetPasswordMainView, View.OnClickListener {

    private EditText emailID_tv;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailID_tv = findViewById(R.id.emailID_tv);
        Button btn_reset_password = findViewById(R.id.btn_reset_password);
        Button btnBack = findViewById(R.id.btn_back);
        progressBar = findViewById(R.id.progressBar);
        btnBack.setOnClickListener(this);
        btn_reset_password.setOnClickListener(this);
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
        Toast.makeText(this, returnString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_reset_password:
                String email = emailID_tv.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter your registered password id", Toast.LENGTH_SHORT).show();
                    return;
                }

                new ResetPasswordPresenter(ResetPasswordActivity.this, new ResetPasswordModel()).sendPasswordResetEmail(email);
                break;
        }
    }
}
