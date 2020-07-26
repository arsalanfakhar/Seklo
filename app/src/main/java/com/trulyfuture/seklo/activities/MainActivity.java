package com.trulyfuture.seklo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

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

        //no tint to bottom nav icons
        binding.bottomNavigationView.setItemIconTintList(null);

        //setup bottom navigation
        NavController navController= Navigation.findNavController(this,R.id.navHostfragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController);
    }
}