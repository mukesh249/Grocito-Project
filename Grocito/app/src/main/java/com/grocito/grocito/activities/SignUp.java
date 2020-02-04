package com.grocito.grocito.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.grocito.grocito.R;
import com.grocito.grocito.databinding.ActivitySignUpBinding;
import com.grocito.grocito.presenter.SignUpPresenter;
import com.grocito.grocito.viewmodel.SignUpViewModel;

public class SignUp extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);

        signUpViewModel = new SignUpViewModel(this);
        binding.setSignupViewModel(signUpViewModel);
        binding.setSignupPresenter(new SignUpPresenter() {
            @Override
            public void SignupData() {
                final String f_name = binding.firstNameEt.getText().toString();
                final String l_name = binding.lastNameEt.getText().toString();
                final String email = binding.emailEt.getText().toString();
                final String mobile = binding.phoneNoEt.getText().toString();
                final String referral = binding.referralEt.getText().toString();
                signUpViewModel.SignUpRequest(mobile, f_name, l_name, email, referral);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        binding.bottomLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
