package com.example.lenovo.tmbd_safwane;

import android.content.Context;
import android.content.Intent;
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

import static com.example.lenovo.tmbd_safwane.R.drawable.movies;

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

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        mNavItems.add(new NavItem("Movies", "List the movies", movies));
        mNavItems.add(new NavItem("Tv shows", "List the tv shows", R.drawable.tv_shows));
        mNavItems.add(new NavItem("Settings", "Change your settings", R.drawable.ic_settings_black_24dp));
        mNavItems.add(new NavItem("Favourites", "List your favourites", R.drawable.heart01));

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
                if(mNavItems.get(position).mTitle == "Movies"){
                    Intent intentMain = new Intent(MovieActivity.this ,
                            MovieListActivity.class);
                    MovieActivity.this.startActivity(intentMain);
                    Log.i("Content "," Movie layout ");
                }
                if(mNavItems.get(position).mTitle == "Tv shows"){
                    Intent intentMain = new Intent(MovieActivity.this ,
                            SerieListActivity.class);
                    MovieActivity.this.startActivity(intentMain);
                    Log.i("Content "," Serie layout ");
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

        Picasso
                .with(context)
                .load("http://image.tmdb.org/t/p/w500"+movie.getBackdropPath())
                .into(poster);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_both, menu);
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
