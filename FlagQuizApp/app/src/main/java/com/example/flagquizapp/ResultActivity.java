package com.example.flagquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView totalCorrect, totalWrong, totalEmpty, successRate;
    Button buttonPlayAgain, buttonQuit;
    int correct, wrong, empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        totalCorrect = findViewById(R.id.textView_total_correct);
        totalWrong = findViewById(R.id.textView_total_wrong);
        totalEmpty = findViewById(R.id.textView_total_empty);
        successRate = findViewById(R.id.textView_total_success);

        buttonPlayAgain = findViewById(R.id.button_play_again);
        buttonQuit = findViewById(R.id.button_quit);

        correct = getIntent().getIntExtra("correct", 0);
        wrong = getIntent().getIntExtra("wrong", 0);
        empty = getIntent().getIntExtra("empty", 0);

        totalCorrect.setText("Total Correct Answers: " + correct);
        totalWrong.setText("Total Wrong Answers: " + wrong);
        totalEmpty.setText("Total Empty Answers: " + empty);
        successRate.setText("Success Rate: " + (correct*10) + "%");

        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newIntent = new Intent(Intent.ACTION_MAIN);
                newIntent.addCategory(Intent.CATEGORY_HOME);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
                finish();

            }
        });
    }
}