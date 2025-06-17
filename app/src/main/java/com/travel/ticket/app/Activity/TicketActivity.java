package com.travel.ticket.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.travel.ticket.app.Domain.Item;
import com.travel.ticket.app.R;
import com.travel.ticket.app.databinding.ActivityTicketBinding;

public class TicketActivity extends AppCompatActivity {
    ActivityTicketBinding binding;
    private Item object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTicketBinding.inflate(getLayoutInflater());
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
            binding.durationTxt.setText(object.getDuration());
            binding.tourGuideNameTxt.setText(object.getTourGuideName());
            binding.timeTxt.setText(object.getTimeTour());
            binding.tourGuideTxt.setText(object.getDateTour());

            binding.callBtn.setOnClickListener(v ->{
                String phone = object.getTourGuidePhone();
                Intent intent =new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",phone,null));
                startActivity(intent);

            });
            binding.messageBtn.setOnClickListener(v ->{
                Intent sendIntent =new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("sms:"+object.getTourGuidePhone()));
                sendIntent.putExtra("sms_body","type your message");
                startActivity(sendIntent);
            });


            Glide.with(TicketActivity.this)
                    .load(object.getPic())
                    .into(binding.pic);
            Glide.with(TicketActivity.this)
                    .load(object.getTourGuidePic())
                    .into(binding.profile);
        }
        binding.backBtn.setOnClickListener(v->finish());
        binding.downloadTicketBtn.setOnClickListener(v ->{ });
    }

    private void getIntentExtra() {
        object =(Item)getIntent().getSerializableExtra("object");
    }
}