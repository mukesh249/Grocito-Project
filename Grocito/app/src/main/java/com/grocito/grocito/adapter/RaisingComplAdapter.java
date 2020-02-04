package com.grocito.grocito.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.OtherItemBinding;
import com.grocito.grocito.databinding.RaisingComplItemBinding;
import com.grocito.grocito.model.IsSpecialModel;
import com.grocito.grocito.model.RaisingModel;
import com.grocito.grocito.utils.Utils;

import java.util.ArrayList;

public class RaisingComplAdapter extends RecyclerView.Adapter<RaisingComplAdapter.ViewHolder> {

    ArrayList<RaisingModel.Datum> arrayList = new ArrayList<>();

    Context context;

    public RaisingComplAdapter(Context context, ArrayList<RaisingModel.Datum> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RaisingComplItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                ,R.layout.raising_compl_item,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RaisingModel.Datum datum = arrayList.get(i);

        viewHolder.binding.dateTimeTv.setText(String.format("Date : %s",datum.createdAt));
        viewHolder.binding.problemIdTv.setText(String.format("#%s",datum.complaintId));
        viewHolder.binding.titleTv.setText(Utils.FirstLatterCap(String.format("%s",datum.title)));
        viewHolder.binding.problemTv.setText(Utils.FirstLatterCap(String.format("Qus.  %s",datum.problem)));
        viewHolder.binding.solutionTv.setText(Utils.FirstLatterCap(String.format("Ans.  %s",datum.solution)));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RaisingComplItemBinding binding;
        public ViewHolder(@NonNull RaisingComplItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
