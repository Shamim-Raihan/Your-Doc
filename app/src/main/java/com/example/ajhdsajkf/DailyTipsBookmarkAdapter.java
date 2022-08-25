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

public class DailyTipsBookmarkAdapter  extends RecyclerView.Adapter<DailyTipsBookmarkAdapter.MyViewHolder> {

    private Context context;
    private List<DailyTips> dailyTipsBookmarkList;

    public DailyTipsBookmarkAdapter(Context context, List<DailyTips> dailyTipsBookmarkList) {
        this.context = context;
        this.dailyTipsBookmarkList = dailyTipsBookmarkList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.daily_tips_bookmark_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DailyTips dailyTips = dailyTipsBookmarkList.get(position);
        holder.title.setText(dailyTips.getTitle());
        Picasso.get()
                .load(dailyTips.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image;
                String title;
                String des;
                image = dailyTips.getImage();
                title = dailyTips.getTitle();
                des = dailyTips.getDescription();

                Intent intent = new Intent(context, DailyTipsDetail.class);
                intent.putExtra("image", image);
                intent.putExtra("title", title);
                intent.putExtra("des", des);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyTipsBookmarkList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView viewPost;
        ImageView delete;
        LinearLayout linearLayout;

        FirebaseAuth mAuth;
        FirebaseUser firebaseUser;
        DatabaseReference databaseReference;
        String uid;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.DailyTipsBookmarkListImageID);
            title = itemView.findViewById(R.id.DailyTipsBookmarkListTitleID);
            viewPost = itemView.findViewById(R.id.DailyTipsBookmarkViewPostID);
            delete = itemView.findViewById(R.id.DailyTipsBookmarkDeleteID);
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
                    final DailyTips[] dailyTips = {dailyTipsBookmarkList.get(pos)};
                    final String selected_title = dailyTips[0].getTitle();
                    final String[] daily_tips_list = new String[1];

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            daily_tips_list[0] = snapshot.child(uid).child("dailytips").getValue(String.class);
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

                                    String[] daily_tips_list_array;
                                    daily_tips_list_array = daily_tips_list[0].split(",");
                                    String up = "";

                                    for (int i=0; i<daily_tips_list_array.length; i++)
                                    {
                                        if (daily_tips_list_array[i].equals(selected_title))
                                        {
                                            continue;
                                        }

                                        else
                                        {
                                            if(up.equals(""))
                                            {
                                                up = up+daily_tips_list_array[i];
                                            }
                                            else
                                            {
                                                up = up+","+daily_tips_list_array[i];
                                            }
                                        }
                                    }

                                    databaseReference.child(uid).child("dailytips").setValue(up);
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
