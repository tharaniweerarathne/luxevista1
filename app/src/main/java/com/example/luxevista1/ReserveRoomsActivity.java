package com.example.luxevista1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReserveRoomsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView startDateTextView, endDateTextView;
    private int startYear, startMonth, startDay;
    private int endYear, endMonth, endDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_rooms);

        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);

        Calendar calendar = Calendar.getInstance();

        startYear = calendar.get(Calendar.YEAR);
        startMonth = calendar.get(Calendar.MONTH);
        startDay = calendar.get(Calendar.DAY_OF_MONTH);


        ListView listView = findViewById(R.id.roomListView);
        dbHelper = new DatabaseHelper(this);
        List<Room> rooms = dbHelper.getBookings();
        RoomAdapter adapter = new RoomAdapter(this, rooms, false);
        listView.setAdapter(adapter);

        startDateTextView.setOnClickListener(v -> {
            DatePickerDialog startDatePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        startYear = year;
                        startMonth = month;
                        startDay = dayOfMonth;
                        startDateTextView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                        showEndDatePicker(listView);
                    },
                    startYear, startMonth, startDay);
            startDatePickerDialog.show();
        });

        endDateTextView.setOnClickListener(v -> {
            DatePickerDialog endDatePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        endYear = year;
                        endMonth = month;
                        endDay = dayOfMonth;
                        endDateTextView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    },
                    startYear, startMonth, startDay);

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.set(startYear, startMonth, startDay);
            endDatePickerDialog.getDatePicker().setMinDate(calendarEnd.getTimeInMillis());

            endDatePickerDialog.show();
        });
    }

    private void showEndDatePicker(ListView listView) {

        DatePickerDialog endDatePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    endYear = year;
                    endMonth = month;
                    endDay = dayOfMonth;
                    endDateTextView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                    Date startDate = new Date(startYear - 1900, startMonth, startDay);
                    Date endDate = new Date(endYear - 1900, endMonth, endDay);
                    createBookingItems(listView, startDate, endDate);

                },
                startYear, startMonth, startDay);

        Calendar calendar = Calendar.getInstance();
        calendar.set(startYear, startMonth, startDay);
        endDatePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        endDatePickerDialog.show();
    }

    private void createBookingItems(ListView listView, Date startDate, Date endDate){

        List<Room> rooms = dbHelper.getRoomDetailsByAvailability(startDate.getTime(), endDate.getTime());
        RoomAdapter adapter = new RoomAdapter(this, rooms, false);
        listView.setAdapter(adapter);
    }
}
