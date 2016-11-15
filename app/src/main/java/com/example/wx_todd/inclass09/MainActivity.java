package com.example.wx_todd.inclass09;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetTriviaAsync.QuestionGetter {
    final static String TRIVIA_URL =
            "http://dev.theappsdr.com/apis/trivia_json/index.php.";
    Button exitButton, startButton;
    ArrayList<Question> questions = new ArrayList<>();
    TextView triviaText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        triviaText = (TextView) findViewById(R.id.triviaReadyText);
        triviaText.setVisibility(View.INVISIBLE);

        exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setEnabled(false);

        if (isConnected()) {
            new GetTriviaAsync(this, this).execute(TRIVIA_URL);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void getQuestions(ArrayList<Question> questions) {
        this.questions = questions;
        startButton.setEnabled(true);
        triviaText.setVisibility(View.VISIBLE);
    }
    public boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
