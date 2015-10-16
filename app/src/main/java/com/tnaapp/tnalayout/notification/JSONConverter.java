package com.tnaapp.tnalayout.notification;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YoungKaka on 9/26/2015.
 */
public class JSONConverter {

    public static JSONObject getJsonByString(String jsonString){
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONObject getJsonFromParse(Intent intent) {
        Bundle bundle = intent.getExtras();
        String jsonData = bundle.getString("com.parse.Data");
        JSONObject json = null;

        try {
            json = new JSONObject(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
