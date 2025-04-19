package com.example.luxevista1;

public class Room {
    private int id;
    private String roomType;
    private String roomCategory;
    private String roomPrice;
    private String roomDescription;
    private String features;

    private int imageId;

    private boolean serviceOne;

    private boolean serviceTwo;

    private boolean serviceThree;


    public Room(int id, String roomType, String roomCategory, String roomPrice, String roomDescription,
                String features, int imageId, boolean serviceOne, boolean serviceTwo, boolean serviceThree) {
        this.id = id;
        this.roomType = roomType;
        this.roomCategory = roomCategory;
        this.roomPrice = roomPrice;
        this.roomDescription = roomDescription;
        this.features = features;
        this.imageId = imageId;
        this.serviceOne = serviceOne;
        this.serviceTwo = serviceTwo;
        this.serviceThree = serviceThree;
    }


    public String getRoomType() {
        return roomType;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public String getFeatures() {
        return features;
    }

    public int getImageId() {
        return imageId;
    }

    public int getId() {
        return id;
    }

    public boolean getIsServiceOne() {
        return serviceOne;
    }

    public boolean getIsServiceTwo() {
        return serviceTwo;
    }

    public boolean getIsServiceThree() {
        return serviceThree;
    }

}
