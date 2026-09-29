package com.example.mealapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mealapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(this)
                .load("https://www.themealdb.com/images/meal-icon.png")
                .into(binding.imageLogo);

        binding.buttonIngresar.setOnClickListener(v -> {
            if (tieneInternet()) {
                startActivity(new Intent(this, AppActivity.class));
            } else {
                mostrarDialogSinInternet();
            }
        });
    }

    private boolean tieneInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkCapabilities caps = cm.getNetworkCapabilities(cm.getActiveNetwork());
        return caps != null && (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
    }

    private void mostrarDialogSinInternet() {
        new AlertDialog.Builder(this)
                .setTitle("Sin conexión a Internet")
                .setMessage("Se requiere conexión a Internet para usar la aplicación.")
                .setPositiveButton("Configuración", (dialog, which) ->
                        startActivity(new Intent(Settings.ACTION_SETTINGS)))
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
