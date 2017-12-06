package com.example.lenovo.tmbd_safwane;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 28/11/2017.
 */

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "favorite.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_FAVORITE= "CREATE TABLE " + Favorite.TABLE  + "("
                + Favorite.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Favorite.KEY_title+ " TEXT, "
                + Favorite.KEY_overview+ " TEXT, "
                + Favorite.KEY_poster + " TEXT, "
                + Favorite.KEY_vote_average + " DOUBLE )";

        db.execSQL(CREATE_TABLE_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Favorite.TABLE);

        // Create tables again
        onCreate(db);

    }
    public boolean insert(Integer id, String title, String overview, String poster, double vote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Favorite.KEY_ID, id);
        values.put(Favorite.KEY_title, title);
        values.put(Favorite.KEY_overview,overview);
        values.put(Favorite.KEY_poster, poster);
        values.put(Favorite.KEY_vote_average, vote);
        long result = db.insert(Favorite.TABLE, null, values);
        return result != -1;

    }


    public Cursor getFavoriteList() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Favorite.KEY_ID + "," +
                Favorite.KEY_title + "," +
                Favorite.KEY_overview + "," +
                Favorite.KEY_poster + "," +
                Favorite.KEY_vote_average +
                " FROM " + Favorite.TABLE;


        return db.rawQuery(selectQuery, null);
    }


    /*public Cursor getById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select ID, TITLE, DESC, POSTER, VOTE_AVERAGE from " + Favorite.TABLE + " where ID=" + id, null);
    }*/
    public Cursor getFavoritesById(int Id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Favorite.KEY_ID + "," +
                Favorite.KEY_title + "," +
                Favorite.KEY_overview + "," +
                Favorite.KEY_poster + "," +
                Favorite.KEY_vote_average +
                " FROM " + Favorite.TABLE
                + " WHERE " +
                Favorite.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string


        return db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

    }


    /*public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Favorite.TABLE, "ID = ?", new String[]{id});
    }
    */

    public Integer delete(int favorite_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        return db.delete(Favorite.TABLE, Favorite.KEY_ID + "= ?", new String[] { String.valueOf(favorite_id) });
        //db.close(); // Closing database connection
    }


}