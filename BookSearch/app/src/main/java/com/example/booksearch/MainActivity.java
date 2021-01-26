package com.example.booksearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private BookListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private boolean recyclerViewFull = false;
    //private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=cocina&maxResults=10";
    private static final String BOOK_REQUEST_URL_BASE = "https://www.googleapis.com/books/v1/volumes";
    private StringBuilder qryStr;
    private String keywordToSearch;
    private TextView emptyView;
    private static final int BOOK_LOADER_ID = 1;
    private  static String langRestrictKey;
    private  static String langRestrictValue;
    private static String maxResultKey;
    private static String maxResultValue;

    private final SearchView.OnQueryTextListener searchViewOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mAdapter.clear();

            keywordToSearch = query;
            //On callback call MainActivity.this which implements the interface
            androidx.loader.app.LoaderManager.getInstance(MainActivity.this).restartLoader(BOOK_LOADER_ID,null, MainActivity.this);
            android.content.Loader<Object> loader0 = getLoaderManager().getLoader(BOOK_LOADER_ID);
            if(loader0 != null){
                loader0.forceLoad();
            }

            emptyView.setVisibility(View.GONE);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        emptyView = (TextView) findViewById(R.id.empty_view);
        mAdapter = new BookListAdapter(this, new ArrayList<Book>());
        LoaderManager loaderManager = getSupportLoaderManager();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (mAdapter.getItemCount() == 0 ){
            //mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
        if(savedInstanceState!= null){
            emptyView.setVisibility(View.GONE);
            keywordToSearch = savedInstanceState.getString("keyword");
            androidx.loader.app.LoaderManager.getInstance(MainActivity.this).restartLoader(BOOK_LOADER_ID,null, MainActivity.this);
            android.content.Loader<Object> loader0 = getLoaderManager().getLoader(BOOK_LOADER_ID);
            if(loader0 != null){
                loader0.forceLoad();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search Books");
        searchView.setOnQueryTextListener(searchViewOnQueryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, @Nullable Bundle args) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String maxResults = sharedPreferences.getString(getString(R.string.settings_max_results_key),getString(R.string.settings_max_results_default));
        String langRestricts = sharedPreferences.getString(getString(R.string.settings_langrestrict_key), getString(R.string.settings_langrestrict_default));
        Uri baseUri = Uri.parse(BOOK_REQUEST_URL_BASE);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q",keywordToSearch);
        uriBuilder.appendQueryParameter("maxResults", maxResults);
        if(!langRestricts.equals("none")){
            uriBuilder.appendQueryParameter(getString(R.string.settings_langrestrict_key),langRestricts);
        }

        return new BookLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Book>> loader, ArrayList<Book> books) {
        mAdapter.clear();
        if( books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
            mAdapter.notifyDataSetChanged();
        }

        Log.d(LOG_TAG, "onLoadFinsihed");
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        Log.d(LOG_TAG, "onLoadReset");

        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("keyword",keywordToSearch);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}