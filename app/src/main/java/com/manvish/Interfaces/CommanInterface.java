package com.manvish.Interfaces;

import com.manvish.Util.VideoList;

import java.util.List;

public interface CommanInterface {


    interface ResetPasswordModel {
        void sendPasswordResetEmail(String email, ResetPasswordModel.OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onFinished(String returnString);
        }
    }

    interface ResetPasswordMainView {
        void showProgress(String progressbarStr);

        void dismissProgress();

        void returnString(String returnString);

    }

    interface ResetPasswordPresenter {
        void sendPasswordResetEmail(String email);
    }


    interface SignUpModel {
        void createUserWithEmailAndPassword(String email, String password, SignUpModel.OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onFinished(String returnString);
        }
    }

    interface SignUpMainView {
        void showProgress(String progressbarStr);

        void dismissProgress();

        void returnString(String returnString);
    }

    interface SignUpPresenter {
        void createUserWithEmailAndPassword(String email,String password);
    }


    interface SignInModel {
        void signInWithEmailAndPassword(String email,String password, SignInModel.OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onFinished(String returnString);
        }
    }

    interface SignInMainView {
        void showProgress(String progressbarStr);

        void dismissProgress();

        void returnString(String returnString);
    }

    interface SignInPresenter {
        void sendPasswordResetEmail(String email,String password);
    }


    interface MainActivityModel {
        void getVideos(MainActivityModel.OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onFinished(List<VideoList> jsonResponse);
        }
    }

    interface MainActivityView {
        void showProgress(String progressbarStr);

        void dismissProgress();

        void returnString(List<VideoList> returnString);
    }

    interface MainActivityPresenter {
        void getVideos();
    }

}
