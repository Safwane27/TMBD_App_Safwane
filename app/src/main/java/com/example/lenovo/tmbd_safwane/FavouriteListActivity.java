package com.example.lenovo.tmbd_safwane;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.tmbd_safwane.models.Movie;

import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.tmbd_safwane.R.drawable.movies;
import static com.example.lenovo.tmbd_safwane.R.id.rv;

public class FavouriteListActivity extends AppCompatActivity {
    private static String TAG = FavouriteListActivity.class.getSimpleName();

    Context context = this;

    //Drawer list attributes
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    Context mContext;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    //API attributes
    String API_KEY= "177a0c44d9c3d89ddd347a7a6e9899db";
    String API_BASE = "https://api.themoviedb.org/3/";

    //Recycler view and Adapter
    private String layout = "List";
    private MovieAdapter mAdapter;
    private RecyclerView mList;

    private DBHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavItems.add(new NavItem(getResources().getString(R.string.movies), getResources().getString(R.string.movies_des), R.drawable.movies));
        mNavItems.add(new NavItem(getResources().getString(R.string.tvshow), getResources().getString(R.string.tvshow_des), R.drawable.tv_shows));
        mNavItems.add(new NavItem(getResources().getString(R.string.settings), getResources().getString(R.string.settings_des), R.drawable.ic_settings_black_24dp));
        mNavItems.add(new NavItem(getResources().getString(R.string.favorites), getResources().getString(R.string.favorites_des  ), R.drawable.heart01));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigation Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //selectItemFromDrawer(position);
                /**
                 * TODO
                 * This function should create and open the corresponding intent of each item on the list
                 */
                setTitle(mNavItems.get(position).mTitle);
                Toast.makeText(mContext, "You clicked on " + mNavItems.get(position).mTitle, Toast.LENGTH_SHORT).show();
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.movies)){
                    Intent intentMain = new Intent(FavouriteListActivity.this ,
                            MovieListActivity.class);
                    FavouriteListActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.tvshow)){
                    Intent intentMain = new Intent(FavouriteListActivity.this ,
                            SerieListActivity.class);
                    FavouriteListActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.settings)){
                    Intent intentMain = new Intent(FavouriteListActivity.this ,
                            SettingsActivity.class);
                    FavouriteListActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.favorites)){
                    Intent intentMain = new Intent(FavouriteListActivity.this ,
                            FavouriteListActivity.class);
                    FavouriteListActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
            }
        });

        db = new DBHelper(this);
        Cursor res = db.getFavoriteList();
        mList = (RecyclerView) findViewById(rv);
        final List<Movie> listMovies = new ArrayList<>();


        while (res.moveToNext()) {
            Log.v("movie", res.getString(0) + " " + res.getString(1));
            Movie m = new Movie();
            m.setId(res.getInt(0));
            m.setTitle(res.getString(1));
            m.setOverview(res.getString(2));
            m.setPosterPath(res.getString(3));
            m.setVoteAverage(res.getDouble(4));

            listMovies.add(m);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mList.setLayoutManager(layoutManager);

        mAdapter = new MovieAdapter(listMovies, getApplicationContext());

        mList.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Show the grid view after clicking on Grid Icon
        if (id == R.id.action_grid) {
            Intent intentMain = new Intent(FavouriteListActivity.this ,
                    MovieGridActivity.class);
            FavouriteListActivity.this.startActivity(intentMain);
            Log.i("Content "," Main layout ");
        }

        return super.onOptionsItemSelected(item);
    }





    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {


        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView =  view.findViewById(R.id.title);
            TextView subtitleView = view.findViewById(R.id.subTitle);
            ImageView iconView = view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }
    }
}

