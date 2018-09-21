package com.manvish.Presenter;

import android.os.AsyncTask;

import com.manvish.Interfaces.CommanInterface;

public class SignUpPresenter implements CommanInterface.SignUpPresenter, CommanInterface.SignUpModel.OnFinishedListener {
    private static CommanInterface.SignUpMainView signUpMainView;
    private static CommanInterface.SignUpModel signUpModel;

    public SignUpPresenter(CommanInterface.SignUpMainView SignUpMainView, CommanInterface.SignUpModel SignUpModel) {
        SignUpPresenter.signUpMainView = SignUpMainView;
        SignUpPresenter.signUpModel = SignUpModel;
    }

    @Override
    public void createUserWithEmailAndPassword(String email,String password) {

        new createUserWithEmailAndPasswordAsync("Please Wait...", email,password).execute();

    }

    @Override
    public void onFinished(String returnString) {
        if (signUpMainView != null) {
            signUpMainView.returnString(returnString);

            signUpMainView.dismissProgress();
        }
    }

    private class createUserWithEmailAndPasswordAsync extends AsyncTask<Void, String, String> {

        private final String progressbarStr;
        private final String email;
        private final String password;

        private createUserWithEmailAndPasswordAsync(String progressbarStr, String email, String password) {
            this.progressbarStr = progressbarStr;
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (signUpMainView != null) {
                signUpMainView.dismissProgress();
                signUpMainView.showProgress(this.progressbarStr);
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            signUpModel.createUserWithEmailAndPassword(email, password,SignUpPresenter.this);
            return null;

        }

    }

}
