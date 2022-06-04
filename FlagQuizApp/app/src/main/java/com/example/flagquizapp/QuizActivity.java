package com.example.flagquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class QuizActivity extends AppCompatActivity {

    TextView correctAnswers, emptyAnswers, wrongAnswers, questionsTextView;
    ImageView flageImageView;
    Button buttonA, buttonB, buttonC, buttonD;
    ImageButton next;

    FlagDatabase flagDatabase;
    ArrayList<FlagModel> questionList = new ArrayList<>();

    int correctCount = 0;
    int wrongCount = 0;
    int emptyCount = 0;
    int questionsCount = 0;
    private FlagModel correctFlag;
    ArrayList<FlagModel> wrongAnswersList;

    HashSet<FlagModel> mixOptions = new HashSet<>();
    ArrayList<FlagModel> options = new ArrayList<>();

    boolean buttonControl = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        correctAnswers = findViewById(R.id.textView_correct);
        emptyAnswers = findViewById(R.id.textView_empty);
        wrongAnswers = findViewById(R.id.textView_wrong);
        questionsTextView = findViewById(R.id.textView_question);
        flageImageView = findViewById(R.id.imageView_flag);
        buttonA = findViewById(R.id.button_a);
        buttonB = findViewById(R.id.button_b);
        buttonC = findViewById(R.id.button_c);
        buttonD = findViewById(R.id.button_d);
        next = findViewById(R.id.imageButton_next);

        flagDatabase = new FlagDatabase(QuizActivity.this);
        questionList = new FlagsDAO().getRandomTenQuestions(flagDatabase);


        loadQuestions();

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerControl(buttonA);
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerControl(buttonB);
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                answerControl(buttonC);
            }
        });

        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                answerControl(buttonD);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                questionsCount++;


                correctAnswers.setText("Correct Answers :" + correctCount);
                wrongAnswers.setText("Wrong Answers :" + wrongCount);

                if (!buttonControl && questionsCount < 10){
                    emptyCount++;
                    emptyAnswers.setText("Empty Answers: " + emptyCount);
                    loadQuestions();
                }else if (buttonControl && questionsCount<10){

                    loadQuestions();
                    buttonA.setClickable(true);
                    buttonB.setClickable(true);
                    buttonC.setClickable(true);
                    buttonD.setClickable(true);

                    buttonA.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
                    buttonB.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
                    buttonC.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
                    buttonD.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
                }else if (questionsCount == 10){

                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("correct", correctCount);
                    intent.putExtra("wrong" , wrongCount);
                    intent.putExtra("empty", emptyCount);
                    startActivity(intent);
                    finish();

                }
                buttonControl = false;
            }
        });

    }

    public void loadQuestions(){

        questionsTextView.setText("Questions: " + (questionsCount+1));
        correctFlag = questionList.get(questionsCount);
        flageImageView.setImageResource(getResources().getIdentifier(correctFlag.getFlag_image(), "drawable", getPackageName()));
        wrongAnswersList = new FlagsDAO().getRandomThreeOptions(flagDatabase);

        mixOptions.clear();
        mixOptions.add(correctFlag);
        mixOptions.add(wrongAnswersList.get(0));
        mixOptions.add(wrongAnswersList.get(1));
        mixOptions.add(wrongAnswersList.get(2));

        options.clear();

        for (FlagModel flg: mixOptions){
            options.add(flg);
        }

        buttonA.setText(options.get(0).getFlag_name());
        buttonB.setText(options.get(1).getFlag_name());
        buttonC.setText(options.get(2).getFlag_name());
        buttonD.setText(options.get(3).getFlag_name());

    }

    public void answerControl(Button button){

        String correctAnswer = correctFlag.getFlag_name();
        String selectedAnswer = button.getText().toString();

        if (selectedAnswer.equals(correctAnswer)){
            correctCount++;
            button.setBackgroundColor(Color.GREEN);

        }else {
            wrongCount++;
            button.setBackgroundColor(Color.RED);
            if (buttonA.getText().toString().equals(correctAnswer)){
                buttonA.setBackgroundColor(Color.GREEN);
            }
            if (buttonB.getText().toString().equals(correctAnswer)){
                buttonB.setBackgroundColor(Color.GREEN);
            }
            if (buttonC.getText().toString().equals(correctAnswer)){
                buttonC.setBackgroundColor(Color.GREEN);
            }
            if (buttonD.getText().toString().equals(correctAnswer)){
                buttonD.setBackgroundColor(Color.GREEN);
            }


        }


        buttonA.setClickable(false);
        buttonB.setClickable(false);
        buttonC.setClickable(false);
        buttonD.setClickable(false);

        buttonControl = true;
    }
}