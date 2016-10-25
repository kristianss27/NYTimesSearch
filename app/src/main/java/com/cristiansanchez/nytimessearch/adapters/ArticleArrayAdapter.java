package com.cristiansanchez.nytimessearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristiansanchez.nytimessearch.R;
import com.cristiansanchez.nytimessearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kristianss27 on 10/20/16.
 */
public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView headline;

        public ViewHolder(View v){
            super(v);
            imageView = (ImageView) v.findViewById(R.id.ivImage);
            headline = (TextView) v.findViewById(R.id.tvHeadLine);
        }
    }

    //List of any object to display in my RecyclerView
    private List<Article> articles;
    private Context context;

    //Type of objects we might use
    private final int IMAGE = 0, NOIMAGE = 1;

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public int getItemCount() {
        return this.articles.size();
    }

    @Override
    public ArticleArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.layout_viewholder1, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleArrayAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = articles.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.headline;
        textView.setText(article.getHeadline());
        ImageView imageView = viewHolder.imageView;
        imageView.setImageResource(0);

        if(!article.getMultimedia().equalsIgnoreCase("")){
            Picasso.with(getContext())
                    .load(article.getMultimedia())
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(viewHolder.imageView);
        }

    }

}
