package com.grocito.grocito.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.MyorderDetailItemBinding;
import com.grocito.grocito.databinding.MyorderListItemBinding;
import com.grocito.grocito.model.MyOrderDetailsModel;
import com.grocito.grocito.model.MyOrderListModel;
import com.grocito.grocito.utils.Utils;

import java.util.ArrayList;

public class MyOrderDetailsItemListAdapter extends RecyclerView.Adapter<MyOrderDetailsItemListAdapter.ViewHolder> {

    ArrayList<MyOrderDetailsModel.MetaDatum> arrayList;
    Context context;

    public MyOrderDetailsItemListAdapter(Context context, ArrayList<MyOrderDetailsModel.MetaDatum> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        MyorderDetailItemBinding notificationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.myorder_detail_item,viewGroup,false);
        return new ViewHolder(notificationItemBinding);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MyOrderDetailsModel.MetaDatum metaDatum = arrayList.get(i);
        Utils.setImage(context,viewHolder.binding.itemImage, WebUrls.IMAGE_PRODUCT+metaDatum.productImage);
        viewHolder.binding.itemNameTv.setText(String.format("%s",metaDatum.productName));
        viewHolder.binding.itemPriceTv.setText(String.format("Item Price : Rs.%.2f", Double.parseDouble(metaDatum.price)));
        viewHolder.binding.itemQtyTv.setText(String.format("No of Items: %s",metaDatum.qty+""));
        viewHolder.binding.subAmtTv.setText(String.format("Sub Amount: Rs.%.2f",Double.parseDouble(metaDatum.netAmount)));
        viewHolder.binding.itemdisTv.setText(String.format("Item Discount : Rs.%.2f",Double.parseDouble(metaDatum.offerAmount)));
//        viewHolder.binding.deliveryTypTv.setText(String.format("%s",metaDatum.));
        viewHolder.binding.deliveryDateTv.setText(String.format("%s",metaDatum.expectedDeliveryDate));
//        viewHolder.binding.deliveryTimeTv.setText(String.format("%s",metaDatum.));
        viewHolder.binding.orderStatusTv.setText(String.format("%s",metaDatum.status));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MyorderDetailItemBinding binding;

        public ViewHolder(MyorderDetailItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    context.startActivity(new Intent(context, ProductDetail.class));
//                }
//            });

        }
    }
}
