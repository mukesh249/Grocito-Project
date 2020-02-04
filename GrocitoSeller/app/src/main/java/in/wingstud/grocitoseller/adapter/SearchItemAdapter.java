package in.wingstud.grocitoseller.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;

import in.wingstud.grocitoseller.R;
import in.wingstud.grocitoseller.activity.ProductUpload;
import in.wingstud.grocitoseller.databinding.ColorSelectItemBinding;
import in.wingstud.grocitoseller.databinding.SearchItemBinding;
import in.wingstud.grocitoseller.model.ColorModel;
import in.wingstud.grocitoseller.model.SearchModel;
import in.wingstud.grocitoseller.util.Utils;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> {

    Context context;
    ArrayList<SearchModel> arrayList = new ArrayList<>();
    public SearchItemAdapter(Context context, ArrayList<SearchModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.search_item,parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchModel model = arrayList.get(position);
        holder.binding.searchItem.setText(String.format("%s",model.getName()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SearchItemBinding binding;
        public ViewHolder(@NonNull SearchItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.searchItemLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    ProductUpload.getInstance().getsearchData(arrayList.get(pos).getJsonObject());
                }
            });

        }
    }
}
