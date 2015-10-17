package com.tnaapp.tnalayout.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;
import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.tien.box.TTool;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YoungKaka on 9/25/2015.
 */
public class NotificationsReceiver extends ParsePushBroadcastReceiver {
    private int mId = 1;

//    @Override
//    protected void onPushOpen(Context context, Intent intent) {
//        Intent myIntent = new Intent(context, ReceiverActivity.class);
//        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//
//            JSONObject json = JSONConverter.getJsonFromParse(intent);
//        T.s(context, json.toString());
//        String text = "";
//        try {
//            text = json.getString("alert");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//            myIntent.putExtra("hello", text);
//
//        context.startActivity(myIntent);
//        super.onPushOpen(context, intent);
//    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //  super.onReceive(context,intent);
        String title = "";
        String league = "";
        String des = "";
        String type = "";
        String id = "";
        JSONObject json = JSONConverter.getJsonFromParse(intent);
        if (json != null) {
            try {
                Log.d("NotificationJSON", json.toString());
                title = json.getString("alert");
                league = json.getString("league");
                des = json.getString("des");
                type = json.getString("type");
                id = json.getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        createCustomNotification(context, title, league, des,type,id);
    }


//    @Override
//    protected Bitmap getLargeIcon(Context context, Intent intent) {
//        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.premierleague);
//        return icon;
//
//    }

    protected void createCustomNotification(Context context, String title, String league, String des,String type,String id) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(title)
                        .setContentText(des)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(soundUri)
                        .setTicker(title);
        if("news".equals(type)) {
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.news));
        }else  if("videos".equals(type)){
            mBuilder.setLargeIcon(getLargIconByLeague(context,league));
        }

        Intent backIntent = new Intent(context, MainActivity.class);
        backIntent.putExtra("id", id);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

      //
        Intent intent = new Intent(context, ReceiverActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        final PendingIntent pendingIntent = PendingIntent.getActivities(context, 1,
                new Intent[]{backIntent, intent}, PendingIntent.FLAG_ONE_SHOT);

//        Intent resultIntent = new Intent(context, ReceiverActivity.class);
//        resultIntent.putExtra("type", type);
//        resultIntent.putExtra("id", id);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mId, mBuilder.build());
    }

    protected Bitmap getLargIconByLeague(Context context, String league) {
        int id = R.drawable.premierleague;
        if ("laliga".equals(league)) {
            id = R.drawable.laliga;
        } else if ("ligone".equals(league)) {
            id = R.drawable.ligueone;
        } else if ("bundesliga".equals(league)) {
            id = R.drawable.bundesliga;
        } else if ("seria".equals(league)) {
            id = R.drawable.seriea;
        }
        return BitmapFactory.decodeResource(context.getResources(), id);
    }
}
