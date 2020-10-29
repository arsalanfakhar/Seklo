package com.trulyfuture.seklo.screens.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.trulyfuture.seklo.MainActivity;
import com.trulyfuture.seklo.databinding.ResetPasswordPopupBinding;
import com.trulyfuture.seklo.screens.signup.SignupActivity;
import com.trulyfuture.seklo.databinding.ActivityLoginBinding;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.screens.signup.LoginSignupViewModel;
import com.trulyfuture.seklo.utils.ProgressDialog;
import com.trulyfuture.seklo.utils.SharedPreferenceClass;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;
    private LoginSignupViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(LoginSignupViewModel.class);


        loginBinding.forgetPassBtn.setOnClickListener(v -> {
            showForgetPassPopup();
        });

        loginBinding.loginBtn.setOnClickListener(view -> {

            if (!isFieldsEmpty()) {

                ProgressDialog.showLoader(this);

                Users user = new Users();
                user.setStr(loginBinding.email.getText().toString());
                user.setPassword(loginBinding.password.getText().toString());
                viewModel.
                        authenticateUser(user)
                        .observe(this, results -> {
                            ProgressDialog.hideLoader();
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

        if (!isValidEmail(loginBinding.email.getText().toString())) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void addToSharedPrefs(int userId) {
        SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(this, SharedPreferenceClass.UserDetails);
        sharedPreferenceClass.SetIntegerEditor("userId", userId);
        sharedPreferenceClass.DoCommit();
    }

    private void showForgetPassPopup() {
        ResetPasswordPopupBinding popupBinding = ResetPasswordPopupBinding.inflate(getLayoutInflater());

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setView(popupBinding.getRoot());


        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        popupBinding.sendEmailBtn.setOnClickListener(v -> {
            if(TextUtils.isEmpty(popupBinding.email.getText())){
                Toast.makeText(this,"Fields are empty",Toast.LENGTH_SHORT).show();
            }
            else {
                if (isInternetAvailable()) {
                    Map<String,Object> passMap=new HashMap<>();
                    passMap.put("Email",popupBinding.email.getText().toString().toLowerCase());

                    viewModel.resetPassword(passMap).observe(this,sekloResults -> {
                        if (sekloResults.getResults().getCode() == 1) {
                            Toast.makeText(this,"Check your email to reset password",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(this,sekloResults.getResults().getMessage(),Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    });
                } else
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}