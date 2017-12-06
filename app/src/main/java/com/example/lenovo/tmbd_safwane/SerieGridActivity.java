package com.example.lenovo.tmbd_safwane;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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

import com.example.lenovo.tmbd_safwane.models.Serie;
import com.example.lenovo.tmbd_safwane.models.Series;
import com.example.lenovo.tmbd_safwane.service.ApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SerieGridActivity extends AppCompatActivity {
    private static String TAG = SerieGridActivity.class.getSimpleName();

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
    private SerieAdapterGrid mAdapter;
    private RecyclerView mList;

    private static String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        language = pref.getString("lang", null);

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
                    Intent intentMain = new Intent(SerieGridActivity.this ,
                            MovieListActivity.class);
                    SerieGridActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.tvshow)){
                    Intent intentMain = new Intent(SerieGridActivity.this ,
                            SerieListActivity.class);
                    SerieGridActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.settings)){
                    Intent intentMain = new Intent(SerieGridActivity.this ,
                            SettingsActivity.class);
                    SerieGridActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.favorites)){
                    Intent intentMain = new Intent(SerieGridActivity.this ,
                            FavouriteListActivity.class);
                    SerieGridActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
            }
        });

        try {
            retrofitMethod();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Show the grid view after clicking on Grid Icon
        if (id == R.id.action_list) {
            Intent intentMain = new Intent(SerieGridActivity.this ,
                    SerieListActivity.class);
            SerieGridActivity.this.startActivity(intentMain);
            Log.i("Content "," Main layout ");
        }

        return super.onOptionsItemSelected(item);
    }



    public void retrofitMethod() throws IOException {
        final List<Serie> listSeries = new ArrayList<>();

        Retrofit restAdapter =
                new Retrofit.Builder()
                        .baseUrl(API_BASE)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        ).build();


        // Create a very simple REST adapter which points TMDB API endpoint.
        ApiService apiservice =  restAdapter.create(ApiService.class);

        // Execute the call asynchronously. Get a positive or negative callback.
        apiservice.getPopularSeries(API_KEY, language).enqueue(new Callback<Series>() {
            @Override
            public void onResponse(Call<Series> call, Response<Series> response) {
                // The network call was a success and we got a response

                //Toast.makeText(MovieListActivity.this, "It's working", Toast.LENGTH_SHORT).show();
                int i=0;
                Series series = response.body();
                if (series != null) {
                    for (Serie serie : series.getResults()) {
                        if (serie.getName() != null){// && movie.getPosterPath() != null) {
                            listSeries.add(serie);
                            i++;
                        }
                    }
                }

                /**
                 * Add the list we get to our recyler view
                 */

                mList = (RecyclerView) findViewById(R.id.rv);

                GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
                mList.setLayoutManager(layoutManager);

                mAdapter = new SerieAdapterGrid(listSeries, getApplicationContext());

                mList.setAdapter(mAdapter);
                // TODO: use the repository list and display it
            }

            @Override
            public void onFailure(Call<Series> call, Throwable t) {
                // the network call was a failure
                Toast.makeText(SerieGridActivity.this, "Could not retrieve data, check connection", Toast.LENGTH_SHORT).show();
                // TODO: handle error
            }
        });
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

