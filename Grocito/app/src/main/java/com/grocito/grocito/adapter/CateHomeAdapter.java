package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.SeeAllProduct;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.CategoryItemHomeBinding;
import com.grocito.grocito.model.CateHomeModel;
import com.grocito.grocito.utils.Utils;

public class CateHomeAdapter extends RecyclerView.Adapter<CateHomeAdapter.ViewHolder> {

    ArrayList<CateHomeModel> arrayList;

    Context context;
    int size;

    public CateHomeAdapter(Context context, ArrayList<CateHomeModel> arrayList,int size) {
        this.arrayList = arrayList;
        this.context = context;
        this.size = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CategoryItemHomeBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.category_item_home, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CateHomeModel bavergesModel = arrayList.get(i);
//        Utils.strikeText(viewHolder.binding.priveDisTv);
        viewHolder.binding.productName.setText(bavergesModel.getName());
        Utils.setImage(context, viewHolder.binding.productImage, WebUrls.Catgory_Image_URL + bavergesModel.getImage());
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CategoryItemHomeBinding binding;

        public ViewHolder(@NonNull CategoryItemHomeBinding itemView) {
            super(itemView.getRoot());

            this.binding = itemView;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    context.startActivity(new Intent(context, SeeAllProduct.class)
                            .putExtra("cat_id", arrayList.get(pos).getId()));
                }
            });
        }
    }
}
