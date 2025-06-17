package com.travel.ticket.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.travel.ticket.app.Domain.Item;
import com.travel.ticket.app.R;
import com.travel.ticket.app.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    private Item object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        if (object != null){
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText("$"+object.getPrice());
        binding.backBtn.setOnClickListener(v->finish());
        binding.bedTxt.setText(""+object.getBed());
        binding.durationTxt.setText(object.getDuration());
        binding.distanceTxt.setText(object.getDistance());
        binding.descriptionTxt.setText(object.getDescription());
        binding.addressTxt.setText(object.getAddress());
        binding.ratingBar.setRating ((float)object.getScore());
        binding.ratingTxt.setText(object.getScore()+" Rating");

        Glide.with(DetailActivity.this)
                .load(object.getPic())
                .into(binding.pic);
    }
        binding.addToCartBtn.setOnClickListener(v ->{
            Intent intent = new Intent(DetailActivity.this,TicketActivity.class);
            intent.putExtra("object",object);
            startActivity(intent);
        });
    }

    private void getIntentExtra() {
        object =(Item)getIntent().getSerializableExtra("object");
    }
}