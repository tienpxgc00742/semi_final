package com.tnaapp.tnalayout.tien.code;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;

/**
 * Created by TIENPX3010 on 10/15/2015.
 */
public class TActivity {
    public static void configMainActivity(final MainActivity main)
    {
        Dialog dialog =new Dialog(main,android.R.style.Theme_Black_NoTitleBar);
        dialog.setContentView(R.layout.tien_on_start);
        dialog.show();
        dialog.findViewById(R.id.get_started).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(main.getBaseContext(), "msg msg", Toast.LENGTH_LONG).show();
                Intent i = new Intent(main, SelectActivity.class);
                main.startActivity(i);
            }
        });
    }
    public static void configSelectActivity(final MainActivity main)
    {
        main.findViewById(R.id.get_started).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(main.getBaseContext(), "msg msg", Toast.LENGTH_LONG).show();
                Intent i = new Intent(main, SelectActivity.class);
                main.startActivity(i);
            }
        });
    }
}
