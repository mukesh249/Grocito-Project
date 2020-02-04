package com.grocito.grocito.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.grocito.grocito.R;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityOtpScreenBinding;
import com.grocito.grocito.presenter.OtpPresenter;
import com.grocito.grocito.utils.Utils;
import com.grocito.grocito.viewmodel.OtpViewModel;

public class OtpScreen extends AppCompatActivity {

    ActivityOtpScreenBinding binding;
    OtpViewModel otpViewModel;
    String fname,lname,mobile,referral,email,response,activity;
    public static String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_otp_screen);databaseList();

        otpViewModel = new OtpViewModel(this);
        binding.setOtpViewModel(otpViewModel);
        if (getIntent().getExtras()!=null){
            activity = getIntent().getExtras().getString("activity","");
            if (activity.equals("login")){
                response = getIntent().getExtras().getString("response","");
            } else if (activity.equals("signup")){
                fname = getIntent().getExtras().getString("fname","");
                lname = getIntent().getExtras().getString("lname","");
                email = getIntent().getExtras().getString("email","");
                referral = getIntent().getExtras().getString("referral","");

            }
            mobile = getIntent().getExtras().getString("mobile","");
            otp = getIntent().getExtras().getString("otp","");
            Log.i("sdfasf", otp+"  "+mobile);
//            binding.otpString.setText(otp);

        }
        binding.setViewPresenter(new OtpPresenter() {
            @Override
            public void OtpResend() {
                otpViewModel.ResendOtp(mobile);
            }

            @Override
            public void OptVerifiy() {
                Log.i("otp_code", otp+"="+binding.firstPinView.getText());
                if (otp.compareTo(binding.firstPinView.getText().toString())==0) {
                    if (activity.equals("login")){
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("status_code") == 1) {

                                JSONArray dataArray = jsonObject.optJSONArray("data");
                                SharedPrefManager.setLogin(Constrants.IsLogin,true);
                                SharedPrefManager.setUserName(Constrants.UserName, dataArray.getJSONObject(0).optString("username"));
                                SharedPrefManager.setUserID(Constrants.UserId, dataArray.getJSONObject(0).optString("id"));
                                SharedPrefManager.setUserEmail(Constrants.UserEmail,dataArray.getJSONObject(0).optString("email" ));
                                SharedPrefManager.setUserMobile(Constrants.UserMobile,dataArray.getJSONObject(0).optString("mobile" ));
                                SharedPrefManager.setUserPic(Constrants.UserPic, dataArray.getJSONObject(0).optJSONObject("user_kyc").optString("profile_image"));
                                SharedPrefManager.setUserFirstName(Constrants.UserFirstName, dataArray.getJSONObject(0).optJSONObject("user_kyc").optString("f_name"));
                                SharedPrefManager.setUserLastName(Constrants.UserLastName, dataArray.getJSONObject(0).optJSONObject("user_kyc").optString("l_name"));

                                startActivity(new Intent(OtpScreen.this,HomeScreen.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else if (activity.equals("signup")){
                        otpViewModel.OtpVerifiyRequest(mobile,fname,lname,email,referral);
                    }
                } else {
                    Utils.Toast(OtpScreen.this, "OTP not Match");
                }
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
