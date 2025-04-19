package com.example.luxevista1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity {

    private ListView listViewTasks;

    private Button btnLogout;

    private Button morePromotionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        listViewTasks = findViewById(R.id.listViewTasks);
        btnLogout = findViewById(R.id.btnLogout);
        morePromotionBtn= findViewById(R.id.morePromotionBtn);

        String[] tasks = {"Reserve Room with services", "My Bookings", "Promotions", "Local Attractions"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listViewTasks.setAdapter(adapter);

        int userId = getIntent().getIntExtra("userId", 0);

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTask = tasks[position];
                Toast.makeText(DashboardActivity.this, "You selected: "+ selectedTask, Toast.LENGTH_SHORT).show();

                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(DashboardActivity.this, ReserveRoomsActivity.class);
                        break;
                    case 1:
                        intent = new Intent(DashboardActivity.this, ViewBookingsActivity.class);
                        break;
                    case 2:
                        intent = new Intent(DashboardActivity.this, PromotionsActivity.class);
                        Toast.makeText(DashboardActivity.this, "Holiday Escape: Exclusive discounts for memorable family adventures!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(DashboardActivity.this, "Holiday Special&quot;, &quot;Exclusive holiday packages for families. give a diffrent sentence", Toast.LENGTH_SHORT).show();
                        Toast.makeText(DashboardActivity.this, "Midweek Magic: Stay between Monday and Thursday and receive 20% off!", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        intent = new Intent(DashboardActivity.this, LocalAttractionsActivity.class);
                        break;
                }
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(DashboardActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            }


        });
        morePromotionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, PromotionsActivity.class);
                startActivity(intent);
                Toast.makeText(DashboardActivity.this, "Holiday Escape: Exclusive discounts for memorable family adventures!", Toast.LENGTH_SHORT).show();
                Toast.makeText(DashboardActivity.this, "Holiday Special&quot;, &quot;Exclusive holiday packages for families. give a diffrent sentence", Toast.LENGTH_SHORT).show();
                Toast.makeText(DashboardActivity.this, "Midweek Magic: Stay between Monday and Thursday and receive 20% off!", Toast.LENGTH_SHORT).show();
            }


        });
    }
}
