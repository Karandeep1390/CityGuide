package com.example.cityguide.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.cityguide.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context){
        this.context = context;
    }
    int Images[]={
            R.drawable.search_place,
            R.drawable.make_a_call,
            R.drawable.add_missing_place,
            R.drawable.sit_back_and_relax
    };
    int Headings[]={
            R.string.firstSlideTitle,
            R.string.secondSlideTitle,
            R.string.thirdSlideTitle,
            R.string.fourthSlideTitle
    };
    int Description[]= {
            R.string.first_slide_description,
            R.string.second_slide_description,
            R.string.third_slide_description,
            R.string.fourth_slide_description,
    };

    @Override
    public int getCount() {
        return Headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout,container,false);

        ImageView imageView = view.findViewById(R.id.imageSliderView);
        TextView heading = view.findViewById(R.id.slide_title);
        TextView des = view.findViewById(R.id.slide_desc);

        imageView.setImageResource(Images[position]);
        heading.setText(Headings[position]);
        des.setText(Description[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
