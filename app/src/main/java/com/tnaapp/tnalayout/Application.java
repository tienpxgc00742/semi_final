package com.tnaapp.tnalayout;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by YoungKaka on 10/2/2015.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "pdYK0WsHAeH1LZjkYJHvzjizTFPexV15rmNkDaqM", "gsthtinWDTYgL9jNOxJqsjyxws5LhBNPBgxaAeGO");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
