package com.example.mealapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mealapp.databinding.ActivityAppBinding;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAppBinding binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtener el NavController desde el NavHostFragment (no desde la vista)
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host);
        NavController navController = navHostFragment.getNavController();

        binding.bottomNav.setOnItemSelectedListener(item -> {
            // popUpTo inclusivo: el back stack siempre queda con un solo fragmento,
            // así el botón atrás regresa al MainActivity
            NavOptions options = new NavOptions.Builder()
                    .setPopUpTo(R.id.categoriesFragment, true)
                    .build();
            navController.navigate(item.getItemId(), null, options);
            return true;
        });

        binding.bottomNav.setOnItemReselectedListener(item -> {
            // no recrear el fragmento si se vuelve a seleccionar el mismo
        });
    }
}