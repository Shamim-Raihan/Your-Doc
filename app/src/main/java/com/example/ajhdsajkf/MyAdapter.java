package com.example.ajhdsajkf;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.annotation.IncompleteAnnotationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Doctors> doctorsList;
    private String userCategory;
    ArrayList<Doctors> doctorListAll;



    public MyAdapter(Context context, List<Doctors> doctorsList, String userCategory) {
        this.context = context;
        this.doctorsList = doctorsList;
        this.userCategory = userCategory;
        this.doctorListAll = new ArrayList<>(doctorsList);
    }




    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_sample_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Doctors doctors = doctorsList.get(position);


        //holder.image.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_tranaction_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));


        holder.name.setText(doctors.getFirstname());
        holder.specialist.setText(doctors.getSpecialist());
        holder.area.setText(doctors.getArea());

        Picasso.get()
                .load(doctors.getImageUri())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<Doctors> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filteredList.addAll(doctorListAll);
            }
            else {
                for (Doctors list : doctorListAll){
                    if (list.getFirstname().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(list);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            doctorsList.clear();
            doctorsList.addAll((Collection<? extends Doctors>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView specialist;
        TextView area;
        TextView bookNow;
        TextView viewProfile;
        ConstraintLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.ImageID);
            name = itemView.findViewById(R.id.NameID);
            specialist = itemView.findViewById(R.id.SpecialistID);
            area = itemView.findViewById(R.id.AreaID);
            bookNow = itemView.findViewById(R.id.BookNowID);
            viewProfile = itemView.findViewById(R.id.ViewProfileID);
            container = itemView.findViewById(R.id.RecyclerViewContainerID);

            viewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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

                    Doctors doctors = doctorsList.get(pos);
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
                    intent.putExtra("userCategory", userCategory);
                    intent.putExtra("uid", uid);
                    intent.putExtra("bookStatus", "null");
                    context.startActivity(intent);
                }
            });

            bookNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseAuth mAuth;
                    FirebaseUser firebaseUser;
                    String uid_1;
                    int pos = getAbsoluteAdapterPosition();
                    Doctors doctors = doctorsList.get(pos);
                    uid_1 = doctors.getUid();
                    mAuth = FirebaseAuth.getInstance();
                    firebaseUser = mAuth.getCurrentUser();
                    if (firebaseUser == null)
                    {
                        Toast.makeText(context, "Please Log In or Sign Up", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        Intent intent = new Intent(context, DayList.class);

                        intent.putExtra("uid", uid_1);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
