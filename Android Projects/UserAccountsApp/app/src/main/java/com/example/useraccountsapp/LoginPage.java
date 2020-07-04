package com.example.useraccountsapp;

/* Name: Mehdad Zaman
    SBU ID: 112323211*/

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;

    TextView failedLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        usernameText = findViewById(R.id.username_login);
        passwordText = findViewById(R.id.password_login);

        failedLogin = findViewById(R.id.failedLoginText);
    }

    public void loginUser(View view)
    {
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if((TextUtils.isEmpty(username)) || (TextUtils.isEmpty(password)))
        {
            Toast.makeText(this, "One input field is empty!", Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);

        boolean savedUsername = sharedPreferences.getBoolean(username, false);

        if(savedUsername == false)
        {
            failedLogin.setText("INVALID USERNAME");
            failedLogin.setVisibility(View.VISIBLE);
            return;
        }

        String savedPassword = sharedPreferences.getString(username + "PASSWORD", "");

        if(!password.equals(savedPassword))
        {
            failedLogin.setText("INVALID PASSWORD");
            failedLogin.setVisibility(View.VISIBLE);
            return;
        }

        String savedFullName = sharedPreferences.getString(username + "FULLNAME", "");

        Intent intent = new Intent(this, MainActivity.class);

        String[] userValues = {username, password, savedFullName};

        intent.putExtra("userIntentValues", userValues);
        startActivity(intent);
    }

    public void newUserLoginPageClick(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void loginUserLoginPageClick(View view)
    {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
}