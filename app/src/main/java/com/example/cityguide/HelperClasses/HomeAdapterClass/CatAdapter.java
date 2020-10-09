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

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {
    ArrayList<CatHelperClass> catLocations;

    public CatAdapter(ArrayList<CatHelperClass> catLocations) {
        this.catLocations = catLocations;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorie_card_design,parent,false);
        CatViewHolder catViewHolder = new CatViewHolder(view);
        return catViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        CatHelperClass catHelperClass = catLocations.get(position);

        holder.imageView.setImageResource(catHelperClass.getImage());
        holder.title.setText(catHelperClass.getTitle());
    }



    @Override
    public int getItemCount() {
        return catLocations.size();
    }

    public static class CatViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cat_image);
            title = itemView.findViewById(R.id.cat_title);
        }
    }
}
