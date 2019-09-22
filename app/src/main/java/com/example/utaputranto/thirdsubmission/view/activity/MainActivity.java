package com.example.utaputranto.thirdsubmission.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.view.fragment.FavoriteFragment;
import com.example.utaputranto.thirdsubmission.view.fragment.MovieFragment;
import com.example.utaputranto.thirdsubmission.view.fragment.SearchFragment;
import com.example.utaputranto.thirdsubmission.view.fragment.SearchTvFragment;
import com.example.utaputranto.thirdsubmission.view.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String searchData;
    static final String SEARCH = "search";
    SearchView searchView, searchViewTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null) {
            FragmentManager fragmentManager =
                    getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_container
                            , new MovieFragment())
                    .commit();

        }

        navigationView.setCheckedItem(R.id.nav_movie);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH, searchData);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        savedInstanceState.getString(SEARCH);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchMovie));
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Fragment fragment = null;
                fragment = new SearchFragment();

                searchData = query;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment)
                        .commit();

                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        searchViewTv  =(SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchTv));
        searchViewTv.setQueryHint(getResources().getString(R.string.searchTv));
        searchViewTv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Fragment fragment = null;
                fragment = new SearchTvFragment();

                searchData = query;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment)
                        .commit();

                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.change_language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }else if (id == R.id.action_settings){
            Intent gotoSetting = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(gotoSetting);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = new Fragment();

        if (id == R.id.nav_movie) {
            fragment = new MovieFragment();
            getSupportActionBar().setTitle(getResources().getString(R.string.menu_movie));

        } else if (id == R.id.nav_tv_show) {
            fragment = new TvShowFragment();
            getSupportActionBar().setTitle(getResources().getString(R.string.menu_tv_show));
        } else if (id == R.id.nav_fav_movie) {
            fragment = new FavoriteFragment();
            getSupportActionBar().setTitle(getResources().getString(R.string.favourit_movies));
        }

        loadFragment(fragment);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public String getMyData() {
        return searchData;
    }
}
