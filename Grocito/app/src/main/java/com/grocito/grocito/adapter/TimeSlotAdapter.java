package com.grocito.grocito.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.grocito.grocito.R;
import com.grocito.grocito.activities.AddNewAddress;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.AddressItemBinding;
import com.grocito.grocito.databinding.TimeslotItemBinding;
import com.grocito.grocito.model.CheckOutModel;
import com.grocito.grocito.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {

    List<CheckOutModel.DateTimeSlot> arrayList;

    Context context;

    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;
    public static String seleted_Time= "";

    public TimeSlotAdapter(Context context, List<CheckOutModel.DateTimeSlot> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TimeslotItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.timeslot_item, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CheckOutModel.DateTimeSlot timeSlot = arrayList.get(i);
        viewHolder.binding.radioBtn1.setText(String.format("%s - %s",timeSlot.startTime , timeSlot.endTime));
        viewHolder.binding.radioBtn1.setChecked(timeSlot.isChecked);

        //for default check in first item
        if(i == 0 && timeSlot.isChecked && viewHolder.binding.radioBtn1.isChecked())
        {
            lastChecked = viewHolder.binding.radioBtn1;
            lastCheckedPos = 0;
        }

        viewHolder.binding.radioBtn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RadioButton cb = (RadioButton) v;

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        timeSlot.isChecked = false;
                    }

                    lastChecked = cb;
                    lastCheckedPos = i;
                }
                else
                    lastChecked = null;

                seleted_Time=timeSlot.startTime+"-"+timeSlot.endTime;
                Utils.Toast(context,timeSlot.startTime+"-"+timeSlot.endTime);
                timeSlot.isChecked = cb.isChecked();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TimeslotItemBinding binding;

        public ViewHolder(@NonNull TimeslotItemBinding
                                  itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

//    public void deleteAddress(int pos) {
////        rawpos = pos;
////        HashMap objectNew = new HashMap();
////        objectNew.put("address_id", arrayList.get(pos).id);
////        Log.i("DeleteUserAddress_obj", objectNew + "");
//      //  new WebTask(context, WebUrls.BASE_URL + WebUrls.DeleteUserAddress, objectNew, this, RequestCode.CODE_DeleteUserAddress, 5);
//    }
//
//    @Override
//    public void onComplete(String response, int taskcode) {
//        if (RequestCode.CODE_UserCheckout == taskcode) {
//            Log.i("DeleteUserAddress_res", response);
//
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = new JSONObject(response);
//                if (jsonObject.optInt("status_code") == 1){
//                    Utils.Toast(context,jsonObject.optString("message"));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
