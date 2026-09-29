package com.example.mealapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mealapp.databinding.FragmentRecipeBinding;
import com.example.mealapp.dto.MealDetailResponse;
import com.example.mealapp.network.RetrofitClient;
import com.example.mealapp.network.TheMealApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeFragment extends Fragment {

    private FragmentRecipeBinding binding;
    private final TheMealApi api = RetrofitClient.get().create(TheMealApi.class);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonBuscar.setOnClickListener(v -> {
            String id = binding.editIdMeal.getText().toString().trim();
            if (!id.isEmpty()) {
                buscarReceta(id);
            }
        });

        // Si se llegó desde el RecyclerView de Meals (o por agitación), cargar automáticamente
        String mealId = getArguments() != null
                ? getArguments().getString("mealId") : null;
        if (mealId != null && !mealId.isEmpty()) {
            binding.editIdMeal.setText(mealId);
            buscarReceta(mealId);
        }
    }

    private void buscarReceta(String id) {
        api.lookup(id).enqueue(new Callback<MealDetailResponse>() {
            @Override
            public void onResponse(Call<MealDetailResponse> call,
                                   Response<MealDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null
                        && response.body().getMeals() != null
                        && !response.body().getMeals().isEmpty()) {
                    mostrarReceta(response.body().getMeals().get(0));
                } else {
                    Toast.makeText(getContext(), "No se encontró la receta",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MealDetailResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarReceta(MealDetailResponse.MealDetail meal) {
        binding.textMealName.setText(meal.getStrMeal());
        binding.textMealCategory.setText("Categoría: " + meal.getStrCategory());
        binding.textMealArea.setText("Origen: " + meal.getStrArea());
        binding.textInstructions.setText(meal.getStrInstructions());

        StringBuilder ingredientes = new StringBuilder();
        String[] nombres = meal.getIngredientes();
        String[] medidas = meal.getMedidas();
        for (int i = 0; i < nombres.length; i++) {
            if (nombres[i] != null && !nombres[i].trim().isEmpty()) {
                ingredientes.append("• ").append(nombres[i].trim());
                if (medidas[i] != null && !medidas[i].trim().isEmpty()) {
                    ingredientes.append(" (").append(medidas[i].trim()).append(")");
                }
                ingredientes.append("\n");
            }
        }
        binding.textIngredients.setText(ingredientes.toString());

        if (getContext() != null) {
            Glide.with(getContext()).load(meal.getStrMealThumb()).into(binding.imageMeal);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}