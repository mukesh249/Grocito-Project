package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.SeeAllProduct;
import com.grocito.grocito.common.MyApplication;
import com.grocito.grocito.databinding.HomeItemBinding;
import com.grocito.grocito.model.HomeModel;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    ArrayList<HomeModel> arrayList;

    Context context;

    public HomeAdapter(Context context, ArrayList<HomeModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        HomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.home_item, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HomeModel homeModel = arrayList.get(i);
        viewHolder.binding.catName.setText(homeModel.getCat_name());

        viewHolder.binding.catRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        MyApplication.RecyclerView(viewHolder.binding.catRecyclerView);
        HomeCatProductAdapter homeCatProductAdapter = new HomeCatProductAdapter(context, homeModel.getBavArrayList());
        viewHolder.binding.catRecyclerView.setAdapter(homeCatProductAdapter);
        homeCatProductAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        HomeItemBinding binding;

        public ViewHolder(@NonNull HomeItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

            binding.seeAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    context.startActivity(new Intent(context, SeeAllProduct.class)
                            .putExtra("cat_id", arrayList.get(pos).getId())
                    );

                }
            });
        }
    }
}
