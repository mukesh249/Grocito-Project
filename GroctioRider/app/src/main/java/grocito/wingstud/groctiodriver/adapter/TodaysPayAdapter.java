package grocito.wingstud.groctiodriver.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import grocito.wingstud.groctiodriver.R;
import grocito.wingstud.groctiodriver.bean.WalletBean;
import grocito.wingstud.groctiodriver.databinding.TodaysPayLayoutBinding;

/**
 * Created by hemant on 24-08-2019.
 */

public class TodaysPayAdapter extends RecyclerView.Adapter<TodaysPayAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<WalletBean> list;

    public TodaysPayAdapter(Context mContext, ArrayList<WalletBean> list){
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodaysPayLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.todays_pay_layout, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WalletBean bean = list.get(position);

        holder.binding.txtOrderId.setText(mContext.getString(R.string.order_id) + ": " + bean.getOrder_id());
//        holder.binding.txtMobile.setText("+91 " + bean.get());
        holder.binding.txtPrice.setText(mContext.getString(R.string.Rs) + bean.getAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TodaysPayLayoutBinding binding;

        public MyViewHolder(final TodaysPayLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

            this.binding.txtMobile.setVisibility(View.GONE);
        }
    }
}
