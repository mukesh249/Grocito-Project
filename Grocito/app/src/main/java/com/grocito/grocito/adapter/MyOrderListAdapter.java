package com.grocito.grocito.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.MyOrderDetails;
import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.databinding.MyorderListItemBinding;
import com.grocito.grocito.databinding.NotificationItemBinding;
import com.grocito.grocito.model.MyOrderListModel;
import com.grocito.grocito.model.NotificationModel;

import java.util.ArrayList;

public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.ViewHolder> {

    ArrayList<MyOrderListModel.Datum> arrayList;
    Context context;

    public MyOrderListAdapter(Context context, ArrayList<MyOrderListModel.Datum> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        MyorderListItemBinding notificationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.myorder_list_item,viewGroup,false);
        return new ViewHolder(notificationItemBinding);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MyOrderListModel.Datum datum = arrayList.get(i);
        viewHolder.binding.orderDateTv.setText(String.format("Date/Time: %s",datum.createdAt));
        viewHolder.binding.orderIdTv.setText(String.format("Order Id: %s",datum.orderId));
        viewHolder.binding.orderItemNoTv.setText(String.format("No of Items: %s",datum.orderMetaData.size()));
        viewHolder.binding.orderStatusTv.setText(String.format("Status: %s",datum.status));
        viewHolder.binding.orderAmtTv.setText(String.format("Total Amount: Rs.%.2f",Double.parseDouble(datum.netAmount)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MyorderListItemBinding binding;

        public ViewHolder(MyorderListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(view -> context.startActivity(new Intent(context,
                    MyOrderDetails.class).putExtra("order_id",
                    arrayList.get(getLayoutPosition()).id+"")));

        }
    }
}
