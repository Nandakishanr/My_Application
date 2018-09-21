package com.manvish.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.manvish.Interfaces.CommanInterface;
import com.manvish.Util.VideoList;
import com.manvish.rest.ApiClient;
import com.manvish.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityModel implements CommanInterface.MainActivityModel {

    @Override
    public void getVideos(final CommanInterface.MainActivityModel.OnFinishedListener onFinishedListener) {
        Call<List<VideoList>> call = ApiClient.getClient().create(ApiInterface.class).getVideoList();
        call.enqueue(new Callback<List<VideoList>>() {
            @Override
            public void onResponse(@NonNull Call<List<VideoList>> call, @NonNull Response<List<VideoList>> response) {

                final List<VideoList> jsonResponse = response.body();
                onFinishedListener.onFinished(jsonResponse);
            }

            @Override
            public void onFailure(@NonNull Call<List<VideoList>> call, @NonNull Throwable t) {
                Log.d("kishan", t.getMessage());
                onFinishedListener.onFinished(null);
            }
        });
    }
}
