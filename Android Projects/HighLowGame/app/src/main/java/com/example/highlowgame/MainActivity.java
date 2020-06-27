package com.example.highlowgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText answer;
    private TextView tooHighOrLow;
    private TextView numberOfAttempts;
    private TextView playAgain;

    private int totalAttempts;
    private int numAttempts;

    private int randomNumber;
    private int currentGuess;

    Button guessButton;
    Button playAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answer = findViewById(R.id.answer_edit_text);
        tooHighOrLow = findViewById(R.id.guess_text);
        numberOfAttempts = findViewById(R.id.attempts_text);
        playAgain = findViewById(R.id.play_again);

        guessButton = findViewById(R.id.guess_button);
        playAgainButton = findViewById(R.id.play_again_button);

        numAttempts = 0;
        totalAttempts = 10;
        randomNumber = (int)(Math.random() * 101);
    }

    public void guess(View v)
    {
        try
        {
            String answer_text = answer.getText().toString();

            if (TextUtils.isEmpty(answer_text))
            {
                tooHighOrLow.setVisibility(View.VISIBLE);
                tooHighOrLow.setText("No valid number has been inputted.");
                tooHighOrLow.setTextColor(Color.RED);
                return;
            }

            currentGuess = Integer.parseInt(answer_text);

            if (currentGuess < randomNumber)
            {
                tooHighOrLow.setVisibility(View.VISIBLE);
                tooHighOrLow.setText("Your guess is too low.");
                tooHighOrLow.setTextColor(Color.RED);

                numAttempts++;

                if(numAttempts >= totalAttempts)
                {
                    tooHighOrLow.setVisibility(View.VISIBLE);
                    tooHighOrLow.setText("Your guess is too low and you have reached the maximum number of attempts.");
                    guessButton.setEnabled(false);
                    playAgain.setVisibility(View.VISIBLE);
                    playAgainButton.setEnabled(true);
                }
            }
            else if (currentGuess > randomNumber)
            {
                tooHighOrLow.setVisibility(View.VISIBLE);
                tooHighOrLow.setText("Your guess is too high.");
                tooHighOrLow.setTextColor(Color.RED);

                numAttempts++;

                if(numAttempts >= totalAttempts)
                {
                    tooHighOrLow.setVisibility(View.VISIBLE);
                    tooHighOrLow.setText("Your guess is too high and you have reached the maximum number of attempts.");
                    guessButton.setEnabled(false);
                    playAgain.setVisibility(View.VISIBLE);
                    playAgainButton.setEnabled(true);
                }
            }
            else
            {
                tooHighOrLow.setVisibility(View.VISIBLE);
                tooHighOrLow.setText("Your guess is correct!");
                tooHighOrLow.setTextColor(Color.GREEN);

                numAttempts++;

                guessButton.setEnabled(false);
                playAgain.setVisibility(View.VISIBLE);
                playAgainButton.setEnabled(true);
            }

            numberOfAttempts.setText("Number of tries: " + numAttempts);
        }
        catch (Exception e){}
    }

    public void playAgain(View v)
    {
        numAttempts = 0;
        totalAttempts = 10;
        randomNumber = (int)(Math.random() * 101);

        guessButton.setEnabled(true);
        playAgainButton.setEnabled(false);

        playAgain.setVisibility(View.INVISIBLE);
        tooHighOrLow.setVisibility(View.INVISIBLE);

        numberOfAttempts.setText("Number of tries: " + numAttempts);
    }
}