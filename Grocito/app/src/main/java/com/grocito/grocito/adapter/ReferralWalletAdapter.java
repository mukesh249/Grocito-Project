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
import com.grocito.grocito.databinding.ReferralWalletItemBinding;
import com.grocito.grocito.model.GrocitoWalletModel;

import java.util.List;

public class ReferralWalletAdapter extends RecyclerView.Adapter<ReferralWalletAdapter.ViewHolder> {

    List<GrocitoWalletModel.ReferWallet> arrayList;

    Context context;

    public ReferralWalletAdapter(Context context, List<GrocitoWalletModel.ReferWallet> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ReferralWalletItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.referral_wallet_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GrocitoWalletModel.ReferWallet homeCatProductModel = arrayList.get(i);
        viewHolder.binding.dateTimeTv.setText(homeCatProductModel.createdAt);
        viewHolder.binding.productNameTv.setText(homeCatProductModel.paymentType);
        viewHolder.binding.amountTv.setText(String.format("Amount : Rs.%s",homeCatProductModel.amount));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ReferralWalletItemBinding binding;
        public ViewHolder(@NonNull ReferralWalletItemBinding itemView) {
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
