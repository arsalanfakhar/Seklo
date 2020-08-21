package com.trulyfuture.seklo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.ActivityLoginBinding;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.viewmodels.LoginSignupViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;
    private LoginSignupViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(LoginSignupViewModel.class);


        loginBinding.loginBtn.setOnClickListener(view -> {

            if(!isFieldsEmpty()){
                Users users=new Users();
                users.setEmail(loginBinding.email.getText().toString());
                users.setPassword(loginBinding.password.getText().toString());

                viewModel.authenticateUser(users).observe(this,results -> {
                    if(results.getCode()==1){
                        Toast.makeText(this,results.getMessage(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(this,results.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }



        });

        loginBinding.signupHintTxt.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this,SignupActivity.class));
        });

    }

    private boolean isFieldsEmpty() {
        if (TextUtils.isEmpty(loginBinding.email.getText())
                || TextUtils.isEmpty(loginBinding.password.getText())) {
            Toast.makeText(this, "Please complete all fields to continue", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }



}