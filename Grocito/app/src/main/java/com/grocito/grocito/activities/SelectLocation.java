package com.grocito.grocito.activities;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.grocito.grocito.R;
import com.grocito.grocito.databinding.ActivitySelectLocationBinding;

public class SelectLocation extends AppCompatActivity {

    ActivitySelectLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_select_location);

        binding.searchHeader.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.searchHeader.searchTv.setText("Change Address");
        binding.searchHeader.searchTv.setTextColor(getResources().getColor(R.color.black));
        binding.currentLocatinLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectLocation.this,CurrentLocation.class));
            }
        });

        binding.addAddressLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectLocation.this,AddNewAddress.class));
            }
        });
    }
}
