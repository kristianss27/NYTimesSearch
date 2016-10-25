package com.cristiansanchez.nytimessearch.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristiansanchez.nytimessearch.R;

/**
 * Created by kristianss27 on 10/19/16.
 */
public class ViewHolder1 extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView headline;


    public ViewHolder1(View v){
        super(v);
        imageView = (ImageView) v.findViewById(R.id.ivImage);
        headline = (TextView) v.findViewById(R.id.tvHeadLine);
    }
}
