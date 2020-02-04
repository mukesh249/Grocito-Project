package grocito.wingstud.groctiodriver.activity;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import grocito.wingstud.groctiodriver.Api.RequestCode;
import grocito.wingstud.groctiodriver.Api.WebCompleteTask;
import grocito.wingstud.groctiodriver.Api.WebTask;
import grocito.wingstud.groctiodriver.Api.WebUrls;
import grocito.wingstud.groctiodriver.R;
import grocito.wingstud.groctiodriver.databinding.ActivityLoginBinding;
import grocito.wingstud.groctiodriver.util.Utils;
import static grocito.wingstud.groctiodriver.util.Utils.Tosat;

public class LoginActi extends AppCompatActivity implements WebCompleteTask {

    Toolbar toolbar;

    private Context mContext;

    private ActivityLoginBinding binding;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mContext = LoginActi.this;
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
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

    public void loginProcess(View view) {
        userName = binding.etMobileNo.getText().toString();
        if (Utils.isValidMobileNumber(mContext, userName)){
            LoingApi();
        }
    }

    public void LoingApi(){

        HashMap objectNew = new HashMap();
        objectNew.put("mobile",userName);

        new WebTask(LoginActi.this, WebUrls.BASE_URL+ WebUrls.Login,objectNew, LoginActi.this, RequestCode.CODE_Login,1);

    }

    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_Login == taskcode){
            System.out.println("Login_res: "+response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status_code")==1){
                    Tosat(this,jsonObject.optString("message"));
                    startActivity(new Intent(LoginActi.this,OtpActi.class)
                            .putExtra("user_id",jsonObject.optString("user_id"))
                            .putExtra("otp",jsonObject.optString("otp_code"))
                            .putExtra("mobile",userName));
                    finish();
                }else {
                    Tosat(this,jsonObject.optString("error_message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
