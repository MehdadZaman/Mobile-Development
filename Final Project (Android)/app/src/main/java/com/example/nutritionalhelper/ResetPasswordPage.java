/***
 * This class is the activity for the ResetPasswordPage
 *
 * @author Mehdad Zaman
 * @id 112323211
 * Final Project
 * CSE 390 Section 2
 */
package com.example.nutritionalhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordPage extends AppCompatActivity {

    /***
     * Screen view components
     */
    TextView messageReset;
    Button resetPassword;
    EditText email;

    String emailStr;

    /***
     * Starts the activity on the screen
     * @param savedInstanceState The previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_page);
        messageReset = findViewById(R.id.messageResetPassword);
        resetPassword = findViewById(R.id.sendResetPassword);
        email = findViewById(R.id.emailEnterPasswordReset);
    }


    /***
     * Back to login page
     *
     * @param v button click
     */
    public void logInBackReset(View v)
    {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

    /***
     * Send email to reset password
     *
     * @param v button click
     */
    public void sendReset(View v)
    {
        emailStr = email.getText().toString();
        if(TextUtils.isEmpty(emailStr))
        {
            messageReset.setVisibility(View.VISIBLE);
            return;
        }
        sendPasswordResetEmail();
    }

    /***
     * Send reset email password from firebase
     */
    public void sendPasswordResetEmail() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(emailStr)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            messageReset.setText("Email for password reset sent");
                            messageReset.setVisibility(View.VISIBLE);
                            messageReset.setTextColor(Color.GREEN);
                            resetPassword.setEnabled(false);
                        }
                        else {
                            messageReset.setText("Email could not be sent");
                            messageReset.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}