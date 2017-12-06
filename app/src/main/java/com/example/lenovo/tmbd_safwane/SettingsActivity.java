package com.example.lenovo.tmbd_safwane;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.lenovo.tmbd_safwane.R.array.languages;
import static com.example.lenovo.tmbd_safwane.R.id.spinner;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Spinner spinnerLangs;
    String language;
    static boolean initialDisplay = true;

    //Drawer list attributes
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    Context mContext;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<SettingsActivity.NavItem> mNavItems = new ArrayList<SettingsActivity.NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.settings);
        initialDisplay = true;


        mNavItems.add(new SettingsActivity.NavItem(getResources().getString(R.string.movies), getResources().getString(R.string.movies_des), R.drawable.movies));
        mNavItems.add(new SettingsActivity.NavItem(getResources().getString(R.string.tvshow), getResources().getString(R.string.tvshow_des), R.drawable.tv_shows));
        mNavItems.add(new SettingsActivity.NavItem(getResources().getString(R.string.settings), getResources().getString(R.string.settings_des), R.drawable.ic_settings_black_24dp));
        mNavItems.add(new SettingsActivity.NavItem(getResources().getString(R.string.favorites), getResources().getString(R.string.favorites_des  ), R.drawable.heart01));

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
                    Intent intentMain = new Intent(SettingsActivity.this ,
                            MovieListActivity.class);
                    SettingsActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.tvshow)){
                    Intent intentMain = new Intent(SettingsActivity.this ,
                            SerieListActivity.class);
                    SettingsActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.settings)){
                    Intent intentMain = new Intent(SettingsActivity.this ,
                            SettingsActivity.class);
                    SettingsActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
                if(mNavItems.get(position).mTitle == getResources().getString(R.string.favorites)){
                    Intent intentMain = new Intent(SettingsActivity.this ,
                            FavouriteListActivity.class);
                    SettingsActivity.this.startActivity(intentMain);
                    Log.i("Content "," Main layout ");
                }
            }
        });

        /***********Spinner********************/

        spinnerLangs = (Spinner) findViewById(spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                languages, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerLangs.setAdapter(adapterSpinner);

        sharedPreferences = getBaseContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        language = sharedPreferences.getString("lang", null);

        if (language.equals("en-EN")) {
            setLocale("en", SettingsActivity.this);
            spinnerLangs.setSelection(0);
        }
        if (language.equals("fr-FR")) {
            setLocale("fr", SettingsActivity.this);
            spinnerLangs.setSelection(1);
        }

        spinnerLangs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                language = spinnerLangs.getSelectedItem().toString();

                if (!initialDisplay) {
                    if (language.equals("English") || language.equals("Anglais")) {
                        sharedPreferences
                                .edit()
                                .putString("lang", "en-EN")
                                .apply();
                        setLocale("en", SettingsActivity.this);
                        finish();
                        startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));

                    }
                    if (language.equals("French") || language.equals("Français")) {
                        sharedPreferences
                                .edit()
                                .putString("lang", "fr-FR")
                                .apply();
                        setLocale("fr", SettingsActivity.this);
                        finish();
                        startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
                    }

                }
                initialDisplay = false;
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
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



    public static void setLocale(String lang, Context context) {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
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
            Intent intentMain = new Intent(SettingsActivity.this ,
                    MovieListActivity.class);
            SettingsActivity.this.startActivity(intentMain);
            Log.i("Content "," Main layout ");
        }

        return super.onOptionsItemSelected(item);
    }
}
