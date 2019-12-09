package com.example.bc_eats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button mLoginButton;
    private Button mRegisterButton;
    private EditText mPhoneNumber;
    private EditText mPassword;

    public static Context mContext;

    public static Toast sInvalidNumberToast;
    public static Toast sInvalidPasswordToast;
    public static Toast sInvalidCredentialsToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();

        //set toasts
        sInvalidNumberToast = Toast.makeText(mContext,R.string.invalid_number,Toast.LENGTH_SHORT);
        sInvalidPasswordToast = Toast.makeText(mContext,R.string.invalid_pw,Toast.LENGTH_SHORT);
        sInvalidCredentialsToast = Toast.makeText(mContext,R.string.invalid_cred,Toast.LENGTH_SHORT);

        //ServerMobile.communicate();

        mPhoneNumber = (EditText) findViewById(R.id.phone_number_et);
        mPassword = (EditText) findViewById(R.id.password_et);

        mLoginButton = (Button)findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String phoneNumber = mPhoneNumber.getText().toString();
                String password = mPassword.getText().toString();
                String credentials = "%%" + phoneNumber + "%%" + password;

                //ServerMobile.sendMessageToServer(credentials);
            }
        });

        mRegisterButton = (Button)findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void showInvalidNumber(){
        sInvalidNumberToast.show();
    }

    public static void showInvalidPassword(){
        sInvalidPasswordToast.show();
    }

    public static void showInvalidCredentials(){
        sInvalidCredentialsToast.show();
    }

    public static void showValidCredentials(){
        proceedToApp(mContext);
    }
    public static void proceedToApp(Context context) {
        Intent intent = new Intent(context, Application.class);
        context.startActivity(intent);
    }

}
