package com.example.android_client;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Response;

public class Utilities {
    public static int VIDEO_TYPE = 1;
    public static int IMAGE_TYPE = 2;


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

    public static String bitmapToBase64(Bitmap bitmap, int type) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String data = Base64.encodeToString(byteArray, Base64.DEFAULT);
        String filePostfix = type == IMAGE_TYPE ? "png" : type == VIDEO_TYPE ? "mp4" : null;
        String typeString = type == IMAGE_TYPE ? "image" : type == VIDEO_TYPE ? "video" : null;
        return "data:" + typeString + "/" + filePostfix + ";base64," + data;
    }

    public static String shortCompactNumber(long number) {
        if (number >= 1_000_000_000) {
            return String.format("%.1fB", number / 1_000_000_000.0);
        } else if (number >= 1_000_000) {
            return String.format("%.1fM", number / 1_000_000.0);
        } else if (number >= 1_000) {
            return String.format("%.1fK", number / 1_000.0);
        } else {
            return String.valueOf(number);
        }
    }

    public static String videoUriToBase64(Context context, Uri filePath) {
        byte[] byteArray = null;
        try (InputStream inputStream = context.getContentResolver().openInputStream(filePath)) {
            byteArray = new byte[inputStream.available()];
            inputStream.read(byteArray);
            String data = "data:video/mp4;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void handleError(Response response) {
        String errorMessage;
        if(response.code()==403){
            DataManager.Logout();
            ContextApplication.showToast("Registration token expired, please login again.");
        }
        try {
            errorMessage = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            errorMessage = response.message();
        }
        ContextApplication.showToast(errorMessage);
    }

}
