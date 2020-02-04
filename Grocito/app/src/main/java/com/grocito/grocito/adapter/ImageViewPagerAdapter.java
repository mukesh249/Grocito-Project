package com.grocito.grocito.adapter;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.model.ImageModel;
import com.grocito.grocito.R;
import com.grocito.grocito.databinding.ImageViewItemBinding;
import com.grocito.grocito.utils.Utils;

public class ImageViewPagerAdapter extends PagerAdapter {

    private Context context;
    ArrayList<ImageModel> imageArray;

    public ImageViewPagerAdapter(Context context, ArrayList<ImageModel> imageArray) {
        this.context = context;
        this.imageArray = imageArray;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.image_view_item, container, false);

        if (ProductDetail.product_detail){
            binding.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }else {
            binding.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        Utils.setImage(context, binding.imageView, WebUrls.BASE_URL + imageArray.get(position).getImage());
        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageArray.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
