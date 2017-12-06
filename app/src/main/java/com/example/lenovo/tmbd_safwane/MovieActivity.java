package com.example.lenovo.tmbd_safwane;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lenovo on 26/11/2017.
 */

public class MovieActivity extends AppCompatActivity {
    private static String TAG = MovieActivity.class.getSimpleName();

    Context context = this;

    //Drawer list attributes
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    Context mContext;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private DBHelper db;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    private Movie movie;
    private boolean mInserted;
    private boolean isFavourite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        setTitle(R.string.movie_details);
        mContext = this;

        db = new DBHelper(this);


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
                    Intent intentMain = new Intent(MovieActivity.this ,
                            MovieListActivity.class);
                    MovieActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.tvshow)){
                    Intent intentMain = new Intent(MovieActivity.this ,
                            SerieListActivity.class);
                    MovieActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.settings)){
                    Intent intentMain = new Intent(MovieActivity.this ,
                            SettingsActivity.class);
                    MovieActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.favorites)){
                    Intent intentMain = new Intent(MovieActivity.this ,
                            FavouriteListActivity.class);
                    MovieActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
            }
        });

        Intent i = getIntent();
        movie = (Movie) i.getSerializableExtra("movie");

        TextView title = (TextView) findViewById(R.id.title);
        TextView overview = (TextView) findViewById(R.id.overview);
        TextView popularity = (TextView) findViewById(R.id.popularity);
        ImageView poster = (ImageView) findViewById(R.id.poster);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        popularity.setText(Double.toString(movie.getVoteAverage()));

        if(movie.getBackdropPath() != null){
            Picasso
                    .with(context)
                    .load("http://image.tmdb.org/t/p/w500"+movie.getBackdropPath())
                    .into(poster);
        }
        else{
            Picasso
                    .with(context)
                    .load("http://image.tmdb.org/t/p/w500"+movie.getPosterPath())
                    .into(poster);
        }


        Cursor cursor = db.getFavoritesById(movie.getId());
        isFavourite = cursor.getCount() != 0;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_both, menu);
        if(!isFavourite){
            menu.findItem(R.id.action_favourite).setIcon(R.mipmap.ic_favorite_border);
        }
        if(isFavourite){
            menu.findItem(R.id.action_favourite).setIcon(R.drawable.ic_favorite_filled);
        }
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
            Intent intentMain = new Intent(MovieActivity.this ,
                    MovieGridActivity.class);
            MovieActivity.this.startActivity(intentMain);
            Log.i("Content "," Main layout ");
        }
        if (id == R.id.action_list) {
            Intent intentMain = new Intent(MovieActivity.this ,
                    MovieListActivity.class);
            MovieActivity.this.startActivity(intentMain);
            Log.i("Content "," Main layout ");
        }
        if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = getString(R.string.action_share_msg) + " " + movie.getTitle();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.action_share)));
        }

        if (id == R.id.action_favourite) {
            if(!isFavourite){
                mInserted = db.insert(movie.getId(), movie.getTitle(), movie.getOverview(), movie.getPosterPath(),   movie.getVoteAverage());
                if(mInserted) {
                    setTitle("Favorite");
                    item.setIcon(R.drawable.ic_favorite_filled);
                    isFavourite = true;
                }else{
                    setTitle("Already favorite");
                    item.setIcon(R.drawable.ic_favorite_filled);
                }
                //Toast.makeText(mContext, "Error adding to favourites", Toast.LENGTH_LONG);
            }
            else{
                Integer deletedRows = db.delete(movie.getId());
                if(deletedRows > 0){
                    setTitle("No longer favorite");
                    item.setIcon(R.mipmap.ic_favorite_border);
                    isFavourite = false;
                }
            }
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
