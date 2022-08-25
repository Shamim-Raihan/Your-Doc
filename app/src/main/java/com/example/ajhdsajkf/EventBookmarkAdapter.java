package com.example.ajhdsajkf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventBookmarkAdapter  extends RecyclerView.Adapter<EventBookmarkAdapter.MyViewHolder> {

    private Context context;
    private List<Events> eventsBookmarkList;

    public EventBookmarkAdapter(Context context, List<Events> eventsBookmarkList) {
        this.context = context;
        this.eventsBookmarkList = eventsBookmarkList;
    }

    @NonNull
    @Override
    public EventBookmarkAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.events_bookmark_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventBookmarkAdapter.MyViewHolder holder, int position) {
        final Events events = eventsBookmarkList.get(position);
        holder.title.setText(events.getTitle());
        holder.date.setText(events.getDate());

        Picasso.get()
                .load(events.getImage_1())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title;
                String image1;
                String descrioption;
                String date;
                String loacation;
                String time;
                String doctor;

                title = events.getTitle();
                image1 = events.getImage_2();
                descrioption = events.getDescription();
                date = events.getDate();
                loacation = events.getLocation();
                time = events.getTime();
                doctor = events.getDoctor();

                Intent intent = new Intent(context, EventDetail.class);

                intent.putExtra("title", title);
                intent.putExtra("image", image1);
                intent.putExtra("description", descrioption);
                intent.putExtra("date", date);
                intent.putExtra("loacation", loacation);
                intent.putExtra("time", time);
                intent.putExtra("doctor", doctor);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsBookmarkList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView date;
        TextView joinNow;
        ImageView delete;
        LinearLayout linearLayout;

        FirebaseAuth mAuth;
        FirebaseUser firebaseUser;
        DatabaseReference databaseReference;
        String uid;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.EventBookmarkImageID);
            title = itemView.findViewById(R.id.EventBookmarkTitleID);
            date = itemView.findViewById(R.id.EventBookmarkDateID);
            joinNow = itemView.findViewById(R.id.EventBookmarkJoinNowID);
            delete = itemView.findViewById(R.id.EventBookmarkDeleteID);
            linearLayout = itemView.findViewById(R.id.top_layout);

            linearLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

            mAuth = FirebaseAuth.getInstance();
            firebaseUser = mAuth.getCurrentUser();
            uid = firebaseUser.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference("users");

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAbsoluteAdapterPosition();
                    final Events[] events = {eventsBookmarkList.get(pos)};
                    final String selected_title = events[0].getTitle();
                    final String[] event_list = new String[1];

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            event_list[0] = snapshot.child(uid).child("events").getValue(String.class);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("Are You Sure ?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    String[] event_list_array;

                                    event_list_array = event_list[0].split(",");

                                    String up = "";



                                    for (int i=0; i<event_list_array.length; i++)
                                    {


                                        if (event_list_array[i].equals(selected_title))
                                        {
                                            continue;
                                        }

                                        else
                                        {
                                            if(up.equals(""))
                                            {
                                                up = up+event_list_array[i];
                                            }
                                            else
                                            {
                                                up = up+","+event_list_array[i];
                                            }
                                        }
                                    }

                                    databaseReference.child(uid).child("events").setValue(up);
                                    Intent intent = new Intent(context, BookmarkActivity.class);
                                    intent.putExtra("check", "1");
                                    intent.putExtra("dailytipscheck", "1");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    context.startActivity(intent);
                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
            });



        }
    }
}
