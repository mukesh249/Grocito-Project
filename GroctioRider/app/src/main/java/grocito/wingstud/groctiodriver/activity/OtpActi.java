package grocito.wingstud.groctiodriver.activity;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import grocito.wingstud.groctiodriver.Api.RequestCode;
import grocito.wingstud.groctiodriver.Api.WebCompleteTask;
import grocito.wingstud.groctiodriver.Api.WebTask;
import grocito.wingstud.groctiodriver.Api.WebUrls;
import grocito.wingstud.groctiodriver.Common.Constrants;
import grocito.wingstud.groctiodriver.Common.SharedPrefManager;
import grocito.wingstud.groctiodriver.R;
import grocito.wingstud.groctiodriver.databinding.ActivityOtpBinding;
import grocito.wingstud.groctiodriver.util.Utils;

import static grocito.wingstud.groctiodriver.util.Utils.Tosat;

public class OtpActi extends AppCompatActivity implements WebCompleteTask {

    private Toolbar toolbar;
    private Context mContext;
    private ActivityOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);

        mContext = OtpActi.this;
        initialize();


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Failed", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.e("NEW_TOKEN", token);
                        SharedPrefManager.setDeviceToken(Constrants.Token,token);
//                        Toast.makeText(OtpActi.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void verifyProcess(View view){
        if (getIntent().getExtras()!=null){
            Utils.PrintMsg("user_id: "+getIntent().getExtras().getString("user_id",""));
            OtpVerify(getIntent().getExtras().getString("user_id",""));
        }
    }
    public void resendProcess(View view){
        if (getIntent().getExtras()!=null){
            Utils.PrintMsg("mobile: "+getIntent().getExtras().getString("mobile",""));
            OtpResend(getIntent().getExtras().getString("mobile",""));
        }
    }
    public void OtpVerify(String userid){

        HashMap objectNew = new HashMap();
        objectNew.put("user_id",userid);
        objectNew.put("otp",binding.pinView.getValue());
        Utils.PrintMsg("Token_otpActicity "+SharedPrefManager.getDeviceToken(Constrants.Token));
        objectNew.put("device_token",SharedPrefManager.getDeviceToken(Constrants.Token));

        Utils.PrintMsg("Otp_vefity_Object: " + objectNew);

        new WebTask(OtpActi.this, WebUrls.BASE_URL+ WebUrls.OtpApi,objectNew, OtpActi.this, RequestCode.CODE_OtpCheck,1);

    }
    public void OtpResend(String mobile){

        HashMap objectNew = new HashMap();
        objectNew.put("mobile",mobile);

        new WebTask(OtpActi.this, WebUrls.BASE_URL+ WebUrls.ResendOtpApi,objectNew, OtpActi.this, RequestCode.CODE_ResendOtp,1);

    }

    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_OtpCheck == taskcode){
            System.out.println("Otp_res: "+response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status_code")==1){


                    if (jsonObject.optJSONArray("data").length()>0){
                        JSONObject dataObj = jsonObject.optJSONArray("data").getJSONObject(0);
                        SharedPrefManager.setLogin(Constrants.IsLogin,true);
                        SharedPrefManager.setUserID(Constrants.UserId,dataObj.optString("id"));
                        SharedPrefManager.setUserName(Constrants.UserName,dataObj.optString("username"));
                        SharedPrefManager.setUserEmail(Constrants.UserEmail,dataObj.optString("email"));
                        SharedPrefManager.setMobile(Constrants.UserMobile,dataObj.optString("mobile"));
                        SharedPrefManager.setProfilePic(Constrants.UserPic,dataObj.optJSONObject("user_kyc").optString("profile_image"));
                        SharedPrefManager.setGender(Constrants.UserGender,dataObj.optJSONObject("user_kyc").optString("gender"));
                        SharedPrefManager.setAddress(Constrants.UserAddress,dataObj.optJSONObject("user_kyc").optString("address"));

                        startActivity(new Intent(OtpActi.this,MainActivity.class));
                        finish();
                    }

                }else {
                    Tosat(this,jsonObject.optString("error_message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (RequestCode.CODE_ResendOtp == taskcode){
            System.out.println("ResendOtp_res: "+response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status_code")==1){
                    Tosat(this,jsonObject.optString("message"));
                }else {
                    Tosat(this,jsonObject.optString("error_message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
