package com.cristiansanchez.nytimessearch.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cristiansanchez.nytimessearch.R;
import com.cristiansanchez.nytimessearch.activities.ArticleActivity;
import com.cristiansanchez.nytimessearch.models.Article;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by kristianss27 on 10/20/16.
 */
public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView headline;
    private Context context;
    private List<Article> articles;

    public ViewHolder2(View v,Context context, List<Article> articles){
        super(v);
        headline = (TextView) v.findViewById(R.id.tvHeadLine2);
        // Store the context
        this.context = context;
        this.articles = articles;
        // Attach a click listener to the entire row view
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition(); // gets item position
        if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
            Article article = articles.get(position);
            // We can access the data within the views
            Intent intent = new Intent(context, ArticleActivity.class);
            intent.putExtra("article", Parcels.wrap(article));
            v.getContext().startActivity(intent);
            //Toast.makeText(context, article.getHeadline(), Toast.LENGTH_SHORT).show();
        }
    }
}
