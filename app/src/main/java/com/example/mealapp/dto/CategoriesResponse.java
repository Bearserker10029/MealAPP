package com.example.mealapp.dto;

import java.util.ArrayList;

public class CategoriesResponse {
    private ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public static class Category {
        private String idCategory;
        private String strCategory;
        private String strCategoryThumb;
        private String strCategoryDescription;

        public String getIdCategory() {
            return idCategory;
        }

        public String getStrCategory() {
            return strCategory;
        }

        public String getStrCategoryThumb() {
            return strCategoryThumb;
        }

        public String getStrCategoryDescription() {
            return strCategoryDescription;
        }
    }
}
