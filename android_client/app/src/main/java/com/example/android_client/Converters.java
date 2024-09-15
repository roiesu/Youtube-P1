package com.example.android_client;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public String fromArray(ArrayList<String> stringArr) {
        String string = "";
        for (String s : stringArr) {
            string += (s + ",");
        }
        return string;
    }

    @TypeConverter
    public ArrayList<String> toArray(String arrString) {
        ArrayList<String> myStrings = new ArrayList<>();

        for (String s : arrString.split(",")) {
            myStrings.add(s);
        }

        return myStrings;
    }
}