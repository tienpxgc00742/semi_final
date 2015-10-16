package com.tnaapp.tnalayout.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by YoungKaka on 9/25/2015.
 */
public class T {
    public static void s(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void l(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
