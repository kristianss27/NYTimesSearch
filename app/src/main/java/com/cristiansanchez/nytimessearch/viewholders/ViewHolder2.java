package com.cristiansanchez.nytimessearch.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cristiansanchez.nytimessearch.R;

/**
 * Created by kristianss27 on 10/20/16.
 */
public class ViewHolder2 extends RecyclerView.ViewHolder {

    private ImageView imageView;

    public ViewHolder2(View v){
        super(v);
        imageView = (ImageView) v.findViewById(R.id.ivImage);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
