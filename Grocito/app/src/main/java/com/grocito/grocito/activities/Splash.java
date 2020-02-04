package com.grocito.grocito.activities;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.R;
import com.grocito.grocito.model.UserPagesModel;

public class Splash extends AppCompatActivity implements WebCompleteTask {

    private static String TAG = "Splash";

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPrefManager = new SharedPrefManager(this);
//        PermissionActivity permissionActivity = new PermissionActivity();
//        if(permissionActivity.checkPermission(getApplicationContext())){
        CommonPages();
            initApp();
//        }else {
//            startActivity(new Intent(new Intent(Splash.this,PermissionActivity.class)));
//            finish();
//        }
    }

    private void initApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (SharedPrefManager.isIntro(Constrants.IsIntro)){
                    if (SharedPrefManager.isLogin(Constrants.IsLogin)) {
                        intent = new Intent(Splash.this, HomeScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else {
                        intent = new Intent(Splash.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    intent = new Intent(Splash.this, IntroActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2500);
    }

    public void CommonPages(){
        HashMap objectNew = new HashMap();
//        objectNew.put("")
        new WebTask(this, WebUrls.BASE_URL+WebUrls.UserPages,objectNew,Splash.this, RequestCode.CODE_UserPages,5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_UserPages == taskcode){
            Log.i("user_pages",response);
            UserPagesModel userPagesModel = JsonDeserializer.deserializeJson(response,UserPagesModel.class);
            if (userPagesModel.statusCode==1){
                SharedPrefManager.setAboutUsDes(Constrants.AboutUs,userPagesModel.data.aboutUs.description);
                SharedPrefManager.setPrivacyPolicy(Constrants.PrivacyPolicy,userPagesModel.data.privacyPolicy.description);
                SharedPrefManager.setTermCondition(Constrants.TermCondition,userPagesModel.data.termCondition.description);
                SharedPrefManager.setCancelReturn(Constrants.CancelReturn,userPagesModel.data.returnPolicy.description);
                SharedPrefManager.setFaq(Constrants.Faq,userPagesModel.data.faq.toString());
            }
        }
    }
}
