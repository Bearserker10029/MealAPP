package com.example.mealapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealapp.databinding.ItemMealBinding;
import com.example.mealapp.dto.MealsResponse;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    public interface OnItemClickListener {
        void onClick(MealsResponse.MealBrief plato);
    }

    private final List<MealsResponse.MealBrief> lista;
    private final OnItemClickListener listener;

    public MealAdapter(List<MealsResponse.MealBrief> lista, OnItemClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMealBinding binding = ItemMealBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MealViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealsResponse.MealBrief plato = lista.get(position);
        holder.binding.textMealName.setText(plato.getStrMeal());
        holder.binding.textMealId.setText("ID: " + plato.getIdMeal());
        Glide.with(holder.binding.imageMeal.getContext())
                .load(plato.getStrMealThumb())
                .into(holder.binding.imageMeal);
        holder.binding.getRoot().setOnClickListener(v -> listener.onClick(plato));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        final ItemMealBinding binding;

        MealViewHolder(ItemMealBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
