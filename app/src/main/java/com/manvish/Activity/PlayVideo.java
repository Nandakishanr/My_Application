package com.manvish.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.manvish.Util.VideoList;
import com.manvish.my_application.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayVideo extends AppCompatActivity implements ValueEventListener, MediaPlayer.OnErrorListener {

    private VideoView video_View;
    private int stopPosition = 0;
    private ArrayList<VideoList> videoLists;
    private int position;
    private Uri uri;
    private ProgressDialog pDialog;
    private DatabaseReference mDB;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        mDB = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        Intent i = getIntent();
        videoLists = (ArrayList<VideoList>) i.getSerializableExtra("LIST");
        position = i.getIntExtra("POSITION", 0);
        video_View = findViewById(R.id.video_View);
        pDialog = new ProgressDialog(PlayVideo.this);
        pDialog.setMessage(getString(R.string.preparing_video));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        RelativeLayout relativelayout = findViewById(R.id.relativelayout);
        relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaController != null)
                    mediaController.show();
            }
        });

        updateUI();
        new StreamVideo().execute();
    }

    public void updateUI() {
        TextView id_tv = findViewById(R.id.id_tv);
        TextView title_tv = findViewById(R.id.title_tv);
        TextView description_tv = findViewById(R.id.description_tv);

        String description = "<font color='#EE0000'>Description: </font>";
        String id = "<font color='#EE0000'>ID: </font>";
        String title = "<font color='#EE0000'>Title: </font>";
        description_tv.setText(Html.fromHtml(description + videoLists.get(position).getDescription()));
        id_tv.setText(Html.fromHtml(id + videoLists.get(position).getId()));
        title_tv.setText(Html.fromHtml(title + videoLists.get(position).getTitle()));
    }

    @Override
    public void onPause() {
        super.onPause();

        if (video_View != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    stopPosition = video_View.getCurrentPosition();
                    videoLists.get(position).setStreaming_time(stopPosition);
                    video_View.pause();
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/listItem/" + videoLists.get(position).getId(), videoLists.get(position));
                    mDB.updateChildren(childUpdates);
                }
            });

        }
    }


    @Override
    public void onResume() {
        super.onResume();

        if (video_View != null) {
            Query query = mDB.child("listItem").child(videoLists.get(position).getId());
            query.addListenerForSingleValueEvent(PlayVideo.this);
        }
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (!dataSnapshot.exists()) {
            stopPosition = 0;
            video_View.seekTo(stopPosition);
            video_View.start();
        } else {
            for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                if (Objects.requireNonNull(jobSnapshot.getKey()).equalsIgnoreCase("streaming_time")) {
                    stopPosition = Integer.parseInt(Objects.requireNonNull(jobSnapshot.getValue()).toString());
                    video_View.seekTo(stopPosition);
                    video_View.start();
                    break;
                }
            }
        }

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
            finish();

        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        pDialog.dismiss();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.CustomDialog);
        alertDialogBuilder.setTitle(getString(R.string.Warning));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(getString(R.string.warning_dialog_msg));
        alertDialogBuilder.setIcon(R.drawable.ic_warning_black_24dp);

        alertDialogBuilder.setNegativeButton(getString(R.string.Cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        video_View = null;
                        finish();
                    }
                });

        alertDialogBuilder.setPositiveButton(getString(R.string.TryAgain),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Query query = mDB.child("listItem").child(videoLists.get(position).getId());
                        query.addListenerForSingleValueEvent(PlayVideo.this);
                        new StreamVideo().execute();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return true;
    }


    private class StreamVideo extends AsyncTask<Void, Void, Void>  {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            try {
                mediaController = new MediaController(PlayVideo.this);
                mediaController.setAnchorView(video_View);

                video_View.setMediaController(mediaController);
                uri = Uri.parse(videoLists.get(position).getUrl());
                video_View.setVideoURI(uri);
                video_View.requestFocus();
                video_View.setOnErrorListener(PlayVideo.this);

                video_View.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        pDialog.dismiss();
                    }
                });

                video_View.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position == videoLists.size())
                            position = 0;

                        uri = Uri.parse(videoLists.get(position).getUrl());
                        video_View.setVideoURI(uri);
                        video_View.suspend();
//                        video_View.start();
                        updateUI();
                        Query query = mDB.child("listItem").child(videoLists.get(position).getId());
                        query.addListenerForSingleValueEvent(PlayVideo.this);
                    }
                });
            } catch (Exception e) {
                pDialog.dismiss();
                e.printStackTrace();
            }

        }

    }

}
