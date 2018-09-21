package com.manvish.Presenter;

import android.os.AsyncTask;

import com.manvish.Interfaces.CommanInterface;
import com.manvish.Util.VideoList;

import java.util.List;

public class MainActivityPresenter implements CommanInterface.MainActivityPresenter, CommanInterface.MainActivityModel.OnFinishedListener {
    private static CommanInterface.MainActivityView mainActivityView;
    private static CommanInterface.MainActivityModel mainActivityModel;

    public MainActivityPresenter(CommanInterface.MainActivityView mainActivityView, CommanInterface.MainActivityModel mainActivityModel) {
        MainActivityPresenter.mainActivityView = mainActivityView;
        MainActivityPresenter.mainActivityModel = mainActivityModel;
    }

    @Override
    public void getVideos() {

        new getVideosAsync("Fetching Videos...").execute();

    }

    @Override
    public void onFinished(List<VideoList> jsonResponse) {
        if (mainActivityView != null) {
            mainActivityView.returnString(jsonResponse);
            mainActivityView.dismissProgress();
        }
    }

    private class getVideosAsync extends AsyncTask<Void, String, String> {

        private final String progressbarStr;

        private getVideosAsync(String progressbarStr) {
            this.progressbarStr = progressbarStr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mainActivityView != null) {
                mainActivityView.dismissProgress();
                mainActivityView.showProgress(this.progressbarStr);
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            mainActivityModel.getVideos(MainActivityPresenter.this);
            return null;

        }

    }

}
