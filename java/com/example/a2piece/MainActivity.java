package com.example.a2piece;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize fragment and fragment manager
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            Log.d("here", "got before FoodListFragment");
            fragment = new FoodListFragment();
            Log.d("here", "got after FoodListFragment");
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
            Log.d("here", "after Transaction");
        }
    }
}


/**********************************MOBILE TO/FRO SERVER METHODS********************************************************************************************/

/**********************************************************************************************************************************************/
