package com.manvish.Presenter;

import android.os.AsyncTask;

import com.manvish.Interfaces.CommanInterface;

public class ResetPasswordPresenter implements CommanInterface.ResetPasswordPresenter, CommanInterface.ResetPasswordModel.OnFinishedListener {
    private static CommanInterface.ResetPasswordMainView resetPasswordMainView;
    private static CommanInterface.ResetPasswordModel resetPasswordModel;

    public ResetPasswordPresenter(CommanInterface.ResetPasswordMainView resetPasswordMainView, CommanInterface.ResetPasswordModel resetPasswordModel) {
        ResetPasswordPresenter.resetPasswordMainView = resetPasswordMainView;
        ResetPasswordPresenter.resetPasswordModel = resetPasswordModel;
    }

    @Override
    public void sendPasswordResetEmail(String email) {

        new sendPasswordResetEmailAsync("Please Wait...", email).execute();

    }

    @Override
    public void onFinished(String returnString) {
        if (resetPasswordMainView != null) {
            resetPasswordMainView.returnString(returnString);

            resetPasswordMainView.dismissProgress();
        }
    }

    private class sendPasswordResetEmailAsync extends AsyncTask<Void, String, String> {

        private final String progressbarStr;
        private final String email;

        private sendPasswordResetEmailAsync(String progressbarStr, String email) {
            this.progressbarStr = progressbarStr;
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (resetPasswordMainView != null) {
                resetPasswordMainView.dismissProgress();
                resetPasswordMainView.showProgress(this.progressbarStr);
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            resetPasswordModel.sendPasswordResetEmail(email, ResetPasswordPresenter.this);
            return null;

        }

    }

}
