package com.example.mealapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mealapp.adapter.CategoryAdapter;
import com.example.mealapp.R;
import com.example.mealapp.databinding.FragmentCategoriesBinding;
import com.example.mealapp.dto.CategoriesResponse;
import com.example.mealapp.network.RetrofitClient;
import com.example.mealapp.network.TheMealApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;
    private final TheMealApi api = RetrofitClient.get().create(TheMealApi.class);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargarCategorias();
    }

    private void cargarCategorias() {
        api.getCategories().enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call,
                                   Response<CategoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<CategoriesResponse.Category> lista = response.body().getCategories();
                    CategoryAdapter adapter = new CategoryAdapter(lista, categoria -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("category", categoria.getStrCategory());
                        Navigation.findNavController(requireView())
                                .navigate(R.id.action_categories_to_meals, bundle);
                    });
                    binding.recyclerCategories.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}