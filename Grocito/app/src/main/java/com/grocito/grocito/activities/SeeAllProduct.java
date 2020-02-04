package com.grocito.grocito.activities;

import android.app.AlertDialog;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import com.grocito.grocito.adapter.SeeAllProductAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.MyApplication;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.R;
import com.grocito.grocito.databinding.ActivitySeeAllProductBinding;
import com.grocito.grocito.model.SeeAllProductsModel;
import com.grocito.grocito.utils.Utils;

public class SeeAllProduct extends AppCompatActivity implements WebCompleteTask {

    private ActivitySeeAllProductBinding binding;
    private SeeAllProductAdapter allProductAdapter;
    private List<SeeAllProductsModel.ProductList> arrayList = new ArrayList<>();
    private String cat_id;
    private static SeeAllProduct mInstance;
    AlertDialog progressDialog;
    private int countitem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_see_all_product);
        progressDialog = new SpotsDialog(this, R.style.Custom);
        mInstance = this;
        if (getIntent().getExtras() != null)
            binding.headlyaout.productCatName.setText(getIntent().getExtras().getString("name", ""));

        binding.productRecyView.setLayoutManager(new GridLayoutManager(this, 1));
        MyApplication.RecyclerView(binding.productRecyView);
        allProductAdapter = new SeeAllProductAdapter(this, arrayList);
        binding.productRecyView.setAdapter(allProductAdapter);
        allProductAdapter.notifyDataSetChanged();

        binding.headlyaout.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.headlyaout.cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SeeAllProduct.this, Cart.class));
            }
        });

        if (getIntent().getExtras() != null) {
            cat_id = getIntent().getExtras().getString("cat_id", "");
        }
        binding.headlyaout.cartItemNo.setText(SharedPrefManager.getCartItemCount(Constrants.CartItemCount)+"");
        productList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        countitem = SharedPrefManager.getCartItemCount(Constrants.CartItemCount);
        addCart(countitem);
    }

    public void productList() {
        Utils.ProgressShow(this, binding.matrialProgress, binding.productRecyView);
        HashMap objectNew = new HashMap();
        objectNew.put("cat_id", cat_id);
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        Log.i("ProductListing_res", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.ProductListing, objectNew,
                SeeAllProduct.this, RequestCode.CODE_ProductListing, 5);
    }

    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_ProductListing == taskcode) {
            Log.i("ProductListing_res", response);
            SeeAllProductsModel seeAllProductsModel = JsonDeserializer.deserializeJson(response, SeeAllProductsModel.class);
            if (seeAllProductsModel.statusCode == 1) {
                this.arrayList.addAll(seeAllProductsModel.data.productList);
                if (!seeAllProductsModel.data.productList.isEmpty()) {
                    binding.headlyaout.productCatName.setText(seeAllProductsModel.data.catdata.name);
                    allProductAdapter.notifyDataSetChanged();
                }
            }
            Utils.ProgressHide(this, binding.matrialProgress, binding.productRecyView);
        }

    }

    public static SeeAllProduct getInstance() {
        return mInstance;
    }

    public void addCart(Integer count) {
//        Utils.CartCount(this, binding.headlyaout.cartItemNo, count);
        if (binding.headlyaout.cartItemNo != null) {
            if (count > 0) {
                binding.headlyaout.cartItemNo.setVisibility(View.VISIBLE);
            } else {
                binding.headlyaout.cartItemNo.setVisibility(View.GONE);
            }
            binding.headlyaout.cartItemNo.setText(String.valueOf(count));
        }
    }

    public void proShow(Boolean value ,String message) {
        if (!progressDialog.isShowing())
            progressDialog.show();
        else{
            progressDialog.dismiss();
            Utils.Toast(this,message);
        }
    }


}
