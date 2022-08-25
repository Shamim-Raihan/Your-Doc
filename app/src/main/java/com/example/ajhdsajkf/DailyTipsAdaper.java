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

public class DailyTipsAdaper extends RecyclerView.Adapter<DailyTipsAdaper.MyViewHolder>{

    private Context context;
    private List<DailyTips> dailyTipsList;

    public DailyTipsAdaper(Context context, List<DailyTips> dailyTipsList) {

        this.context = context;
        this.dailyTipsList = dailyTipsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.daily_tips_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final DailyTips dailyTips = dailyTipsList.get(position);

        holder.title.setText(dailyTips.getTitle());
        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        Picasso.get()
                .load(dailyTips.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title;
                String des;
                String image;

                title = dailyTips.getTitle();
                des = dailyTips.getDescription();
                image = dailyTips.getImage();

                Intent intent = new Intent(context, DailyTipsDetail.class);
                intent.putExtra("title", title);
                intent.putExtra("image", image);
                intent.putExtra("des", des);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyTipsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView bookmark;
        TextView viewPost;
        LinearLayout linearLayout;

        FirebaseAuth mAuth;
        FirebaseUser firebaseUser;
        DatabaseReference databaseReference;
        String uid;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.DailyTipsListImageID);
            title = itemView.findViewById(R.id.DailyTipsListTitleID);
            bookmark = itemView.findViewById(R.id.DailyTipsBookmarkID);
            viewPost = itemView.findViewById(R.id.DailyTipsViewPostID);
            linearLayout = itemView.findViewById(R.id.dailyTipsContainer);

            mAuth = FirebaseAuth.getInstance();
            firebaseUser = mAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference("users");

            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uid = firebaseUser.getUid();
                    final String D_Title;
                    int pos = getAbsoluteAdapterPosition();
                    DailyTips dailyTips = dailyTipsList.get(pos);
                    D_Title = dailyTips.getTitle();

                    final String[] i_dailyTips = {""};
                    final String[] userCategory = new String[1];

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            i_dailyTips[0] = snapshot.child(uid).child("dailytips").getValue(String.class);
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
                                        if (i_dailyTips[0].equals(""))
                                        {
                                            databaseReference.child(uid).child("dailytips").setValue(D_Title);
                                        }
                                        else
                                        {
                                            databaseReference.child(uid).child("dailytips").setValue(i_dailyTips[0] + "," + D_Title);
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(context, "Need patient profile", Toast.LENGTH_SHORT).show();
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


            viewPost.setOnClickListener(new View.OnClickListener() {
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
