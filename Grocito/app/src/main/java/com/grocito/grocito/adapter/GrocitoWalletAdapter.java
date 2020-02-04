package com.grocito.grocito.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.databinding.GrocitoWalletItemBinding;
import com.grocito.grocito.model.GrocitoWalletModel;

import java.util.List;

public class GrocitoWalletAdapter extends RecyclerView.Adapter<GrocitoWalletAdapter.ViewHolder> {

    List<GrocitoWalletModel.GrocitoWallet> arrayList;

    Context context;

    public GrocitoWalletAdapter(Context context, List<GrocitoWalletModel.GrocitoWallet> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        GrocitoWalletItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.grocito_wallet_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GrocitoWalletModel.GrocitoWallet homeCatProductModel = arrayList.get(i);
        viewHolder.binding.dateTimeTv.setText(homeCatProductModel.createdAt);
        viewHolder.binding.productNameTv.setText(homeCatProductModel.paymentType);
        viewHolder.binding.amountTv.setText(String.format("Amount : Rs.%s",homeCatProductModel.amount));
        viewHolder.binding.statusTv.setText(String.format("Status : %s",homeCatProductModel.status));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        GrocitoWalletItemBinding binding;
        public ViewHolder(@NonNull GrocitoWalletItemBinding itemView) {
            super(itemView.getRoot());

            this.binding = itemView;


            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    context.startActivity(new Intent(context, ProductDetail.class)
//                            .putExtra("product_slug",arrayList.get(getAdapterPosition()).slug));
                }
            });
        }
    }
}
