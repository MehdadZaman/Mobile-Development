package com.example.useraccountsapp;

/* Name: Mehdad Zaman
    SBU ID: 112323211*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    EditText fullnameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameText = findViewById(R.id.username_adduser);
        passwordText = findViewById(R.id.password_adduser);
        fullnameText = findViewById(R.id.fullname_adduser);

        String[] passedValues = getIntent().getStringArrayExtra("userIntentValues");

        if(passedValues == null)
        {
            return;
        }

        usernameText.setText(passedValues[0]);
        passwordText.setText(passedValues[1]);
        fullnameText.setText(passedValues[2]);
    }

    public void saveUser(View view)
    {
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String fullname = fullnameText.getText().toString();

        if((TextUtils.isEmpty(username)) || (TextUtils.isEmpty(password)) || (TextUtils.isEmpty(fullname)))
        {
            Toast.makeText(this, "One input field is empty!", Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(username, true);
        editor.putString(username + "PASSWORD", password);
        editor.putString(username + "FULLNAME", fullname);
        editor.apply();

        Toast.makeText(this, "User saved!", Toast.LENGTH_LONG).show();
    }

    public void newUserAddUserPageClick(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void loginUserAddUserPageClick(View view)
    {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
}