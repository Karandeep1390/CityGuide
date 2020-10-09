package com.example.cityguide.HelperClasses.HomeAdapterClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.R;

import java.util.ArrayList;

public class MvAdapter extends RecyclerView.Adapter<MvAdapter.MvViewHolder> {

    ArrayList<MvHelperClass> mv_Location;

    public MvAdapter(ArrayList<MvHelperClass> mv_Location) {
        this.mv_Location = mv_Location;
    }

    @NonNull
    @Override
    public MvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mv_card_design,parent,false);
        MvViewHolder mvViewHolder = new MvViewHolder(view);
        return mvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MvViewHolder holder, int position) {
        MvHelperClass mvHelperClass = mv_Location.get(position);
        holder.imageView.setImageResource(mvHelperClass.getImage());
        holder.title.setText(mvHelperClass.getTitle());
    }

    @Override
    public int getItemCount() {
        return mv_Location.size();
    }

    public static class MvViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;


        public MvViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mv_image);
            title = itemView.findViewById(R.id.mv_title);
        }
    }
}
