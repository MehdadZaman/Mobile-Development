/***
 * This class is the activity for the signup page
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignUpPage extends AppCompatActivity {

    /***
     * Firebase and Firestore access objects
     */
    FirebaseFirestore db;
    private FirebaseAuth mAuth;

    /***
     * Screen view components
     */
    private EditText name;
    private EditText email;
    private EditText password;

    private String nameStr;
    private String emailStr;
    private String passwordStr;

    private TextView invalidSignUp;
    private Button signUp;


    /***
     * Starts the activity on the screen
     * @param savedInstanceState The previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        name = findViewById(R.id.nameEnterSignUp);
        email = findViewById(R.id.emailEnterSignUp);
        password = findViewById(R.id.passwordEnterSignUp);

        invalidSignUp = findViewById(R.id.invalidSignUp);

        signUp = findViewById(R.id.signUpButtonSignUp);

        mAuth = FirebaseAuth.getInstance();

        //FireStore
        db = FirebaseFirestore.getInstance();
    }

    /***
     * Signup acquires user input
     * @param v button click
     */
    public void signUpClick(View v)
    {
        nameStr = name.getText().toString();
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
        if(!validateForm())
        {
            invalidSignUp.setText("Failed to send verification Email");
            invalidSignUp.setVisibility(View.VISIBLE);
            return;
        }
        createAccount(emailStr, passwordStr);
        mAuth.signOut();
    }

    /***
     * Back to login page
     *
     * @param v button click
     */
    public void backToLoginClick(View v)
    {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }


    /***
     * Creates account on firebase
     *
     * @param email user email
     * @param password user password
     */
    public void createAccount(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendEmailVerification();
                        } else {
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthUserCollisionException fauce)
                            {
                                invalidSignUp.setText("There already exists an account with this email");
                            }
                            catch (FirebaseAuthWeakPasswordException faice)
                            {
                                invalidSignUp.setText("Password should be more than six characters and use a mix of alphabetic and numeric characters");
                            }
                            catch (Exception e)
                            {
                                invalidSignUp.setText("Failed to send verification Email");
                            }
                            invalidSignUp.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /***
     * Sends email if email is valid
     */
    public void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            invalidSignUp.setText("Email Sent. Press back to login when account is verified");
                            invalidSignUp.setTextColor(Color.GREEN);
                            invalidSignUp.setVisibility(View.VISIBLE);
                            signUp.setEnabled(false);
                            //FireStore
                            addUserToDataBase();
                            mAuth.signOut();
                        } else {
                            mAuth.getCurrentUser().delete();
                            invalidSignUp.setText("Failed to send verification Email");
                            invalidSignUp.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /***
     * Handles invalid user input
     * @return boolean on whether or not the input is invalid
     */
    public boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(nameStr)) {
            valid = false;
        }

        if (TextUtils.isEmpty(emailStr)) {
            valid = false;
        }
        if (TextUtils.isEmpty(passwordStr)) {
            valid = false;
        }
        return valid;
    }

    /***
     * Adds the user to firebase
     */
    public void addUserToDataBase()
    {
        Map<String, Object> userData = new HashMap<>();

        userData.put("Email", emailStr);
        userData.put("Name", nameStr);
        userData.put("Age", 30);
        userData.put("Weight", 150);
        userData.put("Height", 65);
        userData.put("Gender", "Other");
        userData.put("First Login", true);

        //{Calorie, Fat, Fiber, Sodium, Protein}
        ArrayList<Integer> maxIntakes = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        ArrayList<Integer> currentDailyIntakes = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));

        HashMap<String, ArrayList<Long>> historicalRecordsFireBase = new HashMap<>();
        ArrayList<Long> historicIntakes = new ArrayList<>();
        historicIntakes.add((long)100);
        historicIntakes.add((long)10);
        historicIntakes.add((long)10);
        historicIntakes.add((long)10);
        historicIntakes.add((long)10);

        historicalRecordsFireBase.put("06/27/2020", historicIntakes);

        userData.put("Max Intakes", maxIntakes);
        userData.put("Current Daily Intakes", currentDailyIntakes);
        userData.put("HistoricalIntakes", historicalRecordsFireBase);

        db.collection("users").document(mAuth.getCurrentUser().getUid()).set(userData);
    }
}