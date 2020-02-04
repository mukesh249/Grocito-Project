package com.grocito.grocito.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.databinding.AvailablePlaceBinding;
import com.grocito.grocito.databinding.ColorItemBinding;
import com.grocito.grocito.model.AvailablePlaceModel;
import com.grocito.grocito.model.SeeAllProductsModel;
import com.grocito.grocito.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    ArrayList<AvailablePlaceModel> arrayList;
    Context context;


    public PlaceAdapter(Context context, ArrayList<AvailablePlaceModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AvailablePlaceBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.available_place, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
//        return arrayList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AvailablePlaceBinding binding;

        public ViewHolder(@NonNull AvailablePlaceBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

    }
}
