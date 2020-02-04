package grocito.wingstud.groctiodriver.ui.profile;


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

import grocito.wingstud.groctiodriver.Api.RequestCode;
import grocito.wingstud.groctiodriver.Api.WebCompleteTask;
import grocito.wingstud.groctiodriver.Api.WebTask;
import grocito.wingstud.groctiodriver.Api.WebUrls;
import grocito.wingstud.groctiodriver.Common.Constrants;
import grocito.wingstud.groctiodriver.Common.SharedPrefManager;
import grocito.wingstud.groctiodriver.R;
import grocito.wingstud.groctiodriver.databinding.FragmentProfileBinding;

import static grocito.wingstud.groctiodriver.util.Utils.Tosat;

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
    }

    private void initialize() {
        binding.txtMobile.setText(SharedPrefManager.getMobile(Constrants.UserMobile));
        binding.txtGender.setText(SharedPrefManager.getMobile(Constrants.UserGender));
        binding.txtAddress.setText(SharedPrefManager.getMobile(Constrants.UserAddress));
        binding.txtName.setText(SharedPrefManager.getMobile(Constrants.UserName));

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
                        binding.txtAddress.setText(dataObj.optJSONObject("user_kyc").optString("address"));
                        binding.txtName.setText(dataObj.optString("username"));

                }else {
                    Tosat(getActivity(),jsonObject.optString("error_message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
