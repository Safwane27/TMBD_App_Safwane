package com.example.lenovo.tmbd_safwane;

/**
 * Created by Lenovo on 28/11/2017.
 */

public class Favorite {
    // Labels table name
    public static final String TABLE = "Favorite";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_title= "title";
    public static final String KEY_overview= "overview";
    public static final String KEY_poster= "poster";
    public static final String KEY_vote_average = "vote_average";

    // property help us to keep data
    public int favorite_ID;
    public String title;
    public String overview;
    public String poster;
    public int vote_average;
}
