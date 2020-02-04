package grocito.wingstud.groctiodriver.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import grocito.wingstud.groctiodriver.R;
import grocito.wingstud.groctiodriver.bean.TodayPayBean;
import grocito.wingstud.groctiodriver.databinding.OrderListRowBinding;


public class TOrdersAdapter extends RecyclerView.Adapter<TOrdersAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TodayPayBean> list;

    public TOrdersAdapter(Context mContext, ArrayList<TodayPayBean> list){
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderListRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_list_row, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TodayPayBean bean = list.get(position);

        holder.binding.txtOrderId.setText(mContext.getString(R.string.order_id) + ": " + bean.getOrder_id());
        holder.binding.txtPrice.setText(mContext.getString(R.string.Rs) + bean.getAmount());
        holder.binding.txtDeliveryAdd.setText(bean.getTo_address());
        holder.binding.txtPickAdd.setText(bean.getFrom_address());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private OrderListRowBinding binding;

        public MyViewHolder(final OrderListRowBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }
}
