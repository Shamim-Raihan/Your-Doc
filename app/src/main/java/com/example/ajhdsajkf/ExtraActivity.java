package com.example.ajhdsajkf;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class ExtraActivity extends Activity {

    Activity activity;

    public ExtraActivity(Activity myActivity) {
        this.activity = myActivity;
    }

    public void Exit()
    {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.select_dialog_box);

        TextView no = dialog.findViewById(R.id.NoID);
        TextView yes = dialog.findViewById(R.id.YesID);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();

            }
        });

        dialog.show();
    }



}
