package com.grocito.grocito.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.databinding.FaqItemBinding;
import com.grocito.grocito.model.FAQModel2;
import com.grocito.grocito.model.FaqModel;
import com.grocito.grocito.utils.Utils;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {

    ArrayList<FAQModel2> arrayList = new ArrayList<>();

    Context context;

    public FaqAdapter(Context context, ArrayList<FAQModel2> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FaqItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                ,R.layout.faq_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FAQModel2 datum = arrayList.get(i);
        viewHolder.binding.quesTv.setText(Utils.FirstLatterCap(String.format("Qus.  %s",datum.getTitle())));
        viewHolder.binding.ansTv.setText(Utils.FirstLatterCap(String.format("Ans.  %s",datum.getDescription())));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FaqItemBinding binding;
        public ViewHolder(@NonNull FaqItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
