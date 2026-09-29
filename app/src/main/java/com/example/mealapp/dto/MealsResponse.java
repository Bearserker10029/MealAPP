package com.example.mealapp.dto;

import java.util.ArrayList;

public class MealsResponse {
    private ArrayList<MealBrief> meals;

    public ArrayList<MealBrief> getMeals() {
        return meals;
    }

    public static class MealBrief {
        private String idMeal;
        private String strMeal;
        private String strMealThumb;

        public String getIdMeal() {
            return idMeal;
        }

        public String getStrMeal() {
            return strMeal;
        }

        public String getStrMealThumb() {
            return strMealThumb;
        }
    }
}
