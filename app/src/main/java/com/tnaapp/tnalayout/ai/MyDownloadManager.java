package com.tnaapp.tnalayout.ai;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by YoungKaka on 10/10/2015.
 */
public class MyDownloadManager {
    public static final String DEFAULT_URL = "http://test-data-1094.appspot.com/data/videos";

    public static void main(String[] args) {
//        MyDownloadManager mm = new MyDownloadManager();
//        Log.d("ss",mm.getDataFromUrl(DEFAULT_URL));


    }

    public String getDataFromUrl(String url) {
        String result = "";
        try {
            // Create a URL for the desired page
            URL ur = new URL(url);

            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(ur.openStream()));
            String str;
            StringBuilder sb = new StringBuilder(100);
            while ((str = in.readLine()) != null) {
                sb.append(str);
                // str is one line of text; readLine() strips the newline character(s)
            }
            in.close();
            result = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    // --------------- AsyncTask --------------------

    public class MyAsyncTask extends AsyncTask<String, String, String> {
        private String getDataFromUrl(String url) {
            String result = "";
            try {
                // Create a URL for the desired page
                URL ur = new URL(url);

                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(ur.openStream()));
                String str;
                StringBuilder sb = new StringBuilder(100);
                while ((str = in.readLine()) != null) {
                    sb.append(str);
                    // str is one line of text; readLine() strips the newline character(s)
                }
                in.close();
                result = sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;

        }


        @Override
        protected String doInBackground(String[] params) {
            String result = getDataFromUrl(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
         //   mDisplay.setText(result);
        }
    }
}
