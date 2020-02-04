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
import grocito.wingstud.groctiodriver.adapter.NotificationAdapter;
import grocito.wingstud.groctiodriver.bean.NotificationBean;
import grocito.wingstud.groctiodriver.databinding.ActivityNotificationBinding;

import static grocito.wingstud.groctiodriver.util.Utils.Tosat;

public class NotificationActi extends AppCompatActivity implements WebCompleteTask {

    private Toolbar toolbar;

    private Context mContext;

    private ActivityNotificationBinding binding;

    private ArrayList<NotificationBean> list = new ArrayList<>();
    private NotificationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

        mContext = NotificationActi.this;
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        binding.toolbar.activityTitle.setText(getString(R.string.notification));


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.rvNotification.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvNotification.setItemAnimator(new DefaultItemAnimator());

        binding.toolbar.imvNotification.setVisibility(View.GONE);

        NotificationList(SharedPrefManager.getUserID(Constrants.UserId));
    }

    public void NotificationList(String user_id){

        HashMap objectNew = new HashMap();
        objectNew.put("user_id",user_id);

        new WebTask(NotificationActi.this, WebUrls.BASE_URL+ WebUrls.NotificationListApi,objectNew, NotificationActi.this, RequestCode.CODE_NotificationList,1);

    }
    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_NotificationList == taskcode){
            System.out.println("NotificationList_res: "+response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status_code")==1){
                    JSONArray dataArray = jsonObject.optJSONArray("data");
                    if (dataArray.length()>0){
                        binding.relEmptyWL.setVisibility(View.GONE);
                        binding.rvNotification.setVisibility(View.VISIBLE);
                        for (int i=0;i<dataArray.length();i++){
                            JSONObject dataObj = dataArray.getJSONObject(i);
                            NotificationBean notificationBean = new NotificationBean();
                            notificationBean.setId(dataObj.optString("id"));
                            notificationBean.setUser_id(dataObj.optString("user_id"));
                            notificationBean.setTitle(dataObj.optString("title"));
                            notificationBean.setMessage(dataObj.optString("message"));
                            notificationBean.setCreated_at(dataObj.optString("created_at"));
                            notificationBean.setUpdated_at(dataObj.optString("updated_at"));

                            list.add(notificationBean);
                        }
                        mAdapter = new NotificationAdapter(mContext, list);
                        binding.rvNotification.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }else {
                        binding.relEmptyWL.setVisibility(View.VISIBLE);
                        binding.rvNotification.setVisibility(View.GONE);
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
