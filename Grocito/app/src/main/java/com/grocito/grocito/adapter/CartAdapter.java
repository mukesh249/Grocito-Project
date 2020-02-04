package com.grocito.grocito.adapter;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import com.grocito.grocito.activities.Cart;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.model.CartDataModel;
import com.grocito.grocito.R;
import com.grocito.grocito.databinding.CartItemBinding;
import com.grocito.grocito.utils.Utils;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> implements WebCompleteTask {

    List<CartDataModel.Datum> arrayList;
    Context context;
//    View.OnClickListener onClickListener;

    public CartAdapter(Context context, List<CartDataModel.Datum> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
//        this.onClickListener=onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        CartItemBinding cartItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext())
                ,R.layout.cart_item,viewGroup,false);
        return new ViewHolder(cartItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//
        CartDataModel.Datum cartDataModel = arrayList.get(i);
        viewHolder.binding.setCartView(cartDataModel);
        viewHolder.binding.totalTV.setText(cartDataModel.sprice);
        viewHolder.binding.totalTV.setText(Integer.parseInt(cartDataModel.qty)*Integer.parseInt(cartDataModel.sprice)+"");
        Utils.setImage(context,viewHolder.binding.productImage, WebUrls.IMAGE_PRODUCT+cartDataModel.productImage);



//        viewHolder.binding.plusIv.setTag(i);
//        viewHolder.binding.minusIv.setTag(i);
//        viewHolder.binding.plusIv.setOnClickListener(onClickListener);
//        viewHolder.binding.minusIv.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CartItemBinding binding;


        public ViewHolder(CartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.plusIv.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                int item = Integer.parseInt(binding.countTV.getText().toString());
                item += 1;
                binding.countTV.setText(String.valueOf(item));
                binding.totalTV.setText(String.valueOf(item*Integer.parseInt(binding.priceTV.getText().toString())));
                qtyInc(pos,item,arrayList.get(pos).id);
            });

            binding.minusIv.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                int item = Integer.parseInt(binding.countTV.getText().toString());
                if (item>1){
                    item -= 1;
                    binding.countTV.setText(String.valueOf(item));
                    binding.totalTV.setText(String.valueOf(item*Integer.parseInt(binding.priceTV.getText().toString())));
                    qtyInc(pos,item,arrayList.get(pos).id);
                }
            });

            binding.productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    context.startActivity(new Intent(context, ProductDetail.class));
                }
            });
            binding.deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartItemDelete(getAdapterPosition(),arrayList.get(getAdapterPosition()).id);
                }
            });


        }
    }
    private int cartPos;
    public void qtyInc(int pos,int qty,int cartId) {
        cartPos = pos;
        HashMap objectNew = new HashMap();
        objectNew.put("cart_id", cartId+"");
        objectNew.put("qty", qty+"");
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        arrayList.get(cartPos).qty = qty+"";
        Log.i("qtyInc_obj", objectNew + "");
        new WebTask(context, WebUrls.BASE_URL + WebUrls.UpdateCartData,
                objectNew, this, RequestCode.CODE_UpdateCartData, 1);
    }
    public void cartItemDelete(int pos,int cartId) {
        Cart.getInstance().showProgress();
        HashMap objectNew = new HashMap();
        objectNew.put("cart_id", cartId+"");
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        Log.i("deleteCartItem_obj", objectNew + "");
        new WebTask(context, WebUrls.BASE_URL + WebUrls.DeleteCart,
                objectNew, this, RequestCode.CODE_DeleteCart, 5);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_UpdateCartData==taskcode){
            Log.i("qtyInc_res", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status")==1){

                    notifyDataSetChanged();
                    Cart.getInstance().setTotalSum();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (RequestCode.CODE_DeleteCart==taskcode){
            Log.i("deleteCartItem_res", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.optInt("status")==1){
                    Cart.getInstance().cartList();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
