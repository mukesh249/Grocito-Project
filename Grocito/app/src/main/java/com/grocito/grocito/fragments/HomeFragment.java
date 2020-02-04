package com.grocito.grocito.fragments;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.grocito.grocito.activities.ProductDetail;
import com.grocito.grocito.adapter.CateHomeAdapter;
import com.grocito.grocito.adapter.HomeAdapter;
import com.grocito.grocito.adapter.ImageViewPagerAdapter;
import com.grocito.grocito.adapter.OtherAdapter;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.MyApplication;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.model.HomeCatProductModel;
import com.grocito.grocito.model.CateHomeModel;
import com.grocito.grocito.model.HomeModel;
import com.grocito.grocito.model.ImageModel;
import com.grocito.grocito.R;
import com.grocito.grocito.databinding.FragmentHomeBinding;
import com.grocito.grocito.model.IsSpecialModel;
import com.grocito.grocito.utils.Utils;
import com.grocito.grocito.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment implements WebCompleteTask {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //------------Bottom Image ------------------------
    OtherAdapter otherAdapter;
    FragmentHomeBinding binding;
    ArrayList<IsSpecialModel> specialArrayList = new ArrayList<>();

    //--------------------Slider-----------------------
    ImageViewPagerAdapter imageViewPagerAdapter;
    ArrayList<ImageModel> imageModelArrayList;


    //--------------Category---------------------------
    CateHomeAdapter cateHomeAdapter;
    ArrayList<CateHomeModel> cateHomeModelArrayList = new ArrayList<>();
    private int size;
    private boolean click = false;

    //---------------Category wtih product-------------
    ArrayList<HomeModel> homeModelArrayList = new ArrayList<>();
    HomeAdapter homeAdapter;

    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);

        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        ProductDetail.product_detail = false;

        //----------------Slider Image----------------------
        imageModelArrayList = new ArrayList<>();
        imageViewPagerAdapter = new ImageViewPagerAdapter(getActivity(),imageModelArrayList);
        binding.viewPager.setAdapter(imageViewPagerAdapter);
        binding.dotsIndicator.setViewPager(binding.viewPager);

        //-------------------------------------isSpecial-----------------

        GridLayoutManager manager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL,false);
        manager.setSpanSizeLookup(
                new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (position % 3 == 0 ? 2 : 1 );
                    }
                }
        );
        binding.isSpecialRecyclerView.setLayoutManager(manager);
        MyApplication.RecyclerView( binding.isSpecialRecyclerView);
        otherAdapter = new OtherAdapter(getActivity(),specialArrayList);
        binding.isSpecialRecyclerView.setAdapter(otherAdapter);
        otherAdapter.notifyDataSetChanged();

        //-------------------------------------Home Category with Product-----------------
        binding.shopbyCatRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        MyApplication.RecyclerView( binding.shopbyCatRecyclerView);

        //-------------------------------------Home Category with Product-----------------
        binding.TopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        MyApplication.RecyclerView( binding.TopRecyclerView);
        homeAdapter = new HomeAdapter(getActivity(),homeModelArrayList);
        binding.TopRecyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
        HomeData();

        binding.seeAll.setVisibility(View.GONE);
        binding.seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cateHomeModelArrayList.size()>4){
                    if (size==4){
                        size = cateHomeModelArrayList.size();
                        CateSize();
                    }else {
                        size = 4;
                        CateSize();
                    }
                }else {
                    size = cateHomeModelArrayList.size();
                }

            }
        });

        return binding.getRoot();
    }

    public void CateSize(){
        cateHomeAdapter = new CateHomeAdapter(getActivity(),cateHomeModelArrayList,size);
        binding.shopbyCatRecyclerView.setAdapter(cateHomeAdapter);
        cateHomeAdapter.notifyDataSetChanged();
    }

    public void HomeData(){
        Utils.ProgressShow(getActivity(),binding.materialProgress,binding.nestScrollview);
        HashMap objectNew = new HashMap();
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        new WebTask(getActivity(), WebUrls.BASE_URL+WebUrls.HomeApi,objectNew,HomeFragment.this, RequestCode.CODE_HomeData,5);
    }

    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_HomeData==taskcode){

            try {
                JSONObject jsonObject = new JSONObject(response);

                Log.i("Home_Dat_Res",response);
                if (jsonObject.optInt("status_code")==1){
                    JSONObject dataObj = jsonObject.optJSONObject("data");

                    //-------------------------------------Category home-----------------
                    if (dataObj.optJSONArray("catData").length()>0){
                        binding.seeAll.setVisibility(View.VISIBLE);
                        JSONArray catDataArry = dataObj.optJSONArray("catData");

                        for(int i = 0; i < catDataArry.length(); i++){
                            JSONObject catDataObj = catDataArry.getJSONObject(i);
                            CateHomeModel cateHomeModel = new CateHomeModel();
                            cateHomeModel.setId(catDataObj.optString("id"));
                            cateHomeModel.setName(catDataObj.optString("name"));
                            cateHomeModel.setImage(catDataObj.optString("image"));
                            cateHomeModelArrayList.add(cateHomeModel);
                        }

                        if (cateHomeModelArrayList.size()>4) {
                            size = 4;
                            binding.seeAll.setVisibility(View.VISIBLE);
                        }else {
                            size = cateHomeModelArrayList.size();
                            binding.seeAll.setVisibility(View.GONE);
                        }
                        CateSize();
                    }else {
                        binding.seeAll.setVisibility(View.GONE);
                    }
                    JSONArray catWithProductArry = dataObj.optJSONArray("catWithProduct");

                    //-------------------------------------Home Category with Product-----------------
                    if (catWithProductArry.length()>0){
                        for (int i = 0; i<catWithProductArry.length(); i++){
                            JSONObject catWithProductObj = catWithProductArry.optJSONObject(i);
                            HomeModel homeModel = new HomeModel();
                            homeModel.setId(catWithProductObj.optString("cat_id"));
                            homeModel.setCat_name(catWithProductObj.optString("cat_name"));
                            homeModel.setCat_slug(catWithProductObj.optString("cat_slug"));
                            ArrayList<HomeCatProductModel> bavArrylist = new ArrayList<>();

                            if (catWithProductObj.optJSONArray("productListData").length()>0) {
                                JSONArray prodlistDataArry = catWithProductObj.optJSONArray("productListData");
                                for (int j = 0; j < prodlistDataArry.length(); j++) {
                                    JSONObject prodlistDataObj = prodlistDataArry.optJSONObject(j);
                                    HomeCatProductModel homeCatProductModel = new HomeCatProductModel();
                                    homeCatProductModel.setName(prodlistDataObj.optString("p_name"));
                                    homeCatProductModel.setImage(prodlistDataObj.optString("p_image"));
                                    homeCatProductModel.setSlug(prodlistDataObj.optString("p_slug"));
                                    bavArrylist.add(homeCatProductModel);
                                }
                                homeModel.setBavArrayList(bavArrylist);
                            }
                            homeModelArrayList.add(homeModel);
                        }

                        homeAdapter.notifyDataSetChanged();
                    }

                    //-------------------------------------Home Slider-----------------
                    if (dataObj.optJSONArray("sliderList").length()>0){
                        JSONArray sliderListArry = dataObj.optJSONArray("sliderList");

                        for(int i = 0; i < sliderListArry.length(); i++){
                            JSONObject sliderListObj = sliderListArry.getJSONObject(i);
                            ImageModel imageModel = new ImageModel();
                            imageModel.setImage("public/admin/uploads/slider_image/"+sliderListObj.optString("images"));
                            imageModelArrayList.add(imageModel);
                        }
                        imageViewPagerAdapter.notifyDataSetChanged();
                    }

                    //-------------------------------------Banners-----------------
                    Utils.setImage(getActivity(),binding.middleImage,WebUrls.Banner_Image_URL+dataObj
                            .optJSONObject("middleBanner").optString("images"));
                    Utils.setImage(getActivity(),binding.bottomBanner,WebUrls.Banner_Image_URL+dataObj
                            .optJSONObject("bottomBanner").optString("images"));


                    //-------------------------------------isSpecial Image-----------------
                    JSONObject isSpecialObj = dataObj.optJSONObject("isSpecial");
                    binding.isSpecialHeadTv.setText(isSpecialObj.optString("name"));
                    if (isSpecialObj.optJSONArray("main_category").length()>0){
                        JSONArray main_categoryArry = isSpecialObj.optJSONArray("main_category");

                        for(int i = 0; i < main_categoryArry.length(); i++){
                            JSONObject main_categoryObj = main_categoryArry.getJSONObject(i);
                            IsSpecialModel isSpecialModel = new IsSpecialModel();
                            isSpecialModel.setId(main_categoryObj.optString("id"));
                            isSpecialModel.setCategory_id(main_categoryObj.optString("category_id"));
                            isSpecialModel.setName(main_categoryObj.optString("name"));
                            isSpecialModel.setCategory_slug(main_categoryObj.optString("category_slug"));
                            isSpecialModel.setSlug(main_categoryObj.optString("slug"));
                            isSpecialModel.setDescription(main_categoryObj.optString("description"));
                            isSpecialModel.setImage(main_categoryObj.optString("image"));

                            specialArrayList.add(isSpecialModel);
                        }
                        otherAdapter.notifyDataSetChanged();
                    }
                    Utils.ProgressHide(getActivity(),binding.materialProgress,binding.nestScrollview);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
