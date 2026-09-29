package com.example.mealapp.network;


import com.example.mealapp.dto.CategoriesResponse;
import com.example.mealapp.dto.MealDetailResponse;
import com.example.mealapp.dto.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMealApi {
    // GET 1: todas las categorías
    @GET("api/json/v1/1/categories.php")
    Call<CategoriesResponse> getCategories();

    // GET 2A: filtrar por categoría
    @GET("api/json/v1/1/filter.php")
    Call<MealsResponse> filterByCategory(@Query("c") String category);

    // GET 2B: filtrar por ingrediente
    @GET("api/json/v1/1/filter.php")
    Call<MealsResponse> filterByIngredient(@Query("i") String ingredient);

    // GET 3: detalle del plato por idMeal
    @GET("api/json/v1/1/lookup.php")
    Call<MealDetailResponse> lookup(@Query("i") String idMeal);

    // GET 4: receta aleatoria
    @GET("api/json/v1/1/random.php")
    Call<MealDetailResponse> getRandom();
}
