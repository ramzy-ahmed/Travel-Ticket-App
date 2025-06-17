package com.travel.ticket.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travel.ticket.app.Domain.Category;
import com.travel.ticket.app.R;
import com.travel.ticket.app.databinding.ViewholderCategoryBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewholder> {
    private final List<Category> items;
    private int selectedPosition = -1;
    private int lastSelectedPosition =-1;
    private Context context;
    public CategoryAdapter(List<Category> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        ViewholderCategoryBinding binding =ViewholderCategoryBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CategoryViewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewholder holder, @SuppressLint("RecyclerView") int position) {
        Category item =items.get(position);
        holder.binding.titleTxt.setText(item.getName());
        Glide.with(holder.itemView.getContext())
                .load(item.getImagePath())
                .into(holder.binding.pic);

        holder.binding.getRoot().setOnClickListener(v ->{
            lastSelectedPosition = selectedPosition;
            selectedPosition=position;
            notifyItemChanged(lastSelectedPosition);
            notifyItemChanged(selectedPosition);
        });
        holder.binding.titleTxt.setTextColor(context.getResources().getColor(R.color.white));
        if(selectedPosition ==position){
            holder.binding.pic.setBackgroundResource(0);
            holder.binding.mainLayout.setBackgroundResource(R.drawable.purple_bg);
            holder.binding.titleTxt.setVisibility(View.VISIBLE);
        }else{
            holder.binding.pic.setBackgroundResource(R.drawable.gray_bg);
            holder.binding.mainLayout.setBackgroundResource(0);
            holder.binding.titleTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CategoryViewholder extends RecyclerView.ViewHolder {
        private final ViewholderCategoryBinding binding;

        public CategoryViewholder(ViewholderCategoryBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
