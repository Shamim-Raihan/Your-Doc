package com.example.ajhdsajkf.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajhdsajkf.R;

public class AdminActivityAdapter extends BaseAdapter {

    Context context;
    int[] image;
    private LayoutInflater inflater;

    public AdminActivityAdapter(Context context, int[] image) {
        this.context = context;
        this.image = image;
    }


    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.admin_panel_items, viewGroup,false);
        }


        ImageView imageView = (ImageView) view.findViewById(R.id.ImageViewId);
        TextView textView = (TextView) view.findViewById(R.id.ImageTextViewId);


        imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_down_slow));
        imageView.setImageResource(image[position]);

        return view;
    }
}
