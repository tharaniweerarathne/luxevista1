package com.example.luxevista1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "LuxeVista.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";

    private static final String TABLE_ROOMS = "Rooms";
    private static final String TABLE_BOOKINGS = "bookings";

    // Users Table Columns
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    // Bookings Table Columns
    private static final String COLUMN_BOOKING_ID = "id";
    private static final String COLUMN_ROOM_TYPE = "room_type";
    private static final String COLUMN_ROOM_CATEGORY = "room_category";
    private static final String COLUMN_ROOM_DESCRIPTION = "room_description";
    private static final String COLUMN_ROOM_PRICE = "room_price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_EMAIL + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Rooms Table
        String CREATE_ROOMS_TABLE = "CREATE TABLE " + TABLE_ROOMS + "("
                + COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ROOM_TYPE + " TEXT, "
                + COLUMN_ROOM_CATEGORY + " TEXT, "
                + COLUMN_ROOM_DESCRIPTION + " TEXT, "
                + "image_id" + " INTEGER, "
                + COLUMN_ROOM_PRICE + " TEXT)";
        db.execSQL(CREATE_ROOMS_TABLE);

        // Create Bookings Table
        String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + "Bookings" + "("
                + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "room_id" + " INTEGER, "
                + "user_id" + " INTEGER, "
                + "start_date" + " INTEGER, "
                + "service1" + " INTEGER, "
                + "service2" + " INTEGER, "
                + "service3" + " INTEGER, "
                + "end_date" + " INTEGER)";

        db.execSQL(CREATE_BOOKINGS_TABLE);

        insertDemoRoom(db,"Ocean View Suite", "Standard", "King-size bed, ocean view balcony, luxury amenities","LKR 81,250 per night", R.drawable.image_jpg);
        insertDemoRoom(db, "Ocean View Suite", "Premium",  "King-size bed, larger balcony, upgraded amenities, oceanfront view", "King-size bed, larger balcony, upgraded amenities, oceanfront view", R.drawable.image2);
        insertDemoRoom(db, "Ocean View Suite", "Executive", "Spacious living area, private terrace with ocean view, high-end amenities, and concierge service", "LKR 113,750 per night", R.drawable.img33);
        insertDemoRoom(db, "Deluxe Room", "Standard", "Queen-size bed, standard amenities, city or garden view", "LKR 162,500 per night", R.drawable.img44);
        insertDemoRoom(db, "Deluxe Room", "Premium", "King-size bed, enhanced décor, garden or partial ocean view", "LKR 65,000 per night", R.drawable.img55);
        insertDemoRoom(db, "Deluxe Room", "Executive","King-size bed, work desk, ocean view, and luxury amenities", "LKR 89,375 per night", R.drawable.img66);
    }

    public void insertDemoRoom(SQLiteDatabase db, String roomType, String roomCategory, String roomDescription, String roomPrice, int imageId) {

        // Use ContentValues for better readability and SQL injection prevention
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_TYPE, roomType);
        values.put(COLUMN_ROOM_CATEGORY, roomCategory);
        values.put(COLUMN_ROOM_DESCRIPTION, roomDescription);
        values.put(COLUMN_ROOM_PRICE, roomPrice);
        values.put("image_id", imageId);

        // Insert the new row into the database
        long result = db.insert(TABLE_ROOMS, null, values);

        if (result == -1) {
            Log.e("Database", "Failed to insert room");
        } else {
            Log.d("Database", "Room inserted successfully with ID: " + result);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "Bookings");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        onCreate(db);
    }

    // Add User to the Users Table
    public boolean addUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();

        return result != -1;
    }

    public List<Room> getBookings() {
        List<Room> result = new ArrayList<>();
        String selection = "";
        String[] selectionArgs = {};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ROOMS, null, selection, selectionArgs, null, null, null);
        Room room = null;

        while (cursor.moveToNext()) {
            int roomId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String roomType = cursor.getString(cursor.getColumnIndexOrThrow("room_type"));
            String roomCategory = cursor.getString(cursor.getColumnIndexOrThrow("room_category"));
            String roomPrice = cursor.getString(cursor.getColumnIndexOrThrow("room_price"));
            String roomDescription = cursor.getString(cursor.getColumnIndexOrThrow("room_description"));
            int image_id = cursor.getInt(cursor.getColumnIndexOrThrow("image_id"));

            room = new Room(roomId, roomType, roomCategory, roomPrice, roomDescription, "", image_id,
                    false , false, false);
            result.add(room);
        }
        cursor.close();
        db.close();

        return result;

    }

    public List<Room> getRoomDetailsByUserId(int userId) {
        List<Room> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                "rooms.id, " +
                "rooms.room_type, " +
                "rooms.room_category, " +
                "rooms.room_description, " +
                "rooms.room_price, " +
                "rooms.image_id, " +
                "bookings.start_date, " +
                "bookings.end_date, " +
                "bookings.service1, " +
                "bookings.service2, " +
                "bookings.service3 " +
                "FROM bookings " +
                "INNER JOIN rooms ON bookings.room_id = rooms.id " +
                "WHERE bookings.user_id = ?";

        Cursor cursor =  db.rawQuery(query, new String[]{String.valueOf(userId)});

        while (cursor.moveToNext()) {
            int roomId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String roomType = cursor.getString(cursor.getColumnIndexOrThrow("room_type"));
            String roomCategory = cursor.getString(cursor.getColumnIndexOrThrow("room_category"));
            String roomPrice = cursor.getString(cursor.getColumnIndexOrThrow("room_price"));
            String roomDescription = cursor.getString(cursor.getColumnIndexOrThrow("room_description"));
            int image_id = cursor.getInt(cursor.getColumnIndexOrThrow("image_id"));

            int service1 = cursor.getInt(cursor.getColumnIndexOrThrow("service1"));
            int service2 = cursor.getInt(cursor.getColumnIndexOrThrow("service2"));
            int service3 = cursor.getInt(cursor.getColumnIndexOrThrow("service3"));

            result.add(new Room(roomId, roomType, roomCategory, roomPrice, roomDescription, "", image_id, service1 == 1 , service2 == 1, service3 == 1));
        }
        cursor.close();
        db.close();

        return result;
    }

    public List<Room> getRoomDetailsByAvailability(long searchStartDate, long searchEndDate) {
        List<Room> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " +
                "rooms.id, " +
                "rooms.room_type, " +
                "rooms.room_category, " +
                "rooms.room_description, " +
                "rooms.room_price, " +
                "rooms.image_id, " +
                "bookings.service1, " +
                "bookings.service2, " +
                "bookings.service3 " +
                "FROM rooms " +
                "LEFT JOIN bookings ON rooms.id = bookings.room_id " +
                "AND ((bookings.start_date <= ? AND bookings.end_date >= ?) OR " +
                "(bookings.start_date <= ? AND bookings.end_date >= ?) OR " +
                "(bookings.start_date >= ? AND bookings.end_date <= ?)) " +
                "WHERE bookings.room_id IS NULL";

        Cursor cursor = db.rawQuery(query, new String[]{
                String.valueOf(searchStartDate),
                String.valueOf(searchEndDate),
                String.valueOf(searchStartDate),
                String.valueOf(searchEndDate),
                String.valueOf(searchStartDate),
                String.valueOf(searchEndDate)
        });

        while (cursor.moveToNext()) {
            int roomId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String roomType = cursor.getString(cursor.getColumnIndexOrThrow("room_type"));
            String roomCategory = cursor.getString(cursor.getColumnIndexOrThrow("room_category"));
            String roomPrice = cursor.getString(cursor.getColumnIndexOrThrow("room_price"));
            String roomDescription = cursor.getString(cursor.getColumnIndexOrThrow("room_description"));
            int image_id = cursor.getInt(cursor.getColumnIndexOrThrow("image_id"));

            int service1 = cursor.getInt(cursor.getColumnIndexOrThrow("service1"));
            int service2 = cursor.getInt(cursor.getColumnIndexOrThrow("service2"));
            int service3 = cursor.getInt(cursor.getColumnIndexOrThrow("service3"));

            result.add(new Room(roomId, roomType, roomCategory, roomPrice, roomDescription, "", image_id, service1 == 1 , service2 == 1, service3 == 1));
        }
        cursor.close();
        db.close();

        return result;
    }

    public boolean addBooking(int userId, int roomId, Date startDate, Date endDate , boolean service1,boolean service2, boolean service3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("room_id", roomId);
        values.put("start_date", startDate.getTime());
        values.put("end_date", endDate.getTime());
        values.put("service1", service1);
        values.put("service2", service2);
        values.put("service3", service3);

        long result = db.insert(TABLE_BOOKINGS, null, values);
        db.close();

        return result != -1;
    }
}
