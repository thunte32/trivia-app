package com.example.wx_todd.inclass09;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements GetImageAsync.GetImage {
    ArrayList<Question> questions = new ArrayList<>();
    TextView questionNum, questionText, timeLeft;
    ImageView questionImage;
    Button quitButton, nextButton;
    ProgressBar progressBar;
    RadioGroup radioGroup;
    int currentQuestion = 0;
    int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        Intent intent = getIntent();
        questions = (ArrayList<Question>)getIntent().getSerializableExtra(MainActivity.QUESTIONS_TAG);

        questionNum = (TextView) findViewById(R.id.questNumTextView);
        questionText = (TextView) findViewById(R.id.questionTextView);
        timeLeft = (TextView) findViewById(R.id.countdownTextView);
        questionImage = (ImageView) findViewById(R.id.questionImage);
        quitButton = (Button) findViewById(R.id.triviaQuitButton);
        nextButton = (Button) findViewById(R.id.triviaNextButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        for(int i = 0; i < questions.size(); i++){
            questionImage.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            Question q = questions.get(i);
            questionNum.setText("Q" + (i+1));
            if(q.getImageUrl() != null){
                new GetImageAsync(this).execute(q.getImageUrl());
            }
            questionText.setText(q.getQuestionText());
            for(int j = 0; j < q.getQuestionChoices().length; j++){
                RadioButton button = new RadioButton(this);
                button.setId(j+1);
                button.setText(q.getQuestionChoices().);
            }

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft.setText("Time Remaining: " + 1/1000);
            }

            @Override
            public void onFinish() {
                //Start stats activity, send correct questions

            }
        }.start();

    }

    @Override
    public void setImage(Bitmap bitmap) {
        questionImage.setImageBitmap(bitmap);
        questionImage.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
