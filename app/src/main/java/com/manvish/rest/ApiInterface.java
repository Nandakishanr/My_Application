package com.manvish.rest;


import com.manvish.Util.VideoList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

@SuppressWarnings("ALL")
public interface ApiInterface {

    @GET("/media.json/")
    Call<List<VideoList>> getVideoList();

}
