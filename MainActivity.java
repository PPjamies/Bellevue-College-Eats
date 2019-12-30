package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText editTextFoodInfo;
    EditText editTextLocationInfo;
    Button buttonAddInfo;
    Button buttonStudent;

    DatabaseReference databaseFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseFood = FirebaseDatabase.getInstance().getReference("food");

        editTextFoodInfo = (EditText) findViewById(R.id.foodInfo);
        editTextLocationInfo = (EditText) findViewById(R.id.locationInfo);
        buttonAddInfo = (Button) findViewById(R.id.buttonAddInfo);
        buttonStudent = (Button) findViewById(R.id.buttonStudent);

        buttonAddInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInfo();
                // Send all users notification. New.
                // Maybe just need to send notifications when triggered.
                // Question: Can this update all users with a notification?

                String message = "This is a notification demo.";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        MainActivity.this
                )
                        .setSmallIcon(R.drawable.ic_message)
                        .setContentTitle("New Notification")
                        .setContentText(message)
                        .setAutoCancel(true);

                Intent intent = new Intent(MainActivity.this, StudentView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message", message);

                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(
                        Context.NOTIFICATION_SERVICE
                );
                notificationManager.notify(0, builder.build());



                /* This is to keep track of any changes in the database.

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference reference = firebaseDatabase.getReference();
                reference.child("food")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                                for (DataSnapshot child: children){
                                    Food foodDTO = child.getValue(Food.class);
                                    // change later
                                    Toast.makeText(MainActivity.this, "Data: " + foodDTO.toString(), Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                 */

            }
        });

        buttonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent myIntent = new Intent(MainActivity.this, TestActivity.class);
                // MainActivity.this.startActivity(myIntent);


                // Student View - Able to view information about leftover
                Intent myIntent = new Intent(MainActivity.this, StudentView.class);
                myIntent.putExtra("key", true);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    private void addInfo(){
        String foodInfo = editTextFoodInfo.getText().toString().trim();
        String locationInfo = editTextLocationInfo.getText().toString().trim();

        if (!(TextUtils.isEmpty(foodInfo) || TextUtils.isEmpty(locationInfo))){
            //Store data into firebase database
            String id = databaseFood.push().getKey();
            Food food = new Food(id, foodInfo, locationInfo);
            databaseFood.child(id).setValue(food);
            Toast.makeText(this, "Information added", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Please describe both the food and the location", Toast.LENGTH_LONG).show();
        }
    }

}
