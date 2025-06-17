package com.travel.ticket.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.travel.ticket.app.Adapter.CategoryAdapter;
import com.travel.ticket.app.Adapter.PopularAdapter;
import com.travel.ticket.app.Adapter.RecommendedAdapter;
import com.travel.ticket.app.Adapter.SliderAdapter;
import com.travel.ticket.app.Domain.Category;
import com.travel.ticket.app.Domain.Item;
import com.travel.ticket.app.Domain.Location;
import com.travel.ticket.app.Domain.SliderItems;
import com.travel.ticket.app.R;
import com.travel.ticket.app.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        database =FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initLocations();
        initBanners();
        initCategory();
        initPopular();
        initRecommended();
    }

    private void initRecommended() {
        DatabaseReference myRef = database.getReference("Item");
        ArrayList<Item> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(Item.class));
                    }
                    if(!list.isEmpty()){
                        binding.recyclerViewRecommended.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        RecyclerView.Adapter adapter = new RecommendedAdapter(list);
                        binding.recyclerViewRecommended.setAdapter(adapter);
                    }
                    binding.progressBarRecommended.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    private void initPopular() {
        DatabaseReference myRef = database.getReference("Popular");
        ArrayList<Item> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(Item.class));
                    }
                    if(!list.isEmpty()){
                        binding.recyclerViewPopular.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        RecyclerView.Adapter adapter = new PopularAdapter(list);
                        binding.recyclerViewPopular.setAdapter(adapter);
                    }
                    binding.progressBarPopular.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");
        ArrayList<Category> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(Category.class));
                    }
                if(!list.isEmpty()){
                    binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                    RecyclerView.Adapter adapter = new CategoryAdapter(list);
                    binding.recyclerViewCategory.setAdapter(adapter);
                }
                binding.progressBarCategory.setVisibility(View.GONE);
            }
          }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    private void initLocations() {
        DatabaseReference myRef = database.getReference("Location");
        ArrayList<Location> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(Location.class));
                    }
                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(MainActivity.this,R.layout.sp_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.locationSp.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    private void banners(ArrayList<SliderItems> items){
        binding.viewPager2.setAdapter(new SliderAdapter(items,binding.viewPager2));
        binding.viewPager2.setClipToPadding (false);
        binding.viewPager2.setClipChildren (false);
        binding.viewPager2.setOffscreenPageLimit(3);
        binding.viewPager2.getChildAt(0).setOverScrollMode (RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer (new MarginPageTransformer(40));
        binding.viewPager2.setPageTransformer (compositePageTransformer);
    }
    private void initBanners() {
        DatabaseReference myRef = database.getReference ("Banner");
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItems> items = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange (@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(SliderItems.class));
                    }
                    banners(items);
                    binding.progressBarBanner.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){}
        });
    }
}