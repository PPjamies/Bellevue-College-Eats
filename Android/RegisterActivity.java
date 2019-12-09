package com.example.bc_eats;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    public static EditText mPhoneNumber_EditText;
    public static EditText mPassword_EditText;
    private Button mRegister_Button;


    private static boolean isUser;
    public static Context mContext;
    public static Toast sInvalidPhoneNumber;
    public static Toast sSuccessfulRegistration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = getApplicationContext();

        sInvalidPhoneNumber = Toast.makeText(mContext,R.string.number_in_use,Toast.LENGTH_SHORT);
        sSuccessfulRegistration = Toast.makeText(mContext,R.string.successful_registration,Toast.LENGTH_SHORT);


        mRegister_Button = (Button)findViewById(R.id.register_button);
        mPhoneNumber_EditText = (EditText)findViewById(R.id.phone_number_et);
        mPassword_EditText = (EditText)findViewById(R.id.password_et);

        //program sign up button to collect information from all the text fields
        mRegister_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String phoneNumber = mPhoneNumber_EditText.getText().toString();
                String password = mPassword_EditText.getText().toString();
                String newUser = "@@" + phoneNumber + "@@" + password;

                //ServerMobile.sendMessageToServer(newUser);
            }
        });
    }

    public static void showInvalidPhoneNumber(){
        sInvalidPhoneNumber.show();
    }

    public static void setUserStatus(){
        refreshPage(mContext);
    }

    public static void refreshPage(Context context) {
        mPhoneNumber_EditText.setText("");
        mPassword_EditText.setText("");
        sSuccessfulRegistration.show();
    }
}
