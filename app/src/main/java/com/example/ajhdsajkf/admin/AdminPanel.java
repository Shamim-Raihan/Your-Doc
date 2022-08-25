package com.example.ajhdsajkf.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ajhdsajkf.Adapter.AdminActivityAdapter;
import com.example.ajhdsajkf.AdminPanelActivity;
import com.example.ajhdsajkf.CustomAdapter;
import com.example.ajhdsajkf.DoctorCatagoryActivity;
import com.example.ajhdsajkf.NearbyDoctorListActivity;
import com.example.ajhdsajkf.R;

public class AdminPanel extends AppCompatActivity {

    int[] adminImage = {R.drawable.admin1, R.drawable.admin2, R.drawable.admin3, R.drawable.admin4,
            R.drawable.admin5, R.drawable.admin6};

    private GridView adminActivity;
    private int selected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel2);

        adminActivity = findViewById(R.id.adminActivityId);

        AdminActivityAdapter adapter = new AdminActivityAdapter(this, adminImage);
        adminActivity.setAdapter(adapter);

        adminActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Intent intent = new Intent(AdminPanel.this, UserControl.class);
                    startActivity(intent);
                }

                if (position == 2){
                    Intent intent = new Intent(AdminPanel.this, AdminPanelActivity.class);
                    startActivity(intent);
                }
//                else {
//                    Toast.makeText(AdminPanel.this, "not implemented", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}