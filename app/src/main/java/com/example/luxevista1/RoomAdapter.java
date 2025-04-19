package com.example.luxevista1;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RoomAdapter extends BaseAdapter {
    private Context context;
    private List<Room> roomList;

    private boolean isView;

    public RoomAdapter(Context context, List<Room> roomList, boolean isView) {
        this.context = context;
        this.roomList = roomList;
        this.isView = isView;
    }

    @Override
    public int getCount() {
        return roomList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int resource;
            if(isView){
                resource = R.layout.view_room_list_item;
            } else {
                resource = R.layout.room_list_item;
            }
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);

        Room room = roomList.get(position);

        ImageView roomImage = convertView.findViewById(R.id.roomImage);
        TextView roomName = convertView.findViewById(R.id.roomName);
        TextView roomCategory = convertView.findViewById(R.id.roomCategory);
        TextView roomPrice = convertView.findViewById(R.id.roomPrice);
        TextView roomFeatures = convertView.findViewById(R.id.roomFeatures);
        Button bookRoomButton = convertView.findViewById(R.id.bookRoomButton);

        if(isView){
            CheckBox spaService = convertView.findViewById(R.id.serviceView);
            CheckBox service1 = convertView.findViewById(R.id.serviceTowView);
            CheckBox service2 = convertView.findViewById(R.id.serviceThreeView);

            spaService.setChecked(room.getIsServiceOne());
            service1.setChecked(room.getIsServiceTwo());
            service2.setChecked(room.getIsServiceThree());
        }

        roomImage.setImageResource(room.getImageId());
        roomName.setText(room.getRoomDescription());
        roomCategory.setText(room.getRoomType());
        roomPrice.setText(room.getRoomPrice());
        roomFeatures.setText(room.getFeatures());

        bookRoomButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(context, BookingActivity.class);
            intent.putExtra("roomId", room.getId());
            intent.putExtra("roomType", room.getRoomType());
            intent.putExtra("roomCategory", room.getRoomCategory());
            intent.putExtra("roomPrice", room.getRoomPrice());
            intent.putExtra("roomDescription", room.getRoomDescription());
            intent.putExtra("userId", userId);
            intent.putExtra("imageId", room.getImageId());

            startActivity(context, intent, null);
        });

        return convertView;
    }


}
