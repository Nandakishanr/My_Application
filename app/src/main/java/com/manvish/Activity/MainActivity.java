package com.manvish.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.manvish.Interfaces.CommanInterface;
import com.manvish.Model.MainActivityModel;
import com.manvish.Presenter.MainActivityPresenter;
import com.manvish.Util.VideoList;
import com.manvish.my_application.R;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements CommanInterface.MainActivityView {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    boolean doubleBackToExitPressedOnce = false;
    private ProgressDialog pDialog;
    private MainActivityPresenter mainActivityPresenter;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.logged_out_msg), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        mainActivityPresenter = new MainActivityPresenter(MainActivity.this, new MainActivityModel());
        mainActivityPresenter.getVideos();
    }

    @Override
    public void showProgress(String progressbarStr) {
        pDialog.setMessage(progressbarStr);
        pDialog.show();
    }

    @Override
    public void dismissProgress() {
        pDialog.dismiss();
    }

    @Override
    public void returnString(List<VideoList> jsonResponse) {
        if (jsonResponse == null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.CustomDialog);
            alertDialogBuilder.setTitle(getString(R.string.Warning));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setMessage(getString(R.string.warning_dialog_msg));
            alertDialogBuilder.setIcon(R.drawable.ic_warning_black_24dp);

            alertDialogBuilder.setNegativeButton(getString(R.string.Cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alertDialogBuilder.setPositiveButton(getString(R.string.TryAgain),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            mainActivityPresenter.getVideos();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            adapter = new RecyclerAdapter(jsonResponse);
            recyclerView.setAdapter(adapter);
        }
    }
}
