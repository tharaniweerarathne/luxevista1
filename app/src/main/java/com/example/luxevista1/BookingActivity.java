package com.example.luxevista1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class BookingActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        TextView roomDetails = findViewById(R.id.roomDetails);
        Button confirmBookingButton = findViewById(R.id.confirmBookingButton);

        int roomId = getIntent().getIntExtra("roomId", 0);
        String roomType = getIntent().getStringExtra("roomType");
        String roomCategory = getIntent().getStringExtra("roomCategory");
        String roomPrice = getIntent().getStringExtra("roomPrice");
        String roomDescription = getIntent().getStringExtra("roomDescription");

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        roomDetails.setText("Type: " + roomType + "\nCategory: " + roomCategory +
                "\nPrice: " + roomPrice + "\nDescription: " + roomDescription);

        dbHelper = new DatabaseHelper(this);

        confirmBookingButton.setOnClickListener((View v) -> {

            Date startDate = new Date();
            Date endDate = new Date();
            CheckBox spaService = findViewById(R.id.spaService_edit);
            CheckBox service1 = findViewById(R.id.service1_edit);
            CheckBox service2 = findViewById(R.id.service2_edit);

            boolean isSpaServiceSelected = spaService.isChecked();
            boolean isService1Selected = service1.isChecked();
            boolean isService2Selected = service2.isChecked();

            boolean isInserted = dbHelper.addBooking(userId, roomId, startDate, endDate,isSpaServiceSelected,isService1Selected,isService2Selected);
            if (isInserted) {
                Toast.makeText(BookingActivity.this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(BookingActivity.this, "Booking Failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
