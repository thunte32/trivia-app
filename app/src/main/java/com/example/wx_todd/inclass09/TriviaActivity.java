package com.example.wx_todd.inclass09;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    public final static String CORRECT_TAG = "CORRECT";
    public final static String QUESTION_NUM_TAG = "NUMBER";
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

        correctAnswers = 0;
        currentQuestion = 0;

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
        Log.d("Question", questions.get(0).getQuestionText());
        startQuestion(0);
    }
    protected void startQuestion(int i){
        questionImage.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Question q = questions.get(i);
        questionNum.setText("Q" + (i+1));
        if(q.getImageUrl() != null){
            progressBar.setVisibility(View.VISIBLE);
            new GetImageAsync(this).execute(q.getImageUrl());
        }
        questionText.setText(q.getQuestionText());
        String [] string = q.getQuestionChoices();
        for(int j = 0; j < string.length; j++){
            RadioButton button = new RadioButton(this);
            radioGroup.clearCheck();
            button.setId(j+1);
            button.setText(string[j]);
            radioGroup.addView(button);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft.setText("Time Remaining: " + millisUntilFinished/1000);
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

    public void nextButton(View view) {
        Question q = questions.get(currentQuestion);
        int index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
        if((index + 1) == q.getAnswer()){
            correctAnswers ++;
            Log.d("Correct", "Correct");
        }

        if(currentQuestion + 1  < questions.size() ){
            radioGroup.removeAllViewsInLayout();
            startQuestion(currentQuestion + 1);
            currentQuestion++;
        }
        else{
            Intent intent = new Intent(this, StatsActivity.class );
            intent.putExtra(CORRECT_TAG, correctAnswers);
            intent.putExtra(QUESTION_NUM_TAG, currentQuestion);
            intent.putExtra(MainActivity.QUESTIONS_TAG, questions);
            startActivity(intent);
            finish();
        }
    }
}
