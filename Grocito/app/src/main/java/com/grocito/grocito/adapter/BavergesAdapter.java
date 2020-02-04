package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.model.BavergesModel;
import com.grocito.grocito.R;
import com.grocito.grocito.utils.Utils;
import com.grocito.grocito.databinding.BavergesItemBinding;

public class BavergesAdapter extends RecyclerView.Adapter<BavergesAdapter.ViewHolder> {

    ArrayList<BavergesModel> arrayList = new ArrayList<>();
    BavergesModel bavergesModel;
    Context context;

    public BavergesAdapter(Context context,ArrayList<BavergesModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        BavergesItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.baverges_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Utils.strikeText(viewHolder.binding.priveDisTv);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        BavergesItemBinding binding;
        public ViewHolder(@NonNull BavergesItemBinding itemView) {
            super(itemView.getRoot());

            this.binding = itemView;


            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, ProductDetail.class));
                }
            });
        }
    }
}
