package com.example.ajhdsajkf.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ajhdsajkf.Doctors;
import com.example.ajhdsajkf.MainActivity;
import com.example.ajhdsajkf.PatientProfile;
import com.example.ajhdsajkf.Patients;
import com.example.ajhdsajkf.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder>{

    private Context context;
    private List<Patients> patientList;

    public PatientAdapter(Context context, List<Patients> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_control_patient_list_layout, parent, false);
        return new PatientAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Patients patients = patientList.get(position);
        holder.name.setText(patients.getFullName());
        Picasso.get()
                .load(patients.getImageUri())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView removeAccount;
        ConstraintLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.name);
            removeAccount = itemView.findViewById(R.id.removeAccountId);
            container = itemView.findViewById(R.id.RecyclerViewContainerID);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAbsoluteAdapterPosition();
                    Patients patients = patientList.get(pos);
                    Intent intent = new Intent(context, PatientProfile.class);
                    intent.putExtra("image", patients.getImageUri());
                    intent.putExtra("name", patients.getFirstname());
                    intent.putExtra("last_name", patients.getLastname());
                    intent.putExtra("location", patients.getLocation());
                    intent.putExtra("email", patients.getEmail());
                    intent.putExtra("phone", patients.getPhone());
                    intent.putExtra("age", patients.getAge());
                    context.startActivity(intent);
                }
            });
        }
    }
}
