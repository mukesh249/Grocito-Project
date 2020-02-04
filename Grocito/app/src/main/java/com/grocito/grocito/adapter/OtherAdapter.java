package com.grocito.grocito.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.OtherItemBinding;
import com.grocito.grocito.R;
import com.grocito.grocito.model.IsSpecialModel;
import com.grocito.grocito.utils.Utils;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.ViewHolder> {

    ArrayList<IsSpecialModel> arrayList = new ArrayList<>();

    Context context;

    public OtherAdapter(Context context, ArrayList<IsSpecialModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        OtherItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.other_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        IsSpecialModel isSpecialModel = arrayList.get(i);
        Utils.setImage(context,viewHolder.binding.productImage, WebUrls.Catgory_Image_URL+isSpecialModel.getImage());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OtherItemBinding binding;
        public ViewHolder(@NonNull OtherItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
