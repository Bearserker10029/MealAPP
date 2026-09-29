package com.example.mealapp.dto;

import java.util.ArrayList;

public class MealDetailResponse {
    private ArrayList<MealDetail> meals;

    public ArrayList<MealDetail> getMeals() {
        return meals;
    }

    public static class MealDetail {
        private String idMeal;
        private String strMeal;
        private String strCategory;
        private String strArea;
        private String strInstructions;
        private String strMealThumb;

        private String strIngredient1;
        private String strIngredient2;
        private String strIngredient3;
        private String strIngredient4;
        private String strIngredient5;
        private String strIngredient6;
        private String strIngredient7;
        private String strIngredient8;
        private String strIngredient9;
        private String strIngredient10;
        private String strIngredient11;
        private String strIngredient12;
        private String strIngredient13;
        private String strIngredient14;
        private String strIngredient15;
        private String strIngredient16;
        private String strIngredient17;
        private String strIngredient18;
        private String strIngredient19;
        private String strIngredient20;

        private String strMeasure1;
        private String strMeasure2;
        private String strMeasure3;
        private String strMeasure4;
        private String strMeasure5;
        private String strMeasure6;
        private String strMeasure7;
        private String strMeasure8;
        private String strMeasure9;
        private String strMeasure10;
        private String strMeasure11;
        private String strMeasure12;
        private String strMeasure13;
        private String strMeasure14;
        private String strMeasure15;
        private String strMeasure16;
        private String strMeasure17;
        private String strMeasure18;
        private String strMeasure19;
        private String strMeasure20;

        public String getIdMeal() {
            return idMeal;
        }

        public String getStrMeal() {
            return strMeal;
        }

        public String getStrCategory() {
            return strCategory;
        }

        public String getStrArea() {
            return strArea;
        }

        public String getStrInstructions() {
            return strInstructions;
        }

        public String getStrMealThumb() {
            return strMealThumb;
        }

        public String[] getIngredientes() {
            return new String[]{strIngredient1, strIngredient2, strIngredient3, strIngredient4,
                    strIngredient5, strIngredient6, strIngredient7, strIngredient8, strIngredient9,
                    strIngredient10, strIngredient11, strIngredient12, strIngredient13, strIngredient14,
                    strIngredient15, strIngredient16, strIngredient17, strIngredient18, strIngredient19,
                    strIngredient20};
        }

        public String[] getMedidas() {
            return new String[]{strMeasure1, strMeasure2, strMeasure3, strMeasure4,
                    strMeasure5, strMeasure6, strMeasure7, strMeasure8, strMeasure9,
                    strMeasure10, strMeasure11, strMeasure12, strMeasure13, strMeasure14,
                    strMeasure15, strMeasure16, strMeasure17, strMeasure18, strMeasure19,
                    strMeasure20};
        }
    }
}
