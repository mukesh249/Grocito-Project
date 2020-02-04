package grocito.wingstud.groctiodriver.fragment;


import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;

import grocito.wingstud.groctiodriver.Api.RequestCode;
import grocito.wingstud.groctiodriver.Api.WebCompleteTask;
import grocito.wingstud.groctiodriver.Api.WebTask;
import grocito.wingstud.groctiodriver.Api.WebUrls;
import grocito.wingstud.groctiodriver.R;
import grocito.wingstud.groctiodriver.activity.OrderDetailActi;
import grocito.wingstud.groctiodriver.adapter.TOrdersAdapter;
import grocito.wingstud.groctiodriver.bean.TodayPayBean;
import grocito.wingstud.groctiodriver.databinding.FragmentHistoryBinding;
import grocito.wingstud.groctiodriver.util.recycler_view_utilities.RecyclerItemClickListener;

import static grocito.wingstud.groctiodriver.util.Utils.Tosat;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFrag extends Fragment implements WebCompleteTask {

    private View view;

    private Context mContext;

    private FragmentHistoryBinding binding;
    private AbstractList<TodayPayBean> orderList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    TOrdersAdapter mAdapter;
    ArrayList<TodayPayBean> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        view = binding.getRoot();

        initialize();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initialize() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        binding.rvOrders.setLayoutManager(mLayoutManager);
        binding.rvOrders.setItemAnimator(new DefaultItemAnimator());


        binding.rvOrders.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, OrderDetailActi.class);
//                intent.putExtra(Constants.ORDER_ID, orderList.get(position).getOrderId());
                startActivity(intent);
            }
        }));

        HistoryOrderList();

//        setData();
    }

    public void HistoryOrderList(){

        HashMap objectNew = new HashMap();
//        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        objectNew.put("user_id", "40");

        new WebTask(getActivity(), WebUrls.BASE_URL+ WebUrls.TodayPayment,objectNew, HistoryFrag.this, RequestCode.CODE_TodayPayment,1);

    }
    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_TodayPayment == taskcode){
            System.out.println("TodayPay_res: "+response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status_code")==1){
                    JSONArray dataArray = jsonObject.optJSONArray("data");
                    list.clear();
                    if (dataArray.length()>0){
                        binding.relEmptyWL.setVisibility(View.GONE);
                        binding.rvOrders.setVisibility(View.VISIBLE);
                        for (int i=0;i<dataArray.length();i++){
                            JSONObject dataObj = dataArray.getJSONObject(i);
                            TodayPayBean todayPayBean = new TodayPayBean();
                            todayPayBean.setUser_id(dataObj.optString("user_id"));
                            todayPayBean.setAmount(dataObj.optString("amount"));
                            todayPayBean.setOrder_id(dataObj.optString("order_id"));
                            todayPayBean.setPayment_amount(dataObj.optString("payment_amount"));
                            todayPayBean.setFrom_address(dataObj.optString("from_address"));
                            todayPayBean.setTo_address(dataObj.optString("to_address"));
                            todayPayBean.setDelivery_date(dataObj.optString("delivery_date"));
                            todayPayBean.setDelivery_time(dataObj.optString("delivery_time"));
                            list.add(todayPayBean);
                        }

                        mAdapter= new TOrdersAdapter(mContext, list);
                        binding.rvOrders.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }else {
                        binding.relEmptyWL.setVisibility(View.VISIBLE);
                        binding.rvOrders.setVisibility(View.GONE);
                    }
                }else {
                    Tosat(getActivity(),jsonObject.optString("error_message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
