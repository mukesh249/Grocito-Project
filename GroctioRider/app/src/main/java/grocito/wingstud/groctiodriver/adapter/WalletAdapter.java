package grocito.wingstud.groctiodriver.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import grocito.wingstud.groctiodriver.R;
import grocito.wingstud.groctiodriver.bean.WalletBean;
import grocito.wingstud.groctiodriver.databinding.WalletListBeanBinding;

/**
 * Created by hemant on 25-08-2019.
 */

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<WalletBean> list;

    public WalletAdapter(Context mContext, ArrayList<WalletBean> list){
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WalletListBeanBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.wallet_list_bean, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WalletBean bean = list.get(position);

        try {
            holder.binding.txtOrderId.setText(mContext.getString(R.string.order_id) + " " + bean.getOrder_id());
            holder.binding.txtPickAdd.setText(bean.getFrom_address());
            holder.binding.txtDeliveryAdd.setText(bean.getTo_address());
            holder.binding.txtCommission.setText("Your Commission Rs."+bean.getAmount());
            holder.binding.txtTotal.setText("Rs."+ bean.getPayment_amount());
            holder.binding.txtDeliveryTime.setText("Delivery Time: "+ bean.getDelivery_time());
        } catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private WalletListBeanBinding binding;

        public MyViewHolder(final WalletListBeanBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
