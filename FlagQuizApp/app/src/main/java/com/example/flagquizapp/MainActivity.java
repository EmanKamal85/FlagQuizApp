package com.example.flagquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView textViewWelcome;
    Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewWelcome = findViewById(R.id.textView_welcome);
        buttonStart = findViewById(R.id.button_start);

        copyDatabase();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }

    public void copyDatabase() {
        DatabaseCopyHelper helper = new DatabaseCopyHelper(MainActivity.this);
        try {
            helper.createDataBase();
            helper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}