package com.grocito.grocito.activities;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.grocito.grocito.R;
import com.grocito.grocito.adapter.PlaceAutocompleteAdapter;
import com.grocito.grocito.adapter.PlacesAutoCompleteAdapter;
import com.grocito.grocito.api.RequestCode;
import com.grocito.grocito.api.WebCompleteTask;
import com.grocito.grocito.api.WebTask;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.databinding.ActivityAddNewAddressBinding;
import com.grocito.grocito.model.PlaceInfo;

import org.json.JSONObject;

public class AddNewAddress extends AppCompatActivity implements WebCompleteTask ,GoogleApiClient.OnConnectionFailedListener {

    ActivityAddNewAddressBinding addNewAddressBinding;
    List<Object> object = new ArrayList<Object>();

    String type = "";
    String id, name, mobile, address, house, street, city, landmark, state, pincode, lattitude, longitude, is_default;

    private static String TAG = AddNewAddress.class.getSimpleName();

//    AutoCompleteTextView et_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNewAddressBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_address);

        addNewAddressBinding.headlyaout.searchIcon.setVisibility(View.GONE);
        addNewAddressBinding.headlyaout.cartRL.setVisibility(View.GONE);

        addNewAddressBinding.headlyaout.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addNewAddressBinding.officeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "office";
                addNewAddressBinding.officeBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));
                addNewAddressBinding.homeBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
                addNewAddressBinding.otherBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
            }
        });
        addNewAddressBinding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "home";
                addNewAddressBinding.homeBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));
                addNewAddressBinding.officeBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
                addNewAddressBinding.otherBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
            }
        });
        addNewAddressBinding.otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "other";
                addNewAddressBinding.otherBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));
                addNewAddressBinding.homeBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
                addNewAddressBinding.officeBtn.setBackground(getResources().getDrawable(R.drawable.greyborderlightradius));
            }
        });

        if (getIntent().getExtras() != null) {
            Log.i("sdafsfsafsf", object.toString());
            id = getIntent().getExtras().getString("id", "");
            type = getIntent().getExtras().getString("type", "");
            name = getIntent().getExtras().getString("name", "");
            // = getIntent().getExtras().getString("user_id","");
            mobile = getIntent().getExtras().getString("mobile", "");
            address = getIntent().getExtras().getString("address", "");
            house = getIntent().getExtras().getString("house", "");
            street = getIntent().getExtras().getString("street", "");
            city = getIntent().getExtras().getString("city", "");
            landmark = getIntent().getExtras().getString("landmark", "");
            state = getIntent().getExtras().getString("state", "");
            pincode = getIntent().getExtras().getString("pincode", "");
            lattitude = getIntent().getExtras().getString("lattitude", "");
            longitude = getIntent().getExtras().getString("longitude", "");
            is_default = getIntent().getExtras().getString("is_default", "");

            addNewAddressBinding.stateEt.setText(SharedPrefManager.getState(Constrants.State));
            addNewAddressBinding.cityEt.setText(SharedPrefManager.getCity(Constrants.City));
            addNewAddressBinding.pincodeEt.setText(pincode);
            addNewAddressBinding.nameEt.setText(name);
            addNewAddressBinding.contactEt.setText(mobile);
            addNewAddressBinding.streetdetailEt.setText(street);
            addNewAddressBinding.houseNoEt.setText(house);
            addNewAddressBinding.areaLocalityEt.setText(address);

            if (type.equalsIgnoreCase("home"))
                addNewAddressBinding.homeBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));
            else if (type.equalsIgnoreCase("office"))
                addNewAddressBinding.officeBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));
            else
                addNewAddressBinding.otherBtn.setBackground(getResources().getDrawable(R.drawable.assentcolorborderlightraduis));

        }
        if (id!=null) {
            addNewAddressBinding.headlyaout.productCatName.setText(getResources().getString(R.string.edit_address));
            addNewAddressBinding.AddBtn.setText(getResources().getString(R.string.update_address));
        } else{
            addNewAddressBinding.headlyaout.productCatName.setText(getResources().getString(R.string.add_new_address));
            addNewAddressBinding.AddBtn.setText(getResources().getString(R.string.add_address));
        }

        addNewAddressBinding.AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id!=null) {
                    updateAddress(id);
                } else{
                    addAddress();
                }
            }
        });
        init();

    }


    private GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40,-168),new LatLng(71,136)
    );
    private PlaceInfo mPlace;
    String lat,lng;

    private void init(){
        Log.d(TAG,"init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,this)
                .build();

        addNewAddressBinding.etAddressSign.setOnItemClickListener(mAutoCompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this,mGoogleApiClient,
                LAT_LNG_BOUNDS,null);
        addNewAddressBinding.etAddressSign.setAdapter(mPlaceAutocompleteAdapter);

        addNewAddressBinding.etAddressSign.setOnEditorActionListener((textView, i, keyEvent) -> false);
//        addNewAddressBinding.etAddressSign.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH
//                        || actionId == EditorInfo.IME_ACTION_DONE
//                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
//                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
//
//                    //excuting our method for searching
//                    geoLocate();
//                }
//                return false;
//            }
//        });
        SharedPrefManager.getInstance(this).hideSoftKeyBoard(this);
    }
    private void geoLocate() {
        Log.d(TAG, "geoLocate: geoLoating");
        String searchString = addNewAddressBinding.etAddressSign.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString,1);
        }catch (IOException e){
            Log.d(TAG, "geoLocate: IOexception" + e.getMessage());
        }

        if (list.size()>0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location" + address.toString());
            //Toast.makeText(getContext(),address.toString(),Toast.LENGTH_LONG).show();
            //moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));
        }
    }
    /*
       -------------------------------google places API autocomplete suggestions
 */
    private AdapterView.OnItemClickListener mAutoCompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SharedPrefManager.getInstance(getApplicationContext()).hideSoftKeyBoard(AddNewAddress.this);

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient,placeId);
            placeResult.setResultCallback(mUpdatePlaceDitailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDitailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()){
                Log.d(TAG,"onResult: place query did not complete successfully."+ places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);

            try {
                mPlace = new PlaceInfo();
                mPlace.setAddress(place.getAddress().toString());
//                mPlace.setAttributions(place.getAttributions().toString());
                mPlace.setId(place.getId().toString());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                mPlace.setLatLng(place.getLatLng());
                mPlace.setName(place.getName().toString());
                mPlace.setRating(place.getRating());
                mPlace.setWebsiteuri(place.getWebsiteUri());

                Log.d(TAG,"OnResult: place details " + mPlace.toString());

            }catch (NullPointerException e){
                Log.d(TAG,"OnResult: NullPointerException " + e.getMessage());
            }

            LatLng latlng = mPlace.getLatLng();
            lat = String.valueOf(latlng.latitude);
            lng = String.valueOf(latlng.longitude);
//            addressObj = new JSONObject();
//            try {
//                JSONObject locationobj = new JSONObject();
//                locationobj.put("lat",lat);
//                locationobj.put("lng", lng);
//                addressObj.put("address", et_address.getText().toString().trim());
//                addressObj.put("location", locationobj);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            Log.d(TAG,"OnResult: place lat lng details " + lat + ","+ lng);

            places.release();
        }
    };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void updateAddress(String Address_id) {
        HashMap objectNew = new HashMap();
        objectNew.put("address_id", Address_id);
        objectNew.put("name", addNewAddressBinding.nameEt.getText().toString());
        objectNew.put("street", addNewAddressBinding.streetdetailEt.getText().toString());
        objectNew.put("house", addNewAddressBinding.houseNoEt.getText().toString());
        objectNew.put("address", addNewAddressBinding.areaLocalityEt.getText().toString());
        objectNew.put("type", type);

        Log.i("updateAddress_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.UpdateUserAddress,
                objectNew, AddNewAddress.this, RequestCode.CODE_UpdateUserAddress, 5);
    }
    public void addAddress() {
        HashMap objectNew = new HashMap();
        objectNew.put("user_id", SharedPrefManager.getUserID(Constrants.UserId));
        objectNew.put("name", addNewAddressBinding.nameEt.getText().toString());
        objectNew.put("street", addNewAddressBinding.streetdetailEt.getText().toString());
        objectNew.put("house", addNewAddressBinding.houseNoEt.getText().toString());
        objectNew.put("address", addNewAddressBinding.areaLocalityEt.getText().toString());
        objectNew.put("lattitude", "");
        objectNew.put("longitude","");
        objectNew.put("city", addNewAddressBinding.cityEt.getText().toString());
        objectNew.put("state", addNewAddressBinding.stateEt.getText().toString());
        objectNew.put("pincode", addNewAddressBinding.pincodeEt.getText().toString());
        objectNew.put("type", type);

        Log.i("addAddress_obj", objectNew + "");
        new WebTask(this, WebUrls.BASE_URL + WebUrls.AddUserAddress,
                objectNew, AddNewAddress.this, RequestCode.CODE_AddUserAddress, 5);
    }
    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_UpdateUserAddress == taskcode) {
            Log.i("updateAddress_res", response);
            finish();
        }
        if (RequestCode.CODE_AddUserAddress == taskcode) {
            Log.i("addAddress_res", response);
            finish();
        }
    }




}
