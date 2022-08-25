package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AdminPanelActivity extends AppCompatActivity {

    private CardView add_events;
    private  CardView add_daily_tips;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);


        add_events = findViewById(R.id.Add_events_ID);
        add_daily_tips = findViewById(R.id.add_daily_tips);

        add_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminPanelActivity.this, AdminInputActivity.class);
                intent.putExtra("id", "Event");
                startActivity(intent);

            }
        });

        add_daily_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminPanelActivity.this, AdminInputActivity.class);
                intent.putExtra("id", "DailyTips");
                startActivity(intent);

            }
        });

    }
}
