package com.trulyfuture.seklo.screens.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.trulyfuture.seklo.MainActivity;
import com.trulyfuture.seklo.screens.signup.SignupActivity;
import com.trulyfuture.seklo.databinding.ActivityLoginBinding;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.screens.signup.LoginSignupViewModel;
import com.trulyfuture.seklo.utils.SharedPreferenceClass;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;
    private LoginSignupViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(LoginSignupViewModel.class);


        loginBinding.loginBtn.setOnClickListener(view -> {

            if (!isFieldsEmpty()) {

                Users user = new Users();
                user.setStr(loginBinding.email.getText().toString());
                user.setPassword(loginBinding.password.getText().toString());
                viewModel.
                        authenticateUser(user)
                        .observe(this, results -> {
                            if (results.getCode() == 1) {
                                addToSharedPrefs(results.getId());
                                Toast.makeText(this, results.getMessage(), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(this, results.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }


        });

        loginBinding.signupHintTxt.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
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


    private void addToSharedPrefs(int userId){
        SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(this,SharedPreferenceClass.UserDetails);
        sharedPreferenceClass.SetIntegerEditor("userId",userId);
        sharedPreferenceClass.DoCommit();
    }

}