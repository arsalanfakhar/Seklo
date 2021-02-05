package com.trulyfuture.seklo.screens.login;

import androidx.annotation.Nullable;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.trulyfuture.seklo.MainActivity;
import com.trulyfuture.seklo.databinding.ResetPasswordPopupBinding;
import com.trulyfuture.seklo.screens.signup.SignupActivity;
import com.trulyfuture.seklo.databinding.ActivityLoginBinding;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.screens.signup.LoginSignupViewModel;
import com.trulyfuture.seklo.utils.ProgressDialog;
import com.trulyfuture.seklo.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;
    private LoginSignupViewModel viewModel;

    private CallbackManager callbackManager;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        viewModel = ViewModelProviders.of(this).get(LoginSignupViewModel.class);


        //Forget Password
        loginBinding.forgetPassBtn.setOnClickListener(v -> {
            showForgetPassPopup();
        });

        //Login facebook button
        callbackManager = CallbackManager.Factory.create();


        loginBinding.loginButtonFacebook.setPermissions(Arrays.asList("email", "public_profile"));
        loginBinding.loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                ProgressDialog.showLoader(LoginActivity.this);


//                Toast.makeText(LoginActivity.this,"facebook token"+loginResult.getAccessToken().getToken(),Toast.LENGTH_SHORT).show();
//
//                Toast.makeText(LoginActivity.this,"permissions"+loginResult.getRecentlyGrantedPermissions(),Toast.LENGTH_SHORT).show();
//
//                Toast.makeText(LoginActivity.this,"userId"+loginResult.getAccessToken().getUserId(),Toast.LENGTH_SHORT).show();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
                    String id = null;
                    try {
//                        AccessToken.setCurrentAccessToken(loginResult.getAccessToken());


                        id = object.getString("id");
                        String first_name = object.getString("first_name");
                        String last_name = object.getString("last_name");
//                        String gender = object.getString("gender");
//                        String birthday = object.getString("birthday");
                        String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";
                        String email = "";
                        if (object.has("email")) {
                            email = object.getString("email");
                        }

//                        String graphData="Fbdata"+first_name+"\n"+last_name+"\n"+image_url+"\n"+email;
//                        Toast.makeText(LoginActivity.this, "fbEmail:" + email, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(LoginActivity.this,"facebook token"+loginResult.getAccessToken(),Toast.LENGTH_SHORT).show();

                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("Email", email);
                        userMap.put("Fname", first_name);
                        userMap.put("Lname", last_name);
                        userMap.put("profilePic", image_url);
                        userMap.put("password", "noPassword");
                        userMap.put("Number", "");

//                        Toast.makeText(LoginActivity.this,"Graph API sucess",Toast.LENGTH_SHORT).show();

                        viewModel.facebookLogin(userMap).observe(LoginActivity.this, sekloResults -> {

                            ProgressDialog.hideLoader();

                            if (sekloResults.getResults().getCode() == 1) {
                                addToSharedPrefs(sekloResults.getResults().getId());
                                Toast.makeText(LoginActivity.this, sekloResults.getResults().getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                LoginManager.getInstance().logOut();

                                //Then Login using Login API
                                if (sekloResults.getResults().getMessage().contains("already exist")) {
                                    Users user = new Users();
                                    user.setStr(String.valueOf(userMap.get("Email")));
                                    user.setPassword("noPassword");
                                    loginUser(user);
                                } else {
                                    Toast.makeText(LoginActivity.this, sekloResults.getResults().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Graph APi error", Toast.LENGTH_SHORT).show();
                        if (ProgressDialog.isShowing())
                            ProgressDialog.hideLoader();
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Login button password
        loginBinding.loginBtn.setOnClickListener(view -> {
            if (!isFieldsEmpty()) {

                ProgressDialog.showLoader(this);

                Users user = new Users();
                user.setStr(loginBinding.email.getText().toString());
                user.setPassword(loginBinding.password.getText().toString());
                loginUser(user);
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

        popupBinding.closeBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });

        popupBinding.sendEmailBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(popupBinding.email.getText())) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            } else {
                if (isInternetAvailable()) {
                    Map<String, Object> passMap = new HashMap<>();
                    passMap.put("Email", popupBinding.email.getText().toString().toLowerCase());

                    viewModel.resetPassword(passMap).observe(this, sekloResults -> {
                        if (sekloResults.getResults().getCode() == 1) {
                            Toast.makeText(this, "Check your email to reset password", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(this, sekloResults.getResults().getMessage(), Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    });
                } else
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void loginUser(Users user) {
        viewModel.
                authenticateUser(user)
                .observe(this, results -> {

                    if (ProgressDialog.isShowing())
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

    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}