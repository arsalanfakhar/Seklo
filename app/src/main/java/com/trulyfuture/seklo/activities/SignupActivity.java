package com.trulyfuture.seklo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.trulyfuture.seklo.databinding.ActivitySignupBinding;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.viewmodels.LoginViewModel;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding signupBinding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(signupBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        signupBinding.signupBtn.setOnClickListener(view -> {

            Users users = new Users();
            users.setFname("abc");
            users.setLname("abc");
            users.setEmail("abc@abc.com");
            users.setPassword("123456");
            users.setNumber("324234");


            viewModel.createUser(users).observe(this, results -> {
                if (results != null) {
                    if (results.getCode() == 1) {

                        Toast.makeText(this, results.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, results.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

//            if(!isFieldsEmpty()){
//                Users users=new Users();
//                users.setFname(signupBinding.fullName.getText().toString());
//                users.setLname(signupBinding.fullName.getText().toString());
//                users.setEmail(signupBinding.email.getText().toString());
//                users.setPassword(signupBinding.password.getText().toString());
//                users.setNumber(signupBinding.number.getText().toString());
//
//
//                viewModel.createUser(users).observe(this,results -> {
//                    if(results.getCode()==1){
//                        Toast.makeText(this,results.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(this,results.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//            startActivity(new Intent(SignupActivity.this,MainActivity.class));
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


}