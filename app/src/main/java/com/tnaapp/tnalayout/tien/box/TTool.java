package com.tnaapp.tnalayout.tien.box;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.tnaapp.tnalayout.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TIENPX3010 on 10/16/2015.
 */
public class TTool {

    public static void setRunnerActivity(AppCompatActivity Activity) {
        runnerActivity = Activity;
    }

    private static AppCompatActivity runnerActivity;

    public static String calTime(String pubDate) {
        String currentDate = "2015-10-16 10:05:27";
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateFormat.format(date));
            d2 = format.parse(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = d1.getTime() - d2.getTime();
        long diffHours = diff / (60 * 60 * 1000);
        long diffMinutes = diff / (60 * 1000) % 60;
        String time = "";
        if (diffHours > 0) {
            time = String.valueOf(diffHours) + " " + runnerActivity.getResources().getString(R.string.hours);
        } else {
            time = String.valueOf(diffMinutes) + " " + runnerActivity.getResources().getString(R.string.minutes);
        }
        return time;
    }
}
