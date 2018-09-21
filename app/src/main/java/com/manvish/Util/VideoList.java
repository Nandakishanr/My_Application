package com.manvish.Util;

import android.util.Log;

import java.io.Serializable;

public class VideoList implements Serializable {

    String description;
    String id;
    String title;
    String url;
    String thumb;
    int streaming_time = 0;


    public VideoList(String description, String id, String title, String url, String thumb, int streaming_time) {
        this.description = description;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumb = thumb;
        Log.d("kishan", "VideoList:VideoList:" + streaming_time);
        this.streaming_time = streaming_time;
    }

    public int getStreaming_time() {

        return streaming_time;
    }

    public void setStreaming_time(int streaming_time) {

        this.streaming_time = streaming_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

}
