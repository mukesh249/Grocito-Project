package com.grocito.grocito.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import com.grocito.grocito.adapter.ImageViewPagerAdapter;
import com.grocito.grocito.adapter.SimilarProductAdapter;
import com.grocito.grocito.api.JsonDeserializer;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.model.CartAddModel;
import com.grocito.grocito.model.ImageModel;
import com.grocito.grocito.R;
import com.grocito.grocito.model.ProductDetialModel;
import com.grocito.grocito.model.SelectedSellerModel2;
import com.grocito.grocito.utils.Utils;
import com.grocito.grocito.databinding.ActivityProductDetailBinding;

@SuppressLint("DefaultLocale")
public class ProductDetail extends AppCompatActivity implements WebCompleteTask {

    private ActivityProductDetailBinding binding;
    ImageViewPagerAdapter imageViewPagerAdapter;
    ArrayList<ImageModel> imageModelArrayList = new ArrayList<>();

    @BindView(R.id.backBtn)
    ImageView backBtn;
    @BindView(R.id.search_icon)
    ImageView search_icon;

    private SimilarProductAdapter similarProductAdapter;
    private List<ProductDetialModel.RelatedProductList> arrayList = new ArrayList<>();
    private int countitem = 0;

    private String product_slug;
    public static boolean product_detail = true;
    List<ProductDetialModel.ProductItem_> prices_arrayList = new ArrayList<>();
    private String sellerId, itemId, productId, qty, is_return, is_exchange;
    int item = 1;
    public AlertDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);

        ButterKnife.bind(this, this);
        progressDialog = new SpotsDialog(this, R.style.Custom);
        binding.dotsIndicator.setViewPager(binding.viewPager);

        binding.productRecyView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.productRecyView.setNestedScrollingEnabled(false);

        similarProductAdapter = new SimilarProductAdapter(this, arrayList);
        binding.productRecyView.setAdapter(similarProductAdapter);
        similarProductAdapter.notifyDataSetChanged();

        backBtn.setOnClickListener(view -> finish());
        product_detail = true;
        binding.headlyaout.cartIcon.setOnClickListener(view -> startActivity(new Intent(ProductDetail.this, Cart.class)));
        binding.headlyaout.searchIcon.setOnClickListener(view -> startActivity(new Intent(ProductDetail.this, SearchItem.class)));
        binding.saveForLaterLL.setOnClickListener(view -> {
            binding.saveForLaterLL.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast.makeText(ProductDetail.this, "Save For Later", Toast.LENGTH_LONG).show();
        });
        Utils.strikeText(binding.priveDisTv);

        countitem = SharedPrefManager.getCartItemCount(Constrants.CartItemCount);
        setCartCount(countitem);

        binding.plusIv.setOnClickListener(v -> {
            item+=item;
            binding.countTV.setText(item+"");
        });
        binding.minusIv.setOnClickListener(v -> {
            if (item>0) {
                item -= item;
                binding.countTV.setText(item + "");
            }
        });
        binding.addCartLL.setOnClickListener(view -> {
            qty = binding.countTV.getText().toString();
            addtoCart(sellerId, productId, itemId, qty);
        });

        if (getIntent().getExtras() != null) {
            product_slug = getIntent().getExtras().getString("product_slug", "");
        }
        productDetail();
        binding.capcitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (prices_arrayList.size() > 0) {
                    itemId = prices_arrayList.get(position).id.toString();
                    binding.priveDisTv.setText(String.format("%.2f",Double.parseDouble(prices_arrayList.get(position).price)));
                    binding.priceTV.setText(String.format("%.2f",Double.parseDouble(prices_arrayList.get(position).sprice)));
                    binding.offerPriceTv.setText(String.format("Rs.%s Off", prices_arrayList.get(position).offer));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void productDetail() {
        Utils.ProgressShow(this, binding.matrialProgress, binding.nestScrollview);
        HashMap objectNew = new HashMap();
        objectNew.put("product_slug", product_slug);
        objectNew.put("pincode", SharedPrefManager.getPinCode(Constrants.PinCode));
        Log.i("productDetail_res", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.ProductDetails, objectNew,
                ProductDetail.this, RequestCode.CODE_ProductDetails, 5);
    }

    private void addtoCart(String seller_id, String product_id, String itemId, String qty) {
        ProgressDialog("");
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        objectNew.put("product_id", product_id);
        objectNew.put("is_return", is_return);
        objectNew.put("is_exchange", is_exchange);
        objectNew.put("seller_id", seller_id);
        objectNew.put("qty", qty);
        objectNew.put("item_id", itemId + "");
        Log.i("addtoCart_obj", objectNew + "");
        new WebTask(this,WebUrls.BASE_URL+WebUrls.AddToCart,objectNew,
                this, RequestCode.CODE_AddToCart,5);
    }


    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_ProductDetails == taskcode) {
            Log.i("productDetail_res", response);

            ProductDetialModel productDetialModel = JsonDeserializer.deserializeJson(response, ProductDetialModel.class);

            if (productDetialModel.statusCode == 1) {
                binding.productNameTv.setText(productDetialModel.data.productName);
                productId = productDetialModel.data.productDetails.id.toString();
                is_exchange = productDetialModel.data.productDetails.isExchange.toString();
                is_return = productDetialModel.data.productDetails.isReturn.toString();
                binding.aboutViewTv.setText(Html.fromHtml(productDetialModel.data.productDetails.description));

                if (productDetialModel.data.productDetails.brand!=null&&productDetialModel.data.productDetails.brand.name != null)
                    binding.brandTv.setText(productDetialModel.data.productDetails.brand.name);

                //--------------------------Price Spinner---------------
                ArrayList<String> priceAry = new ArrayList<>();
                if (productDetialModel.data.productItem != null && productDetialModel.data.productItem.size() > 0) {
                    this.prices_arrayList.addAll(productDetialModel.data.productItem);
                    for (ProductDetialModel.ProductItem_ price : productDetialModel.data.productItem) {
                        priceAry.add(String.format("%s - Rs%.2f", price.weight, Double.parseDouble(price.sprice)));
                    }
                    ArrayAdapter<String> priceadapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, priceAry);
                    binding.capcitySpinner.setAdapter(priceadapter);
                }



                prices_arrayList.clear();
                //--------------------------Image View---------------
                if (productDetialModel.data.allImage != null
                        && productDetialModel.data.allImage.size() > 0) {

                    for (ProductDetialModel.AllImage allImage : productDetialModel.data.allImage) {
                        ImageModel imageModel = new ImageModel();
                        imageModel.setImage(productDetialModel.data.productImage + allImage.image);
                        imageModelArrayList.add(imageModel);
                    }
                    imageViewPagerAdapter = new ImageViewPagerAdapter(this, imageModelArrayList);
                    binding.viewPager.setAdapter(imageViewPagerAdapter);
                    imageViewPagerAdapter.notifyDataSetChanged();
                }

                //--------------------------Similar Products---------------
                if (productDetialModel.data.relatedProductList != null
                        && productDetialModel.data.relatedProductList.size() > 0) {
                    binding.similarTv.setVisibility(View.VISIBLE);
                    this.arrayList.addAll(productDetialModel.data.relatedProductList);
                    similarProductAdapter.notifyDataSetChanged();
                } else {
                    binding.similarTv.setVisibility(View.GONE);
                }

                //-------------------------------Seller array------------------------------

                ArrayList<String> sellerList = new ArrayList<>();
                if (productDetialModel.data.sellerList != null
                        && productDetialModel.data.sellerList.size() > 0) {
                    for (ProductDetialModel.SellerList sellerList1 : productDetialModel.data.sellerList) {
                        sellerList.add(sellerList1.name);
                    }
                    ArrayAdapter<String> selleradapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, sellerList);
                    binding.sellerSpinner.setAdapter(selleradapter);
                    binding.sellerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            sellerId = productDetialModel.data.sellerList.get(position).sellerId.toString();
                            getSellerProductItem(sellerId, productId);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }


                Utils.ProgressHide(this, binding.matrialProgress, binding.nestScrollview);
            }

        }
        if (RequestCode.CODE_GetSellerProductItem == taskcode) {
            Log.i("GetSellerProductItem", response);
            //--------------------------Price Spinner---------------
            SelectedSellerModel2 selectedSellerModel = JsonDeserializer.deserializeJson(response, SelectedSellerModel2.class);
            if (selectedSellerModel.statusCode == 1) {
                prices_arrayList.clear();
                ArrayList<String> priceAry = new ArrayList<>();
                if (selectedSellerModel.data.itemData != null) {
                    prices_arrayList.addAll(selectedSellerModel.data.itemData);
                    for (ProductDetialModel.ProductItem_ price : selectedSellerModel.data.itemData) {
                        priceAry.add(String.format("%s - Rs%.2f", price.weight, Double.parseDouble(price.sprice)));
                    }
                    ArrayAdapter<String> priceadapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, priceAry);
                    binding.capcitySpinner.setAdapter(priceadapter);
                }
            }

        }
        if (RequestCode.CODE_AddToCart == taskcode) {
            Log.i("AddtoCart", response);
            CartAddModel cartAddModel = JsonDeserializer.deserializeJson(response, CartAddModel.class);
            if (cartAddModel.status == 1) {
                setCartCount(cartAddModel.cartCount);
            }
            ProgressDialog(cartAddModel.message);
        }
    }

    private void getSellerProductItem(String seller_id, String product_id) {
        HashMap objectNew = new HashMap();
        objectNew.put("seller_id", seller_id);
        objectNew.put("product_id", product_id);
        Log.i("GetSellerProductItem", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.GetSellerProductItem, objectNew, this, RequestCode.CODE_GetSellerProductItem, 5);
    }
    public void ProgressDialog(String message){
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
            Utils.Toast(this,message);
        }
        else
            progressDialog.show();
    }

    public void setCartCount(int count) {
        if (binding.headlyaout.cartItemNo != null) {
            if (count > 0) {
                binding.headlyaout.cartItemNo.setVisibility(View.VISIBLE);
            } else {
                binding.headlyaout.cartItemNo.setVisibility(View.GONE);
            }
            binding.headlyaout.cartItemNo.setText(String.valueOf(count));
        }
    }
}
