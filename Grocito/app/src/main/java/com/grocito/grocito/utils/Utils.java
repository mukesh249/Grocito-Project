package com.grocito.grocito.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import dmax.dialog.SpotsDialog;

import com.grocito.grocito.activities.Splash;
import com.grocito.grocito.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private static AlertDialog retryCustomAlert;
    public static boolean isShown = false;
    public static AlertDialog progressDialog;

    public static boolean isOnline(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info != null)
            if (info.isConnected()) {
                return true;
            } else {
                return false;
            }
        else
            return false;
    }

    public static AlertDialog retryAlertDialog(Context mContext, String title, String
            msg, String firstButton, String SecondButton, View.OnClickListener secondButtonClick) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.retry_alert, null);
        dialogBuilder.setView(dialogView);

        TextView txtRAMsg = (TextView) dialogView.findViewById(R.id.txtRAMsg);
        TextView txtRAFirst = (TextView) dialogView.findViewById(R.id.txtRAFirst);
        TextView txtRASecond = (TextView) dialogView.findViewById(R.id.txtRASecond);
        View deviderView = (View) dialogView.findViewById(R.id.deviderView);

        txtRAMsg.setText(msg);

        if (firstButton.length() == 0) {
            txtRAFirst.setVisibility(View.GONE);
            deviderView.setVisibility(View.GONE);
        } else {
            txtRAFirst.setVisibility(View.VISIBLE);
            txtRAFirst.setText(firstButton);
        }

        if (SecondButton.length() == 0) {
            txtRASecond.setVisibility(View.GONE);
            deviderView.setVisibility(View.GONE);
        } else {
            txtRASecond.setVisibility(View.VISIBLE);
            txtRASecond.setText(SecondButton);
        }

        txtRAFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryCustomAlert.dismiss();
                isShown = false;
            }
        });

        if (secondButtonClick == null) {
            secondButtonClick = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    retryCustomAlert.dismiss();
                    isShown = false;
                }
            };
        }
        txtRASecond.setOnClickListener(secondButtonClick);

        if (!isShown) {

            retryCustomAlert = dialogBuilder.create();
            retryCustomAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            retryCustomAlert.show();
            isShown = true;
        }
        return retryCustomAlert;
    }

    public static void logout(Activity mContext) {
//        SharedPrefManager.setLogin(mContext, false);
//        SharedPrefManager.setUserModelJSON(mContext, "");
//        SharedPrefManager.setUserId(mContext, "");
        mContext.startActivity(new Intent(mContext, Splash.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        isShown = false;
    }

    public static void strikeText(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static void Toast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

    public static String FirstLatterCap(String string) {
        String fl = string.substring(0, 1).toUpperCase();
        return string.replaceFirst(string.substring(0, 1), fl);
    }

    public static boolean checkEmptyNull(String string) {
        return !TextUtils.isEmpty(string) && !string.equals("null");
    }

    public static boolean checkEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    public static void alertDialog(Context context, String title, String msg) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.retry_alert, null);
        dialogBuilder.setView(dialogView);

        TextView txtRAMsg = (TextView) dialogView.findViewById(R.id.txtRAMsg);
        TextView txtRAFirst = (TextView) dialogView.findViewById(R.id.txtRAFirst);
        TextView txtRASecond = (TextView) dialogView.findViewById(R.id.txtRASecond);
        View deviderView = (View) dialogView.findViewById(R.id.deviderView);

        AlertDialog dialog = dialogBuilder.create();
        txtRAMsg.setText(msg);
        txtRAFirst.setText("Ok");
        txtRASecond.setVisibility(View.GONE);

        txtRAFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public static void setImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url).into(imageView);
    }

    public static void ProgressShow(Context context, AVLoadingIndicatorView loadingIndicatorView, View recyclerView) {
        loadingIndicatorView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public static void ProgressHide(Context context, AVLoadingIndicatorView loadingIndicatorView, View recyclerView) {
        loadingIndicatorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public static void CartCount(Context context, TextView view, int count) {
        if (view != null) {
            if (count > 0) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);

            }
            view.setText(String.valueOf(count));
        }
    }

    public static void ProgressDialog(Context context) {
        progressDialog = new SpotsDialog(context, R.style.Custom);
        progressDialog.show();
    }

    public static void ProgressDialogHide(Context context) {
        if (progressDialog.isShowing())
        progressDialog.dismiss();
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDateFromDateString(String inputDateFormat, String outputDateFormat, String inputDate) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat);
        Date date = inputFormat.parse(inputDate);
        return outputFormat.format(date);
    }

}
