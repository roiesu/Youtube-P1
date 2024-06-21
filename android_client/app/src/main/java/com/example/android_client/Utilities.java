package com.example.android_client;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
    public static String dateDiff(Date date) {
        long now = new Date().getTime();
        long diffTime = (long) Math.floor(Math.abs(now - date.getTime()) / 1000);
        if (diffTime < 60) {
            return diffTime + " seconds ago";
        }
        diffTime = (long) Math.floor(diffTime / 60);
        if (diffTime < 60) {
            return diffTime + " minutes ago";
        }
        diffTime = (long) Math.floor(diffTime / 60);
        if (diffTime < 24) {
            return diffTime + " hours ago";
        }
        diffTime = (long) Math.floor(diffTime / 24);
        if (diffTime < 30) {
            return diffTime + " days ago";
        }
        diffTime = (long) Math.floor(diffTime / 30);
        if (diffTime < 12) {
            return diffTime + " months ago";
        }
        diffTime = (long) Math.floor(diffTime / 12);
        return diffTime + " years ago";
    }

    public static String numberFormatter(long number) {
        StringBuilder res = new StringBuilder("");
        int counter = 0;
        while (number != 0) {
            if (counter >= 4 && counter % 3 == 1) {
                res.insert(1, ",");
            }
            res.insert(0, number % 10);
            number /= 10;
            counter++;
        }
        if (counter >= 4 && counter % 3 == 1) {
            res.insert(1, ",");
        }
        return res.toString();
    }

    public static String secondsToTime(long seconds) {
        int minutes = (int) (seconds / 60) % 60;
        int hours = (int) (seconds / 3600);
        seconds = seconds % 60;
        if (hours == 0) {
            return String.format("%02d:%02d", minutes, seconds);
        }
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, YYYY ");
        return formatter.format(date);
    }

    public static String getResourceUriString(Context context, String res, String dir) {
        int videoResId = context.getResources().getIdentifier(res, dir, context.getPackageName());
        String uriString = "android.resource://" + context.getPackageName() + "/" + videoResId;
        return uriString;
    }



}
