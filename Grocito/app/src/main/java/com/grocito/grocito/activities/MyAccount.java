package com.grocito.grocito.activities;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.grocito.grocito.R;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.utils.Utils;
import com.grocito.grocito.databinding.ActivityMyAccountBinding;

public class MyAccount extends AppCompatActivity {

    private ActivityMyAccountBinding myAccountBinding;
    private android.app.AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myAccountBinding = DataBindingUtil.setContentView(this,R.layout.activity_my_account);

        myAccountBinding.headlyaout.cartRL.setVisibility(View.GONE);
        myAccountBinding.headlyaout.searchIcon.setVisibility(View.GONE);

        myAccountBinding.headlyaout.productCatName.setText(getResources().getString(R.string.my_account));

        myAccountBinding.headlyaout.backBtn.setOnClickListener(view -> finish());

        myAccountBinding.editIcon.setOnClickListener(view -> startActivity(new Intent(MyAccount.this, EditProfile.class)));

        myAccountBinding.changeTV.setOnClickListener(view -> startActivity(new Intent(MyAccount.this, AddNewAddress.class)));

        myAccountBinding.logoutLL.setOnClickListener(view -> dialog =
                Utils.retryAlertDialog(MyAccount.this,
                        getResources().getString(R.string.app_name),
                        getResources().getString(R.string.Are_you_sure_to_logout),
                        getResources().getString(R.string.Cancel),
                        getResources().getString(R.string.Yes), v -> {
            //                        logout(true);
                            Utils.logout(MyAccount.this);
                            dialog.dismiss();
                        }));

        myAccountBinding.userEmailTv.setText(SharedPrefManager.getUserEmail(Constrants.UserEmail));
        myAccountBinding.mobileTv.setText(SharedPrefManager.getUserMobile(Constrants.UserMobile));
//        myAccountBinding.addressTv.setText(SharedPrefManager.getAddress(Constrants.));

        if (SharedPrefManager.getUserName(Constrants.UserName)!=null) {
            String[] name = SharedPrefManager.getUserName(Constrants.UserName).split(" ");
            if (Utils.checkEmptyNull(name[0])) {
                myAccountBinding.userNameTv.setText("Hello," + Utils.FirstLatterCap(name[0]));
            }
        }

        myAccountBinding.myOrderLL.setOnClickListener(v -> startActivity(new Intent(MyAccount.this, MyOrderList.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        myAccountBinding.userEmailTv.setText(SharedPrefManager.getUserEmail(Constrants.UserEmail));
        myAccountBinding.mobileTv.setText(SharedPrefManager.getUserMobile(Constrants.UserMobile));
//        myAccountBinding.addressTv.setText(SharedPrefManager.getAddress(Constrants.));

        if (SharedPrefManager.getUserName(Constrants.UserName)!=null) {
            String[] name = SharedPrefManager.getUserName(Constrants.UserName).split(" ");
            if (Utils.checkEmptyNull(name[0])) {
                myAccountBinding.userNameTv.setText("Hello," + Utils.FirstLatterCap(name[0]));
            }
        }
    }
}
