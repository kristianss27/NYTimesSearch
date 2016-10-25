package com.cristiansanchez.nytimessearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cristiansanchez.nytimessearch.R;
import com.cristiansanchez.nytimessearch.models.Article;
import com.cristiansanchez.nytimessearch.viewholders.ViewHolder1;
import com.cristiansanchez.nytimessearch.viewholders.ViewHolder2;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kristianss27 on 10/20/16.
 */
public class ArticleArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public int getItemViewType(int position) {
        if (!articles.get(position).getMultimedia().equalsIgnoreCase("")) {
            return IMAGE;
        }
        return NOIMAGE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case IMAGE:
                View v1 = inflater.inflate(R.layout.layout_viewholder1, parent, false);
                viewHolder = new ViewHolder1(v1,parent.getContext(),articles);
                break;
            case NOIMAGE:
                View v2 = inflater.inflate(R.layout.layout_viewholder2, parent, false);
                viewHolder = new ViewHolder2(v2,parent.getContext(),articles);
                break;
            default:
                View v3 = inflater.inflate(R.layout.layout_viewholder2, parent, false);
                viewHolder = new ViewHolder2(v3,parent.getContext(),articles);
                break;
        }

        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = articles.get(position);

        switch (viewHolder.getItemViewType()) {
            case IMAGE:
                ViewHolder1 vh1 = (ViewHolder1) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case NOIMAGE:
                ViewHolder2 vh2 = (ViewHolder2) viewHolder;
                configureViewHolder2(vh2, position);
                break;
            default:
                ViewHolder2 vh3 = (ViewHolder2) viewHolder;
                configureViewHolder2(vh3, position);
                break;
        }


    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        Article article = (Article) articles.get(position);
        if (article != null) {
            // Set item views based on your views and data model
            vh1.headline.setText(article.getHeadline());
            vh1.imageView.setImageResource(0);

                Picasso.with(getContext())
                        .load(article.getMultimedia())
                        .fit()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(vh1.imageView);
        }
    }

    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        Article article = (Article) articles.get(position);
        if (article != null) {
            Log.d("ArticleArrayAdapter","Get Head Line:"+article.getHeadline());
            vh2.headline.setText(article.getHeadline());
        }

    }

}
