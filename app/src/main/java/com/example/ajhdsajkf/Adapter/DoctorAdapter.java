package com.example.ajhdsajkf.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajhdsajkf.DoctorProfileForPatient;
import com.example.ajhdsajkf.Doctors;
import com.example.ajhdsajkf.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder>{

    private Context context;
    private List<Doctors> doctorList;

    public DoctorAdapter(Context context, List<Doctors> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_control_doctor_list_layout, parent, false);
        return new DoctorAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Doctors doctors = doctorList.get(position);
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holder.name.setText(doctors.getFirstname());
        holder.specialist.setText(doctors.getSpecialist());
        holder.area.setText(doctors.getArea());

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Picasso.get()
                .load(doctors.getImageUri())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.image);

        holder.removeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.delete_dialog_box);

                TextView yes = dialog.findViewById(R.id.YesID);
                TextView no = dialog.findViewById(R.id.NoID);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        databaseReference.removeValue();
                        dialog.dismiss();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView specialist;
        TextView area;
        TextView removeAccount;
        ConstraintLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ImageID);
            name = itemView.findViewById(R.id.NameID);
            specialist = itemView.findViewById(R.id.SpecialistID);
            area = itemView.findViewById(R.id.AreaID);
            removeAccount = itemView.findViewById(R.id.removeAccountId);
            container = itemView.findViewById(R.id.RecyclerViewContainerID);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String image1;
                    String name;
                    String specialist;
                    String qualification;
                    String chamber;
                    String visitingHour;
                    String email;
                    String phone;
                    String fee;
                    String uid;

                    int pos = getAbsoluteAdapterPosition();
                    Doctors doctors = doctorList.get(pos);
                    image1 = doctors.getImageUri();
                    name = doctors.getFirstname();
                    specialist = doctors.getSpecialist();
                    qualification = doctors.getQualification();
                    chamber = doctors.getChamberAddress();
                    visitingHour = doctors.getVisitingHour();
                    email = doctors.getEmail();
                    phone = doctors.getPhone();
                    fee = doctors.getFee();
                    uid = doctors.getUid();

                    Intent intent = new Intent(context, DoctorProfileForPatient.class);
                    intent.putExtra("image", image1);
                    intent.putExtra("name",name);
                    intent.putExtra("specialist", specialist);
                    intent.putExtra("qualification", qualification);
                    intent.putExtra("chamber", chamber);
                    intent.putExtra("visitingHour", visitingHour);
                    intent.putExtra("email", email);
                    intent.putExtra("phone", phone);
                    intent.putExtra("fee", fee);
                    intent.putExtra("userCategory", "admin");
                    intent.putExtra("uid", uid);
                    intent.putExtra("bookStatus", "null");
                    context.startActivity(intent);
                }
            });

        }
    }


}
