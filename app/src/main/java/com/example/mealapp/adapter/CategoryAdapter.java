package com.example.mealapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealapp.databinding.ItemCategoryBinding;
import com.example.mealapp.dto.CategoriesResponse;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public interface OnItemClickListener {
        void onClick(CategoriesResponse.Category categoria);
    }

    private final List<CategoriesResponse.Category> lista;
    private final OnItemClickListener listener;

    public CategoryAdapter(List<CategoriesResponse.Category> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoriesResponse.Category categoria = lista.get(position);
        holder.binding.textName.setText(categoria.getStrCategory());
        holder.binding.textDescription.setText(categoria.getStrCategoryDescription());
        Glide.with(holder.binding.imageCategory.getContext())
                .load(categoria.getStrCategoryThumb())
                .into(holder.binding.imageCategory);
        holder.binding.getRoot().setOnClickListener(v -> listener.onClick(categoria));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        final ItemCategoryBinding binding;

        CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
