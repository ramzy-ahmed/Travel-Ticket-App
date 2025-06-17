package com.travel.ticket.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travel.ticket.app.Activity.DetailActivity;
import com.travel.ticket.app.Domain.Item;
import com.travel.ticket.app.databinding.ViewholderPopularBinding;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopluarViewholder> {
    private ArrayList<Item> items;
    private Context context;
    private ViewholderPopularBinding binding;

    public PopularAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PopularAdapter.PopluarViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= ViewholderPopularBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new PopluarViewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.PopluarViewholder holder, int position) {

        binding.titleTxt.setText(items.get(position).getTitle());
        binding.priceTxt.setText("$" + items.get(position).getPrice());

        binding.addressTxt.setText(items.get(position).getAddress());

        binding.scoreTxt.setText("" + items.get(position).getScore());

        Glide.with(context)
                .load(items.get(position).getPic())
                .into(binding.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object",items.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PopluarViewholder extends RecyclerView.ViewHolder {
        public PopluarViewholder(ViewholderPopularBinding binding) {
            super(binding.getRoot());
        }
    }
}
