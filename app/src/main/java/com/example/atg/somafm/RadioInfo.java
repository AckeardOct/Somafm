package com.example.atg.somafm;

import java.io.Serializable;

/**
 * Created by atg on 23.12.17.
 */

public class RadioInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int icon;
    private String url;
    private boolean favorite = false;

    public RadioInfo(String name, int icon, String url) {
        this.name = name;
        this.icon = icon;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public String getUrl() {
        return url;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
