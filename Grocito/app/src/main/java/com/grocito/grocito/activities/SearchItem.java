package com.grocito.grocito.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.grocito.grocito.R;
import com.grocito.grocito.databinding.ActivitySearchItemBinding;

public class SearchItem extends AppCompatActivity {

    ActivitySearchItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_search_item);

//        binding.headlyaout.backBtn.setColorFilter(getResources().getColor(R.color.white));
//        binding.headlyaout.productCatName.setTextColor(getResources().getColor(R.color.white));
        binding.headlyaout.productCatName.setText(getResources().getString(R.string.app_name));
        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        binding.headlyaout.headViewLine.setVisibility(View.GONE);

    }
}
