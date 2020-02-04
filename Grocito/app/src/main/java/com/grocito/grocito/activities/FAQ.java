package com.grocito.grocito.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.grocito.grocito.R;
import com.grocito.grocito.adapter.FaqAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityFaqBinding;
import com.grocito.grocito.model.FAQModel2;
import com.grocito.grocito.model.FaqModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FAQ extends AppCompatActivity {

    ArrayList<FAQModel2> arrayList = new ArrayList<>();
    private ActivityFaqBinding binding;
    private FaqAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_faq);

        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.productCatName.setText(getResources().getString(R.string.faqs));
        binding.headlyaout.backBtn.setOnClickListener(view->finish());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FaqAdapter(this,arrayList);
        binding.recyclerView.setAdapter(adapter);

        try {
            JSONArray jsonArray = new JSONArray(SharedPrefManager.getFaq(Constrants.Faq));

            if (jsonArray!=null&&jsonArray.length() > 0){
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    FAQModel2 faqModel2 = new FAQModel2();
                    faqModel2.setId(jsonObject.optString("id"));
                    faqModel2.setSection_id(jsonObject.optString("section_id"));
                    faqModel2.setTitle(jsonObject.optString("title"));
                    faqModel2.setDescription(jsonObject.optString("description"));
                    faqModel2.setBanned(jsonObject.optString("banned"));
                    faqModel2.setCreated_at(jsonObject.optString("created_at"));
                    faqModel2.setUpdated_at(jsonObject.optString("updated_at"));

                    arrayList.add(faqModel2);
                }
        }
//            FaqModel faqModel = JsonDeserializer.deserializeJson(jsonObject.toString(),FaqModel.class);
//            arrayList.addAll(faqModel.faq);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
