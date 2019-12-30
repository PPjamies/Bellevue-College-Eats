package com.example.firebasedemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FoodList extends ArrayAdapter<Food> {

    private Activity context;
    private List<Food> foodList;

    public FoodList(Activity context, List<Food> foodList){
        super(context, R.layout.list_layout, foodList);
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewFood = (TextView) listViewItem.findViewById(R.id.textViewFood);
        TextView textViewLocation = (TextView) listViewItem.findViewById(R.id.textViewLocation);

        Food food = foodList.get(position);

        textViewFood.setText(food.getFoodInfo());
        textViewLocation.setText(food.getLocationInfo());

        return listViewItem;
    }
}
