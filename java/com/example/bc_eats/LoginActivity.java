package com.example.bc_eats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private Button mLoginButton;
    private EditText mPhoneNumber_EditText;

    public static Context mContext;
    public static String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        mPhoneNumber_EditText = (EditText) findViewById(R.id.phone_et);
        mLoginButton = (Button)findViewById(R.id.login_bt);

        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPhone = mPhoneNumber_EditText.getText().toString().trim();
                if(mPhone.isEmpty())
                {
                    mPhoneNumber_EditText.setError("please enter phone number");
                    mPhoneNumber_EditText.requestFocus();
                }
                else
                {
                    //Log.d("LOOOOOOOOG","this person is not a current user");
                    Intent intent = new Intent(mContext,PhoneAuthenticationActivity.class);
                    intent.putExtra("mPhone",mPhone);
                    startActivity(intent);
                }
            }
        });
    }
}

