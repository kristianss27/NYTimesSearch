package com.cristiansanchez.nytimessearch.models;

import org.parceler.Parcel;

/**
 * Created by kristianss27 on 10/24/16.
 */
@Parcel
public class NewsDesk {
    boolean arts;
    boolean fashion;
    boolean styleSports;

    public NewsDesk(){
    }

    public void clean(){
        arts = false;
        fashion = false;
        styleSports = false;
    }

    public boolean isArts() {
        return arts;
    }

    public void setArts(boolean arts) {
        this.arts = arts;
    }

    public boolean isFashion() {
        return fashion;
    }

    public void setFashion(boolean fashion) {
        this.fashion = fashion;
    }

    public boolean isStyleSports() {
        return styleSports;
    }

    public void setStyleSports(boolean styleSports) {
        this.styleSports = styleSports;
    }
}
