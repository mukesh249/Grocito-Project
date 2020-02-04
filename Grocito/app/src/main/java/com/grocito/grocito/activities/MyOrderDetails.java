package com.grocito.grocito.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.grocito.grocito.R;
import com.grocito.grocito.adapter.MyOrderDetailsItemListAdapter;
import com.grocito.grocito.adapter.MyOrderListAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityMyOrderDetailsBinding;
import com.grocito.grocito.databinding.ActivityMyOrderListBinding;
import com.grocito.grocito.model.MyOrderDetailsModel;
import com.grocito.grocito.model.MyOrderListModel;

import java.util.ArrayList;
import java.util.HashMap;

public class MyOrderDetails extends AppCompatActivity implements WebCompleteTask {

    private ActivityMyOrderDetailsBinding binding;
    private ArrayList<MyOrderDetailsModel.MetaDatum> arrayList = new ArrayList<>();
    private MyOrderDetailsItemListAdapter adapter;
    private String order_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_order_details);

        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        binding.headlyaout.productCatName.setText(getResources().getString(R.string.my_order));
        binding.headlyaout.backBtn.setOnClickListener(view -> finish());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyOrderDetailsItemListAdapter(this,arrayList);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (getIntent().getExtras()!=null){
            order_id = getIntent().getExtras().getString("order_id","");
            myOrderList();
        }

    }


    public void myOrderList(){
//        binding.matrialProgress.setVisibility(View.VISIBLE);
//        binding.emptyLL.setVisibility(View.GONE);
//        binding.recyclerView.setVisibility(View.GONE);
        HashMap objectNew = new HashMap();
        objectNew.put("order_id", order_id);
        new WebTask(this, WebUrls.BASE_URL+WebUrls.UserOrderDetails,objectNew,
                MyOrderDetails.this, RequestCode.CODE_UserOrderDetails,5);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_UserOrderDetails == taskcode){
            Log.i("UserOrderDetails_res",response);
            MyOrderDetailsModel orderListModel = JsonDeserializer.deserializeJson(response, MyOrderDetailsModel.class);
            if (orderListModel.statusCode==1) {
//                binding.matrialProgress.setVisibility(View.GONE);
//                binding.emptyLL.setVisibility(View.GONE);
//                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.orderidTv.setText(String.format("Order id : %s",orderListModel.data.orderId));
                binding.dateTimeTv.setText(String.format("Order id : %s",orderListModel.data.createdAt));
                binding.dateTimeTv.setText(String.format("Order id : %s",orderListModel.data.createdAt));
                binding.deliveryDateTv.setText(String.format("Order id : %s",orderListModel.data.deliveryDate));
                binding.deliveryTimeTv.setText(String.format("Order id : %s",orderListModel.data.deliveryTime));
                binding.deliveryTypTv.setText(String.format("Order id : %s",orderListModel.data.deliveryType));
                binding.orderStatusTv.setText(String.format("Order id : %s",orderListModel.data.status));
                binding.paymentOptionTv.setText(String.format("Payment Option : %s",orderListModel.data.paymentMode));
                binding.netAmtTv.setText(String.format("Rs.%.2f",Double.parseDouble(orderListModel.data.netAmount)));
//                binding.sgstAmtTv.setText(String.format("Rs.%.2f",Double.parseDouble(orderListModel.data.sgstAmount)));
//                binding.cgstAmtTv.setText(String.format("Rs.%.2f",Double.parseDouble(orderListModel.data.gstAmount)));
//                binding.subAmtTv.setText(String.format("Rs.%.2f",Double.parseDouble(orderListModel.data.totalAmount)));
                binding.deliveryChargeTv.setText(String.format("Rs.%.2f",Double.parseDouble(orderListModel.data.shippingCharge)));
                binding.totalTv.setText(String.format("Rs.%.2f",Double.parseDouble(orderListModel.data.totalAmount)));

                binding.nameTv.setText(String.format("%s",orderListModel.data.address.name));
                binding.typeTv.setText(String.format("%s",orderListModel.data.address.type));
                binding.addressTv.setText(String.format("Address : %s %s %s %s %s(%s)",
                        orderListModel.data.address.house,
                        orderListModel.data.address.street,
                        orderListModel.data.address.landmark,
                        orderListModel.data.address.city,
                        orderListModel.data.address.state,
                        orderListModel.data.address.pincode));

                arrayList.addAll(orderListModel.metaData);
                adapter.notifyDataSetChanged();

            }else {
//                binding.matrialProgress.setVisibility(View.GONE);
//                binding.emptyLL.setVisibility(View.VISIBLE);
//                binding.recyclerView.setVisibility(View.GONE);
            }

        }

    }
}
