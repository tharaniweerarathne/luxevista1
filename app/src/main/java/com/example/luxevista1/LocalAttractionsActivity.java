package com.example.luxevista1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LocalAttractionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_local_attractions);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView attractionsListView = findViewById(R.id.localAttraction);

        String[] attractions = {
                "01. Cultural Villages - Immerse yourself in local traditions and crafts",
                "02. Water Sports - Surfing, Jet Skiing, and more",
                "03. Fine Dining - Explore gourmet seaside restaurants",
                "04. Boat Tours - Discover hidden lagoons",
                "05. Local Markets - Shop for handmade crafts",
                "06. Coral Reef Excursions - Explore vibrant underwater ecosystems",
                "07. Adventure Trails - Hiking along the coastline",
                "08. Fishing Charters - Experience deep-sea fishing with experts",
                "09. Beach Parties - Nightlife by the waves",
                "10. Local History - Guided tours of cultural sites"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                attractions
        );

        attractionsListView.setAdapter(adapter);
    }
}
