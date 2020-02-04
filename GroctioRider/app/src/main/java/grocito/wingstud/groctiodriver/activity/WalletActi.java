package grocito.wingstud.groctiodriver.activity;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import grocito.wingstud.groctiodriver.Api.RequestCode;
import grocito.wingstud.groctiodriver.Api.WebCompleteTask;
import grocito.wingstud.groctiodriver.Api.WebTask;
import grocito.wingstud.groctiodriver.Api.WebUrls;
import grocito.wingstud.groctiodriver.Common.Constrants;
import grocito.wingstud.groctiodriver.Common.SharedPrefManager;
import grocito.wingstud.groctiodriver.R;
import grocito.wingstud.groctiodriver.adapter.WalletAdapter;
import grocito.wingstud.groctiodriver.bean.WalletBean;
import grocito.wingstud.groctiodriver.databinding.ActivityWalletBinding;

import static grocito.wingstud.groctiodriver.util.Utils.Tosat;

public class WalletActi extends AppCompatActivity implements WebCompleteTask {

    private Toolbar toolbar;

    private Context mContext;

    private ActivityWalletBinding binding;

    private WalletAdapter mAdapter;
    private ArrayList<WalletBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet);

        mContext = WalletActi.this;
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.rvWallet.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvWallet.setItemAnimator(new DefaultItemAnimator());

        binding.toolbar.imvNotification.setVisibility(View.VISIBLE);

        CommisionList();
    }


    private void setCommissionData() {
        WalletBean bean = new WalletBean();
        list.add(bean);
        list.add(bean);

        mAdapter = new WalletAdapter(mContext, list);
        binding.rvWallet.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void CommisionList(){

        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
//        objectNew.put("user_id", "40");

        new WebTask(WalletActi.this, WebUrls.BASE_URL+ WebUrls.ComminsionWalletListApi,objectNew, WalletActi.this, RequestCode.CODE_CommissionList,1);

    }
    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_CommissionList == taskcode){
            System.out.println("CommissionList_res: "+response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status_code")==1){
                    JSONArray dataArray = jsonObject.optJSONArray("data");

                    binding.txtBalance.setText(String.format("Rs.%s",jsonObject.optString("total")));
                    if (dataArray.length()>0){
                        for (int i=0;i<dataArray.length();i++){
                            JSONObject dataObj = dataArray.getJSONObject(i);
                            WalletBean walletBean = new WalletBean();
                            walletBean.setUser_id(dataObj.optString("user_id"));
                            walletBean.setAmount(dataObj.optString("amount"));
                            walletBean.setOrder_id(dataObj.optString("order_id"));
                            walletBean.setPayment_amount(dataObj.optString("payment_amount"));
                            walletBean.setFrom_address(dataObj.optString("from_address"));
                            walletBean.setTo_address(dataObj.optString("to_address"));
                            walletBean.setDelivery_date(dataObj.optString("delivery_date"));
                            walletBean.setDelivery_time(dataObj.optString("delivery_time"));
                            list.add(walletBean);
                        }

                        mAdapter = new WalletAdapter(mContext, list);
                        binding.rvWallet.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }else {

                    }
                }else {
                    Tosat(this,jsonObject.optString("error_message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
