package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentView extends AppCompatActivity {

    ListView listViewFood;
    DatabaseReference databaseFood;
    List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);

        databaseFood = FirebaseDatabase.getInstance().getReference("food");

        listViewFood = (ListView) findViewById(R.id.listViewFood);
        foodList = new ArrayList<>();
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                foodList.clear();

                for (DataSnapshot foodSnapshot: dataSnapshot.getChildren()){
                    Food food = foodSnapshot.getValue(Food.class);
                    foodList.add(food);
                }

                FoodList adapter = new FoodList(StudentView.this, foodList);
                listViewFood.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
