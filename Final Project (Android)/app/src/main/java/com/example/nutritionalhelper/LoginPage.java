/***
 * This class is the activity for the loginpage
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
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.Map;

public class LoginPage extends AppCompatActivity {

    /***
     * Firebase and Firestore access objects
     */
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    /***
     * Screen view components
     */
    private EditText email;
    private EditText password;

    private TextView incorrectCredentials;

    private String emailStr;
    private String passwordStr;

    /***
     * Starts the activity on the screen
     * @param savedInstanceState The previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        email = findViewById(R.id.emailEnter);
        password = findViewById(R.id.passwordEnter);
        incorrectCredentials = findViewById(R.id.incorrectCredentials);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        resumeSession();
    }

    /***
     * Takes user to homepage if already logged in
     */
    public void resumeSession() {
        try {
            String s = mAuth.getCurrentUser().getUid();
            if (mAuth.getCurrentUser() != null) {
                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
            }
        }
        catch (Exception e)
        {}
    }

    /***
     * Logs in user after reading email and password
     * @param v button click
     */
    public void logInClick(View v)
    {
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
        if(!validateForm())
        {
            return;
        }
        loginUser(emailStr, passwordStr);
    }

    /***
     * Checks firebase for user credentials
     *
     * @param email user email
     * @param password user password
     */
    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(mAuth.getCurrentUser().isEmailVerified()) {
                                updateUI(user);
                                user.getIdToken(true);
                            }
                            else {
                                mAuth.signOut();
                                incorrectCredentials.setText("Email not verified");
                                updateUI(null);
                            }
                        } else {

                            updateUI(null);
                        }
                    }
                });
    }

    /***
     * Takes user to signup page
     * @param v button click
     */
    public void signUpHereClick(View v)
    {
        Intent intent = new Intent(this, SignUpPage.class);
        startActivity(intent);
    }

    /***
     * Makes sure the edittexts are not empty
     *
     * @return boolean on whether or not email and passowrd fields are empty
     */
    public boolean validateForm() {
        boolean valid = true;
        if (TextUtils.isEmpty(emailStr)) {
            updateUI(null);
            valid = false;
        }
        if (TextUtils.isEmpty(passwordStr)) {
            updateUI(null);
            valid = false;
        }
        return valid;
    }

    /***
     * Takes user to reset password page
     * @param v button click
     */
    public void resetPasswordClick(View v)
    {
        Intent intent = new Intent(this, ResetPasswordPage.class);
        startActivity(intent);
    }

    /***
     * Takes user to verify account page
     * @param v button click
     */
    public void verifyAccountClick(View v)
    {
        Intent intent = new Intent(this, VerifyEmailPage.class);
        startActivity(intent);
    }

    /***
     * Acquires user info to login and send to homepage
     *
     * @param currentUser the current logged in user
     */
    public void updateUI(FirebaseUser currentUser) {
        if (currentUser != null)
        {
            final DocumentReference docRef = db.collection("users").document(currentUser.getUid());
            // Source can be CACHE, SERVER, or DEFAULT.
            Source source = Source.SERVER;
            // Get the document, forcing the SDK to use the offline cache
            docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        Map<String, Object> dataMap = document.getData();
                        boolean firstLogin = (boolean)dataMap.get("First Login");
                        if(firstLogin)
                        {
                            DocumentReference df = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            df.update("First Login", false);
                            Intent intent = new Intent(LoginPage.this, SettingsPage.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(LoginPage.this, HomePage.class);
                            startActivity(intent);
                        }

                        Toast.makeText(LoginPage.this, "Signed in!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            incorrectCredentials.setVisibility(View.VISIBLE);
        }
    }
}