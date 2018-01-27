
package com.rajeevjaiswal.mvp.ui.base;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.rajeevjaiswal.mvp.MvpApp;
import com.rajeevjaiswal.mvp.R;
import com.rajeevjaiswal.mvp.di.component.ActivityComponent;
import com.rajeevjaiswal.mvp.di.component.DaggerActivityComponent;
import com.rajeevjaiswal.mvp.di.module.ActivityModule;
import com.rajeevjaiswal.mvp.utils.CommonUtils;
import com.rajeevjaiswal.mvp.utils.NetworkUtils;

import butterknife.Unbinder;

/**
 * Created by rajeev on 16/12/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private AlertDialog progressDialog;

    private ActivityComponent mActivityComponent;

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(MvpApp.get(this).getComponent())
                .build();

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }


    @Override
    public void showLoading(int resId) {
        showLoading(getString(resId));
    }

    @Override
    public void showLoading(String message) {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(this,message);
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }


    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    protected abstract void setUp();
}
