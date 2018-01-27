
package com.rajeevjaiswal.mvp.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;

import com.rajeevjaiswal.mvp.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

/**
 * Created by rajeev on 16/12/17.
 */

public final class CommonUtils {

    private static final String TAG = "CommonUtils";

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static AlertDialog showLoadingDialog(Context context,String message) {
        AlertDialog progressDialog = new SpotsDialog(context,message,R.style.Custom);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }


}
