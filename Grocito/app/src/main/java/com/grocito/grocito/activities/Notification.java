package com.grocito.grocito.activities;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;

import com.grocito.grocito.adapter.NotificationAdapter;
import com.grocito.grocito.model.NotificationModel;
import com.grocito.grocito.R;
import com.grocito.grocito.databinding.ActivityNotificationBinding;

public class Notification extends AppCompatActivity {

    private ActivityNotificationBinding notificationBinding;
    private NotificationAdapter notificationAdapter;
    private ArrayList<NotificationModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationBinding = DataBindingUtil.setContentView(this,R.layout.activity_notification);
        notificationBinding.headlyaout.searchIcon.setVisibility(View.GONE);
        notificationBinding.headlyaout.cartRL.setVisibility(View.GONE);
        notificationBinding.headlyaout.productCatName.setText(getResources().getString(R.string.notification));

        notificationBinding.headlyaout.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        notificationBinding.headlyaout.cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Notification.this,Cart.class));
            }
        });

        notificationBinding.notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationAdapter = new NotificationAdapter(Notification.this,list);
        notificationBinding.notificationRecyclerView.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();


    }
}
