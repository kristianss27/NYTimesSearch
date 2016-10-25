package com.cristiansanchez.nytimessearch.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

/**
 * Created by kristianss27 on 10/21/16.
 */
public class ArticleClient {
    private String api_base_url;
    private String api_relative_url;
    private String api_key;
    private AsyncHttpClient client;
    private JSONArray jsonArray = null;

    public ArticleClient(String api_base_url,String api_relative_url,String api_key) {
        this.api_base_url = api_base_url;
        this.api_relative_url = api_relative_url;
        this.api_key = api_key;
        this.client = new AsyncHttpClient();
    }

    public String getUrl() {
        return api_base_url + api_relative_url;
    }

    public void getBooks(final String query, RequestParams params,JsonHttpResponseHandler handler) {
            String url = getUrl();
            client.get(url, params, handler);
    }

    public String getApi_base_url() {
        return api_base_url;
    }

    public void setApi_base_url(String api_base_url) {
        this.api_base_url = api_base_url;
    }

    public String getApi_relative_url() {
        return api_relative_url;
    }

    public void setApi_relative_url(String api_relative_url) {
        this.api_relative_url = api_relative_url;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public AsyncHttpClient getClient() {
        return client;
    }

    public void setClient(AsyncHttpClient client) {
        this.client = client;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}
