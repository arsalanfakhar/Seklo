package com.trulyfuture.seklo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.trulyfuture.seklo.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding signupBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signupBinding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(signupBinding.getRoot());


        signupBinding.signupBtn.setOnClickListener(view -> {
            startActivity(new Intent(SignupActivity.this,MainActivity.class));
        });

        signupBinding.signinHintTxt.setOnClickListener(view -> {
            finish();
        });
    }
}