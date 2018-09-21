package com.manvish.Presenter;

import android.os.AsyncTask;

import com.manvish.Interfaces.CommanInterface;

public class SignInPresenter implements CommanInterface.SignInPresenter, CommanInterface.SignInModel.OnFinishedListener {
    private static CommanInterface.SignInMainView signInMainView;
    private static CommanInterface.SignInModel signInModel;

    public SignInPresenter(CommanInterface.SignInMainView signInMainView, CommanInterface.SignInModel signInModel) {
        SignInPresenter.signInMainView = signInMainView;
        SignInPresenter.signInModel = signInModel;
    }

    @Override
    public void sendPasswordResetEmail(String email,String password) {

        new sendPasswordResetEmailAsync("Please Wait...", email,password).execute();

    }

    @Override
    public void onFinished(String returnString) {
        if (signInMainView != null) {
            signInMainView.returnString(returnString);

            signInMainView.dismissProgress();
        }
    }

    private class sendPasswordResetEmailAsync extends AsyncTask<Void, String, String> {

        private final String progressbarStr;
        private final String email;
        private final String password;

        private sendPasswordResetEmailAsync(String progressbarStr, String email, String password) {
            this.progressbarStr = progressbarStr;
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (signInMainView != null) {
                signInMainView.dismissProgress();
                signInMainView.showProgress(this.progressbarStr);
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            signInModel.signInWithEmailAndPassword(email, password,SignInPresenter.this);
            return null;

        }

    }

}
