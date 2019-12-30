package com.example.firebasedemo;

public class Food {
    String foodId;
    String foodInfo;
    String locationInfo;

    public Food(){

    }

    public Food(String foodId, String foodInfo, String locationInfo){
        this.foodId = foodId;
        this.foodInfo = foodInfo;
        this.locationInfo = locationInfo;
    }

    public String getFoodId(){
        return foodId;
    }

    public String getFoodInfo(){
        return foodInfo;
    }

    public String getLocationInfo(){
        return locationInfo;
    }

    // Recently added to send notifications when data is changed.
    public String toString(){
        return foodInfo + " " + locationInfo;
    }

}
