package com.example.ajhdsajkf;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    Context context;
    int[] image;
    String[] area;
    private LayoutInflater inflater;

    CustomAdapter(Context context, String[] area, int[] image)
    {
        this.context = context;
        this.area = area;
        this.image = image;
    }


    @Override
    public int getCount() {
        return area.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sample_view, parent,false);
        }


        ImageView imageView = (ImageView) convertView.findViewById(R.id.ImageViewId);
        TextView textView = (TextView) convertView.findViewById(R.id.ImageTextViewId);


        imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_down_slow));
        imageView.setImageResource(image[position]);
        textView.setText(area[position]);

        return convertView;
    }
}
