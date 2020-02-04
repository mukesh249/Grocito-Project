package in.wingstud.grocitoseller.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.wingstud.grocitoseller.Api.RequestCode;
import in.wingstud.grocitoseller.Api.WebCompleteTask;
import in.wingstud.grocitoseller.Api.WebTask;
import in.wingstud.grocitoseller.Api.WebUrls;
import in.wingstud.grocitoseller.Common.Constrants;
import in.wingstud.grocitoseller.Common.SharedPrefManager;
import in.wingstud.grocitoseller.R;
import in.wingstud.grocitoseller.activity.Dashboard;
import in.wingstud.grocitoseller.activity.EditPost;
import in.wingstud.grocitoseller.activity.ProductUpload;
import in.wingstud.grocitoseller.databinding.RetryAlertBinding;
import in.wingstud.grocitoseller.model.ProductListModel;
import in.wingstud.grocitoseller.databinding.PopUpProductDetailsBinding;
import in.wingstud.grocitoseller.databinding.ProductUploadListItemBinding;
import in.wingstud.grocitoseller.fragment.Inventory;
import in.wingstud.grocitoseller.fragment.ProductUploadList;
import in.wingstud.grocitoseller.util.Utils;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> implements WebCompleteTask {


    ArrayList<ProductListModel> arrayList = new ArrayList<>();
    Context context;
    AlertDialog dialog;
    int raw_post;
    String type = "";

    public ProductListAdapter(ArrayList<ProductListModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductUploadListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.product_upload_list_item,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductListModel model = arrayList.get(position);

        holder.binding.IdTv.setText(String.format("ID : %s",model.getId()));
        holder.binding.createdTv.setText(String.format("%s",model.getCreated_at()));
        holder.binding.cateTv.setText(String.format("%s",model.getCategory_name()));
        holder.binding.subCatTv.setText(String.format("%s",model.getSub_category_name()));

        if (ProductUploadList.duplicate_product){
            holder.binding.deleteImage.setVisibility(View.VISIBLE);
        }else {
            if (String.format("%s",model.getStatus()).equals("Approved")) {
                holder.binding.deleteImage.setVisibility(View.GONE);
                holder.binding.statusTv.setTextColor(context.getResources().getColor(R.color.green));
            } else {
                holder.binding.deleteImage.setVisibility(View.VISIBLE);
                holder.binding.statusTv.setTextColor(context.getResources().getColor(R.color.red));
            }
        }

        holder.binding.statusTv.setText(String.format("%s",model.getStatus()));

        if (model.getImageArray().size()>0)
        Utils.setImage(context,holder.binding.productImage1, SharedPrefManager.getImagePath(Constrants.ImagePath)+"/"+model.getImageArray().get(0));

        holder.binding.viewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressDialog (model);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ProductUploadListItemBinding binding;
        public ViewHolder(@NonNull ProductUploadListItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            if (ProductUploadList.inventroy){
                if (Inventory.ins) {
                    binding.editTv.setText(context.getString(R.string.mark_out_of_stock));
                }else {
                    binding.editTv.setText(context.getString(R.string.mark_in_stock));
                }
            }else {
                binding.editTv.setText(context.getString(R.string.edit));
            }

            binding.deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog  = new AlertDialog.Builder(context);
                    RetryAlertBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context)
                            ,R.layout.retry_alert
                            ,null,
                            false);
                    alertDialog.setView(binding.getRoot());

                    binding.txtRTitle.setText(context.getResources().getString(R.string.app_name));
                    binding.txtRAMsg.setText(context.getString(R.string.ar_u_sr_delt));
                    binding.txtRAFirst.setText(context.getString(R.string.cancel));
                    binding.txtRASecond.setText(context.getString(R.string.yes));
                    dialog = alertDialog.create();
                    binding.txtRASecond.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            raw_post = getAdapterPosition();
                            if (ProductUploadList.duplicate_product){
                                DeleteDupItem(raw_post);
                            }else {
                                DeleteProduct(raw_post);
                            }
                        }
                    });


                    binding.txtRAFirst.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    if (dialog.getWindow()!=null)
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }
            });

            binding.editTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    raw_post = getAdapterPosition();
                    if (binding.editTv.getText().equals(context.getString(R.string.edit))){
                        if (ProductUploadList.duplicate_product){
                            context.startActivity(new Intent(context, ProductUpload.class)
                                    .putExtra("id",arrayList.get(raw_post).getId())
                                    .putExtra("data",arrayList.get(raw_post).getItemobje()+"")
                            );
                        }else {
                            context.startActivity(new Intent(context, EditPost.class)
                                    .putExtra("id",arrayList.get(raw_post).getId())
                                    .putExtra("data",arrayList.get(raw_post).getItemobje()+"")
                            );
                        }

                    }else if (binding.editTv.getText().equals(context.getString(R.string.mark_out_of_stock))){
                        type = "out_stock";
                        MarkForOutStock(raw_post);
                    }else {
                        type = "in_stock";
                        MarkForOutStock(raw_post);
                    }
                }
            });
        }
    }
    private void MarkForOutStock(int pos){
        HashMap objectNew = new HashMap();
        objectNew.put("product_id",arrayList.get(pos).getId());
        objectNew.put("type",type);
        Log.d("MarkForOutStock_obj",objectNew.toString());
        new WebTask(context, WebUrls.BASE_URL+WebUrls.Update_stock_status,objectNew,this, RequestCode.CODE_Update_stock_status,1);
    }

    private void DeleteDupItem(int pos){
        HashMap objectNew = new HashMap();
        objectNew.put("product_id",arrayList.get(pos).getId());
        Log.d("DeleteDupItem_obj",objectNew.toString());
        new WebTask(context, WebUrls.BASE_URL+WebUrls.DeleteDupItem,objectNew,this, RequestCode.CODE_Delete_Product,1);
    }
    private void DeleteProduct(int pos){
        HashMap objectNew = new HashMap();
        objectNew.put("product_id",arrayList.get(pos).getId());
        Log.d("DeleteProduct_obj",objectNew.toString());
        new WebTask(context, WebUrls.BASE_URL+WebUrls.Delete_Product,objectNew,this, RequestCode.CODE_Delete_Product,1);
    }
    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_Delete_Product==taskcode){
            Log.d("DeleteProduct_res",response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code")==1) {
                    arrayList.remove(raw_post);
                    notifyItemRemoved(raw_post);
                    notifyItemChanged(raw_post);
                    notifyItemRangeChanged(raw_post, arrayList.size());
                    dialog.dismiss();
                    Toast.makeText(context,jsonObject.optString("message"),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,jsonObject.optString("message"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (RequestCode.CODE_Update_stock_status==taskcode){
            Log.d("Update_stock_status_res",response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status_code")==1) {
                    arrayList.remove(raw_post);
                    notifyItemRemoved(raw_post);
                    notifyItemChanged(raw_post);
                    notifyItemRangeChanged(raw_post, arrayList.size());
                    Toast.makeText(context,jsonObject.optString("message"),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,jsonObject.optString("message"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private void addressDialog(ProductListModel model){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        PopUpProductDetailsBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(context),
                        R.layout.pop_up_product_details,
                        null,
                        false);
        builder.setView(binding.getRoot());
        Log.i("product_det",model.getColorAry()+"");

        binding.IdTv.setText(String.format("ID : %s",model.getId()));
        binding.createdTv.setText(String.format("%s",model.getCreated_at()));
        binding.cateTv.setText(String.format("%s",model.getCategory_name()));
        binding.subCatTv.setText(String.format("%s",model.getSub_category_name()));
        binding.statusTv.setText(String.format("%s",model.getStatus()));

        binding.colorRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        if (model.getColorAry().size()>0 && !model.getColorAry().get(0).equals("null")) {
            binding.colorLL.setVisibility(View.VISIBLE);
            ColorAdapter adapter = new ColorAdapter(context, model.getColorAry());
            binding.colorRv.setAdapter(adapter);
            notifyDataSetChanged();
        }else {
            binding.colorLL.setVisibility(View.GONE);
        }
        if ( model.getProductitem()!=null&&model.getProductitem().size()>0) {
            binding.productItemRv.setVisibility(View.VISIBLE);
            binding.productItemRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            ProductItemsAdapter productItemsAdapter = new ProductItemsAdapter(context,model.getProductitem());
            binding.productItemRv.setAdapter(productItemsAdapter);
            notifyDataSetChanged();
        }else {
            binding.productItemRv.setVisibility(View.GONE);
        }

        if (model.getImageArray().size()>0 && !model.getImageArray().get(0).equals("null")) {
            binding.imageRv.setVisibility(View.VISIBLE);
            binding.imageRv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            ImageAdapter imageAdapter = new ImageAdapter(context,model.getImageArray());
            binding.imageRv.setAdapter(imageAdapter);
            notifyDataSetChanged();
        }else {
            binding.imageRv.setVisibility(View.GONE);
        }



        AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow()!=null)
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

}
