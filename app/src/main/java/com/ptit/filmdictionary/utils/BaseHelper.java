package com.ptit.filmdictionary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.filmdictionary.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by vanh1200 on 22/10/2019
 */
public class BaseHelper {

    private interface Data {
        String EXPIRED = "Đã hết hạn";
        String DATE_NOW = "Vừa xong";
        String DATE_SECOND = " giây";
        String DATE_MINUTE = " phút";
        String DATE_HOUR = " giờ";
        String DATE_DAY = " ngày";
    }

    public static boolean isInternetOn(Context context) {
        try {
            ConnectivityManager connec = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                return true;
            } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void showToast (Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }


    public static void showCustomSnackbarView(View view, Context context, String content) {
        try {
            Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
//            TextView textView = layout.findViewById(com.google.android.material.R.id.snackbar_text);
//            textView.setVisibility(View.INVISIBLE);
            LayoutInflater mInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View snackView = mInflater.inflate(R.layout.common_snackbar, null);
            TextView tv = snackView.findViewById(R.id.tv_content);
            tv.setText(content);
            layout.addView(snackView, 0);
            layout.setBackground(context.getResources().getDrawable(R.drawable.bg_all_snackbar));
            layout.setPadding(0, 0, 0, 0);
//            final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) layout.getLayoutParams();
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layout.getLayoutParams();
            params.setMargins(context.getResources().getDimensionPixelOffset(R.dimen.all_card_margin_12),
                    0, context.getResources().getDimensionPixelOffset(R.dimen.all_card_margin_12),
                    context.getResources().getDimensionPixelOffset(R.dimen.all_card_margin_12));
            params.height = context.getResources().getDimensionPixelOffset(R.dimen.size_36);
            // Show the Snackbar
            snackbar.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String convertTimeStampToTimeAgo(long timeStamp) {
        long nowMillis = System.currentTimeMillis();
        String dateTimeReturn = "";
        long seconds = TimeUnit.MILLISECONDS.toSeconds(nowMillis - timeStamp);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(nowMillis - timeStamp);
        long hours = TimeUnit.MILLISECONDS.toHours(nowMillis - timeStamp);
        long days = TimeUnit.MILLISECONDS.toDays(nowMillis - timeStamp);
        if (seconds < 30) {
            dateTimeReturn = Data.DATE_NOW;
        } else if (seconds < 60) {
            dateTimeReturn = seconds + Data.DATE_SECOND;
        } else if (minutes < 60) {
            dateTimeReturn = minutes + Data.DATE_MINUTE;
        } else if (hours < 24) {
            dateTimeReturn = hours + Data.DATE_HOUR;
        } else if (days < 30) {
            dateTimeReturn = days + Data.DATE_DAY;
        } else {
            Date date = new Date(timeStamp);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateTimeReturn = "Ngày " + newFormat.format(date);
        }
        return dateTimeReturn;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
