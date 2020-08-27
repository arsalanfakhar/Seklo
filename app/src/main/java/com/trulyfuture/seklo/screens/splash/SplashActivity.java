package com.trulyfuture.seklo.screens.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.screens.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        },SPLASH_TIMEOUT);
    }
}