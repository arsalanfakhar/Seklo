package com.trulyfuture.seklo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setItemIconTintList(null);
    }
}