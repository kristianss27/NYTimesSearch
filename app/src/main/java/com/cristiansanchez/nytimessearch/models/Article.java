package com.cristiansanchez.nytimessearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kristianss27 on 10/20/16.
 */
@Parcel
public class Article {

    String webUrl;
    String headline;
    String multimedia;

    public Article(){
        super();
    }

    public Article(JSONObject jsonObject) throws JSONException{
        this.webUrl = jsonObject.getString("web_url");
        this.headline = jsonObject.getJSONObject("headline").getString("main");
        JSONArray multimediaArray = jsonObject.getJSONArray("multimedia");
        if(multimediaArray.length()>0){
            for (int i=0; i < 1; i++){
                JSONObject multimediaObject = (JSONObject) multimediaArray.get(i);
                this.multimedia = "http://www.nytimes.com/"+multimediaObject.getString("url");
            }
        }
        else{
            this.multimedia="";
        }
    }

    public static ArrayList<Article> fromJSONArray(JSONArray array,List<Article> listArticles){
        ArrayList<Article> arrayArticles;

        if(listArticles.size()>0){
            arrayArticles = new ArrayList<Article>();
            arrayArticles.addAll(listArticles);
        }
        else {
            arrayArticles = new ArrayList<Article>();
        }

            for (int i = 0; i < array.length(); i++) {
                try {
                    arrayArticles.add(new Article(array.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        return arrayArticles;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getMultimedia() {
        return multimedia;
    }
}
