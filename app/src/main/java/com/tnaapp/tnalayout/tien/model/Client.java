package com.tnaapp.tnalayout.tien.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by XTien on 10/11/2015.
 */
public class Client extends AsyncTask<String, String, String> {
    public static String result;
    public Listener mListener;
    public static interface Listener {
        void doneExecute();
        void preExcute();
    }
    public void setListener(Listener listener) {
        mListener = listener;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.preExcute(); //hiện progress bar
    }
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        String result = "";
        try {
            // Create a URL for the desired page
            URL url = new URL(f_url[0]);
            // Read all the text returned by the server

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            if(f_url[1].equals("xml"))
            {
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/xml");
            }
            InputStream inputStream = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            StringBuilder sb = new StringBuilder(100);
            while ((str = in.readLine()) != null) {
                sb.append(str);
                // str is one line of text; readLine() strips the newline character(s)
            }
            in.close();
            result = sb.toString();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(String... progress) {
        // Set progress percentage
    }

    @Override
    protected void onPostExecute(String response) {
        // Dismiss the dialog after the Music file was downloaded
        result = response;
        Log.wtf("INFO", response);
        if (mListener != null) {
            mListener.doneExecute();
        }
    }
}
