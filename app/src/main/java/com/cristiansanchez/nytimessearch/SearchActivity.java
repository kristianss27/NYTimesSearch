package com.cristiansanchez.nytimessearch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cristiansanchez.nytimessearch.activities.SearchFilteredActivity;
import com.cristiansanchez.nytimessearch.adapters.ArticleArrayAdapter;
import com.cristiansanchez.nytimessearch.listeners.EndlessRecyclerViewScrollListener;
import com.cristiansanchez.nytimessearch.models.Article;
import com.cristiansanchez.nytimessearch.models.NewsDesk;
import com.cristiansanchez.nytimessearch.net.ArticleClient;
import com.cristiansanchez.nytimessearch.utils.Utils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchFilteredActivity";

    RecyclerView rvArticles;
    GridLayoutManager gridLayout;

    ArticleArrayAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Article> listArticles;
    ArrayAdapter arrayAdapter;
    RequestParams params;

    String query;
    boolean newSearch;
    private final int REQUEST_CODE = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);


        setupViews();
    }

    public void setupViews(){
        //We got the value or our queryTextView
        listArticles = new ArrayList<>();
        //gridLayout = new GridLayoutManager(SearchActivity.this, 4);
        StaggeredGridLayoutManager gridLayout =
                new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);

        rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        rvArticles.setHasFixedSize(true);
        rvArticles.setLayoutManager(gridLayout);

        rvArticles.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayout,newSearch) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d("LISTENER","page onScroll: "+page);
                Log.d("LISTENER","totalItemsCount: "+totalItemsCount);
                Log.d("LISTENER","getItemAcount: "+adapter.getItemCount());
                onLoadMoreArticles(page,params,query);
            }
        });

        adapter = new ArticleArrayAdapter(SearchActivity.this, listArticles);
        rvArticles.setAdapter(adapter);
        rvArticles.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {

            @Override
            public void onTouchEvent(RecyclerView recycler, MotionEvent event) {
                Toast.makeText(SearchActivity.this,"PRUEBA",Toast.LENGTH_LONG);
            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recycler, MotionEvent event) {
                return false;
            }

        });
    }

    public void onArticleSearch(View view) {
        onLoadMoreArticles(0,null,query);
    }

    public void onLoadMoreArticles(int page,RequestParams params,String query) {
        Log.d("LISTENER","page: "+page);
        this.query = query;
        if(newSearch){
            listArticles.clear();
            adapter.notifyDataSetChanged();
            newSearch = false;
        }

        final List<Article> listAux = new ArrayList<Article>();
        //Let's create our Http Client and set up the params to invoke the api
        String url = getResources().getString(R.string.api_url_search);
        String relativeUrl = getResources().getString(R.string.api_url_article_relative);
        String apiKey = getResources().getString(R.string.api_key);
        final int positionStart = listArticles.size()+1;

        if(params==null) {
            RequestParams paramsAux = new RequestParams();
            paramsAux.put("api-key", getResources().getString(R.string.api_key));
            paramsAux.put("q", query);
            paramsAux.put("page", page);
            params = paramsAux;
        }
        else{
            params.put("q", query);
        }

            ArticleClient client = new ArticleClient(url,relativeUrl,apiKey);

            client.getBooks(client.getUrl(), params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("JSON RESPONSE", "Json response: " + response.toString());
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = response.getJSONObject("response").getJSONArray("docs");
                        if (listArticles.size() > 0) {
                            listAux.addAll(listArticles);
                            listArticles.clear();
                        }

                        listArticles.addAll(Article.fromJSONArray(jsonArray, listAux));
                        Log.d("LISTENER","getItemAcount: "+adapter.getItemCount());
                        adapter.notifyItemRangeInserted(adapter.getItemCount(), listArticles.size() - 1);
                        Log.d("LISTENER","getItemAcount: "+adapter.getItemCount());
                        //adapter.notifyDataSetChanged();

                        Log.d("LIST SIZE", " List size is:" + listArticles.size());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Date date = new Date();
        String query = data.getStringExtra("query");


        String newsDeskChain = "news_desk:(";
        String newsDeskArt ="&quot;Arts&quot;";
        String newsDeskFashion ="&quot;Fashion &amp; Style&quot;";
        String newsDeskSport ="&quot;Sport&quot;";
        String newDeskEnd = ")";

        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            //Fill the news desk
            NewsDesk newsDesk = (NewsDesk) Parcels.unwrap(data.getParcelableExtra("news"));
            boolean isNewsDesk = false;
            if(newsDesk.isArts()){
                newsDeskChain = newsDeskChain + newsDeskArt;
                isNewsDesk = true;
            }
            if(newsDesk.isFashion()){
                newsDeskChain = newsDeskChain + newsDeskFashion;
                isNewsDesk = true;
            }
            if(newsDesk.isStyleSports()){
                newsDeskChain = newsDeskChain + newsDeskSport;
                isNewsDesk = true;
            }

            newsDeskChain = newsDeskChain + newDeskEnd;
            Log.d(TAG,"News Desk: "+newsDeskChain);

            //Define begin date and end date 20160112

            String itemDate = data.getStringExtra("date");
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            Calendar calendar = Calendar.getInstance();

                try {
                    date = dateFormat.parse(itemDate);
                    calendar.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String beginDate = new Utils().getFormatDateUrl(calendar,true);
                String endDate = new Utils().getFormatDateUrl(Calendar.getInstance(),true);
                Log.d(TAG,"begin and end date: "+beginDate+" and "+endDate);

            String sort = data.getStringExtra("sort");
            Log.d(TAG,"Sort: "+sort);

            Log.d(TAG,"Query: "+query);

            RequestParams paramsFiltered = new RequestParams();
            paramsFiltered.put("api-key", getResources().getString(R.string.api_key));
            paramsFiltered.put("begin_date",beginDate);
            //params.put("end_date",endDate);
            if(isNewsDesk){
                paramsFiltered.put("fq", newsDeskChain);
            }

            paramsFiltered.put("sort",sort);
            paramsFiltered.put("page", 0);

            this.params = paramsFiltered;
            setupViews();

        }
    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Expand the search view and request focus
        searchItem.expandActionView();
        searchView.requestFocus();

        // Use a custom search icon for the SearchView in AppBar
        int searchImgId = android.support.v7.appcompat.R.id.search_button;
        ImageView v = (ImageView) searchView.findViewById(searchImgId);
        v.setImageResource(android.R.drawable.ic_menu_search);
        // Customize searchview text and hint colors
        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText et = (EditText) searchView.findViewById(searchEditId);
        et.setTextColor(Color.BLACK);
        et.setHintTextColor(Color.BLACK);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                    newSearch = true;
                    onLoadMoreArticles(0,params,query);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.filter).setVisible(true);
        //menu.findItem(R.id.save_item).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.filter:
                listArticles.clear();
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(this, SearchFilteredActivity.class);
                intent.putExtra("query",query);
                intent.putExtra("code", REQUEST_CODE);
                startActivityForResult(intent,REQUEST_CODE);
                return true;
            /*case R.id.delete_item:
                showAlertDialog();
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
