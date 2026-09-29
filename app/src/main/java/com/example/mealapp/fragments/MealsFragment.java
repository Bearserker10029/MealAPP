package com.example.mealapp.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mealapp.adapter.MealAdapter;
import com.example.mealapp.R;
import com.example.mealapp.databinding.FragmentMealsBinding;
import com.example.mealapp.dto.MealDetailResponse;
import com.example.mealapp.dto.MealsResponse;
import com.example.mealapp.network.RetrofitClient;
import com.example.mealapp.network.TheMealApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsFragment extends Fragment implements SensorEventListener {

    private FragmentMealsBinding binding;
    private final TheMealApi api = RetrofitClient.get().create(TheMealApi.class);

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long ultimaAgitacion = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMealsBinding.inflate(inflater, container, false);
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String categoria = getArguments() != null
                ? getArguments().getString("category") : null;
        if (categoria != null) {
            // Se llegó desde Categories: cargar platos de la categoría
            binding.textTitulo.setText("Categoría: " + categoria);
            binding.searchRow.setVisibility(View.GONE);
            buscarPorCategoria(categoria);
        }

        binding.buttonBuscar.setOnClickListener(v -> {
            String ingrediente = binding.editIngrediente.getText().toString().trim();
            if (!ingrediente.isEmpty()) {
                binding.textTitulo.setText("Platos");
                buscarPorIngrediente(ingrediente);
            }
        });
    }

    private void buscarPorCategoria(String categoria) {
        api.filterByCategory(categoria).enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarPlatos(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscarPorIngrediente(String ingrediente) {
        api.filterByIngredient(ingrediente).enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarPlatos(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarPlatos(ArrayList<MealsResponse.MealBrief> platos) {
        if (platos == null) return;
        MealAdapter adapter = new MealAdapter(platos, plato -> {
            Bundle bundle = new Bundle();
            bundle.putString("mealId", plato.getIdMeal());
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_meals_to_recipe, bundle);
        });
        binding.recyclerMeals.setAdapter(adapter);
    }

    // ------------------ Acelerómetro (solo en este fragmento) ------------------

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double magnitud = Math.sqrt(x * x + y * y + z * z);
        double aceleracion = Math.abs(magnitud - SensorManager.GRAVITY_EARTH);

        if (aceleracion > 4) { // umbral de 4 m/s²
            long ahora = System.currentTimeMillis();
            if (ahora - ultimaAgitacion > 3000) { // evita disparos repetidos
                ultimaAgitacion = ahora;
                obtenerPlatoAleatorio();
            }
        }
    }

    private void obtenerPlatoAleatorio() {
        api.getRandom().enqueue(new Callback<MealDetailResponse>() {
            @Override
            public void onResponse(Call<MealDetailResponse> call,
                                   Response<MealDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null
                        && response.body().getMeals() != null
                        && !response.body().getMeals().isEmpty()) {
                    String id = response.body().getMeals().get(0).getIdMeal();
                    Bundle bundle = new Bundle();
                    bundle.putString("mealId", id);
                    Navigation.findNavController(requireView())
                            .navigate(R.id.action_meals_to_recipe, bundle);
                }
            }

            @Override
            public void onFailure(Call<MealDetailResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}