package com.example.wx_todd.inclass09;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by WX-Todd on 11/8/2016.
 */
public class GetTriviaAsync extends AsyncTask<String, Void, ArrayList<Question>> {

    QuestionGetter questionGetter;
    ProgressDialog progressDialog;
    Context context;

    public GetTriviaAsync(QuestionGetter activity, Context context){
        questionGetter = activity;
        this.context = context;
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {
        ArrayList<Question> questions = new ArrayList<>();
        URL triviaUrl;
        BufferedReader reader = null;
        HttpURLConnection connection = null;

        try {
            triviaUrl = new URL(params[0]);
            connection = (HttpURLConnection) triviaUrl.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder();
            try {
                if(connection != null) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        try {
            JSONObject jsonObject = new JSONObject(builder.toString());
            Log.d("JsonObject", jsonObject.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("questions");
            Log.d("JsonArray", jsonArray.toString());
            for(int i = 0; i < jsonArray.length(); i++){
                Question question = new Question(jsonArray.getJSONObject(i));
                questions.add(question);
            }
            Log.d("Demo3", String.valueOf(questions.size()));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("Demo2", String.valueOf(questions.size()));
        return questions;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Trivia...");
        //progressDialog.show();

    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        questionGetter.getQuestions(questions);
        Log.d("Demo", String.valueOf(questions.size()));
        progressDialog.dismiss();
    }
    public interface QuestionGetter{
        void getQuestions(ArrayList<Question> questions);
    }
}
