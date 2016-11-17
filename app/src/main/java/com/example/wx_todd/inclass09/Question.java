package com.example.wx_todd.inclass09;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by WX-Todd on 11/8/2016.
 */

public class Question implements Serializable {
    String questionText;
    String [] questionChoices;
    URL imageUrl;
    int answer;
    public Question(JSONObject jsonObject) throws JSONException, MalformedURLException {
        questionText = jsonObject.getString("text");
        if(jsonObject.has("image"))
                imageUrl = new URL(jsonObject.getString("image"));
        else
            imageUrl = null;
        getChoiceArray(jsonObject.getJSONObject("choices").getJSONArray("choice"));
        try {
            answer = Integer.parseInt(jsonObject.getJSONObject("choices").getString("answer"));
        }
        catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }

    }
    private void getChoiceArray(JSONArray jsonArray) throws JSONException {
        questionChoices = new String[jsonArray.length()];
        for(int i = 0; i < jsonArray.length(); i++){
            questionChoices[i] = jsonArray.getString(i);
        }
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getQuestionChoices() {
        return questionChoices;
    }

    public URL getImageUrl() {
        return imageUrl;
    }

    public int getAnswer() {
        return answer;
    }
}
