package in.wingstud.grocitoseller.fragment;


import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.wingstud.grocitoseller.Api.RequestCode;
import in.wingstud.grocitoseller.Api.WebCompleteTask;
import in.wingstud.grocitoseller.Api.WebTask;
import in.wingstud.grocitoseller.Api.WebUrls;
import in.wingstud.grocitoseller.Common.Constrants;
import in.wingstud.grocitoseller.Common.SharedPrefManager;
import in.wingstud.grocitoseller.R;
import in.wingstud.grocitoseller.activity.Dashboard;
import in.wingstud.grocitoseller.databinding.FragmentProfileBinding;
import in.wingstud.grocitoseller.util.Utils;

import static in.wingstud.grocitoseller.util.Utils.FirstLatterCap;
import static in.wingstud.grocitoseller.util.Utils.Tosat;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFrag extends Fragment implements WebCompleteTask {

    private View view;

    private Context mContext;

    private FragmentProfileBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        view = binding.getRoot();

        initialize();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Dashboard) getContext()).setTitle(mContext.getString(R.string.profile), false);
    }

    private void initialize() {
        binding.txtMobile.setText(SharedPrefManager.getMobile(Constrants.UserMobile));
        binding.txtGender.setText(SharedPrefManager.getMobile(Constrants.UserGender));
        binding.txtAddress.setText(FirstLatterCap(SharedPrefManager.getAddress(Constrants.UserAddress)));
        binding.txtName.setText(Utils.FirstLatterCap(SharedPrefManager.getUserName(Constrants.UserName)));
        Utils.setImage(getActivity(), binding.imvUser,WebUrls.ImageUrl
                +"/"+SharedPrefManager.getProfilePic(Constrants.UserPic));

        ProfileApi(SharedPrefManager.getUserID(Constrants.UserId));
    }


    public void ProfileApi(String mobile){

        HashMap objectNew = new HashMap();
        objectNew.put("user_id",mobile);

        new WebTask(getActivity(), WebUrls.BASE_URL+ WebUrls.ProfileApi,objectNew, ProfileFrag.this, RequestCode.CODE_ProfileById,1);

    }

    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_ProfileById == taskcode) {
            System.out.println("Profile_res: " + response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status_code")==1){

                        JSONObject dataObj = jsonObject.optJSONObject("data");

                        binding.txtMobile.setText(dataObj.optString("mobile"));
                        binding.txtGender.setText(dataObj.optJSONObject("user_kyc").optString("gender"));
                        binding.txtAddress.setText(FirstLatterCap(dataObj.optJSONObject("user_kyc").optString("address_1")));
                        binding.txtName.setText(FirstLatterCap(dataObj.optString("username")));

                }else {
                    Tosat(getActivity(),jsonObject.optString("error_message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
