package com.example.ajhdsajkf.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ajhdsajkf.Doctors;
import com.example.ajhdsajkf.Message.MessageActivity;
import com.example.ajhdsajkf.Model.Chat;
import com.example.ajhdsajkf.Model.Users;
import com.example.ajhdsajkf.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<Doctors> usersList;
    private boolean isChat;

    String theLastMessage;

    public UserAdapter(Context context, List<Doctors> usersList, boolean isChat) {
        this.context = context;
        this.usersList = usersList;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_fragment_single_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Doctors users =usersList.get(position);
        holder.firstName.setText(users.getFirstname());
        holder.lastName.setText(users.getLastname());

        if (users.getImageUri().equals("")){
            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        }
        else {
            Glide.with(context).load(users.getImageUri()).into(holder.profileImage);
        }

        if (isChat){
            lastMessage(users.getUid(), holder.last_Message);
        }
        else {
            holder.last_Message.setVisibility(View.GONE);
        }

        if (isChat){
            if (users.getStatus().equals("online")){
                holder.imgOnline.setVisibility(View.VISIBLE);
                holder.imgOfline.setVisibility(View.GONE);
            }
            else {
                holder.imgOnline.setVisibility(View.GONE);
                holder.imgOfline.setVisibility(View.VISIBLE);
            }
        }
        else {
            holder.imgOnline.setVisibility(View.GONE);
            holder.imgOfline.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid",users.getUid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView profileImage;
        public TextView firstName;
        public TextView lastName;
        private ImageView imgOnline;
        private ImageView imgOfline;
        public TextView last_Message;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profile_image);
            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            imgOnline = itemView.findViewById(R.id.img_online);
            imgOfline = itemView.findViewById(R.id.img_ofline);
            last_Message = itemView.findViewById(R.id.lastMessage);
        }
    }


    private void lastMessage(final String userid, final TextView last_msg){
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                    chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())){
                        theLastMessage = chat.getMessage();
                    }
                }

                switch (theLastMessage){
                    case "default":
                        last_msg.setText("No message");
                        break;
                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }
                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}



















