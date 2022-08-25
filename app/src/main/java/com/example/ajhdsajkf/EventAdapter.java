package com.example.ajhdsajkf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {


    private Context context;
    private List<Events> eventList;



    public EventAdapter(Context context, List<Events> eventList) {
        this.context = context;
        this.eventList = eventList;
    }



    @NonNull
    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.events_layout, parent, false);



        return new MyViewHolder(view);
    }





    @Override
    public void onBindViewHolder(@NonNull final EventAdapter.MyViewHolder holder, final int position) {


        final Events events = eventList.get(position);

        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

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
        return eventList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{


        ImageView image;
        TextView title;
        TextView date;
        TextView bookmark;
        TextView joinNow;
        LinearLayout container;


        FirebaseAuth mAuth;
        FirebaseUser firebaseUser;
        DatabaseReference databaseReference;
        String uid;





        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.EventListImageID);
            title = itemView.findViewById(R.id.EventListTitleID);
            date = itemView.findViewById(R.id.EventListDateID);
            bookmark = itemView.findViewById(R.id.EventBookmarkID);
            joinNow = itemView.findViewById(R.id.EventJoinNowID);
            container = itemView.findViewById(R.id.EventLayoutContainerID);

            mAuth = FirebaseAuth.getInstance();
            firebaseUser = mAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference("users");

            final String[] userCategory = {""};


            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    uid = firebaseUser.getUid();
                    final String E_Title;
                    int pos = getAbsoluteAdapterPosition();
                    Events events = eventList.get(pos);
                    E_Title = events.getTitle();








                    final String[] i_events = {""};

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            i_events[0] = snapshot.child(uid).child("events").getValue(String.class);
                            userCategory[0] = snapshot.child(uid).child("userCategory").getValue(String.class);

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
                                public void onClick(DialogInterface dialog, final int id) {


                                    if (userCategory[0].equals("patient"))
                                    {
                                        if (i_events[0].equals(""))
                                        {
                                            databaseReference.child(uid).child("events").setValue(E_Title);

                                        }
                                        else
                                        {
                                            databaseReference.child(uid).child("events").setValue(i_events[0] + "," + E_Title);
                                        }
                                    }

                                    else
                                    {
                                        Toast.makeText(context, "Need Patient profile", Toast.LENGTH_SHORT).show();
                                    }


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

            joinNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(false);
                    builder.setMessage("Under Development");
                    builder.setPositiveButton(Html.fromHtml("<font color='#ffffff'>"+"OK" + "</font"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


                    AlertDialog alert=builder.create();
                    alert.show();

                }
            });





        }
    }


}
