package com.trulyfuture.seklo.screens.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.trulyfuture.seklo.MainActivity;
import com.trulyfuture.seklo.databinding.ActivitySignupBinding;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.utils.ProgressDialog;
import com.trulyfuture.seklo.utils.SharedPreferenceClass;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding signupBinding;
    private LoginSignupViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(signupBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(LoginSignupViewModel.class);

        signupBinding.signupBtn.setOnClickListener(view -> {
            if(!isFieldsEmpty()){
                ProgressDialog.showLoader(this);

                Users users=new Users();
                users.setFname(signupBinding.fullName.getText().toString());
                users.setLname(signupBinding.lastName.getText().toString());
                users.setEmail(signupBinding.email.getText().toString());
                users.setPassword(signupBinding.password.getText().toString());
                users.setNumber(signupBinding.number.getText().toString());


                viewModel.createUser(users).observe(this,results -> {
                    ProgressDialog.hideLoader();
                    if(results.getCode()==1){
                        addToSharedPrefs(results.getId());
                        Toast.makeText(this,results.getMessage(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    }
                    else {
                        Toast.makeText(this,results.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });

        signupBinding.signinHintTxt.setOnClickListener(view -> {
            finish();
        });
    }


    private boolean isFieldsEmpty() {
        if (TextUtils.isEmpty(signupBinding.fullName.getText())
                || TextUtils.isEmpty(signupBinding.lastName.getText())
                || TextUtils.isEmpty(signupBinding.email.getText())
                || TextUtils.isEmpty(signupBinding.password.getText())
                || TextUtils.isEmpty(signupBinding.number.getText())) {
            Toast.makeText(this, "Please complete all fields to continue", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void addToSharedPrefs(int userId){
        SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(this,SharedPreferenceClass.UserDetails);
        sharedPreferenceClass.SetIntegerEditor("userId",userId);
        sharedPreferenceClass.DoCommit();
    }

}