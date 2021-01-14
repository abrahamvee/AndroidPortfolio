package com.example.booksearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private RecyclerView mRecyclerView;
    private BookListAdapter mAdapter;
    //private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=cocina&maxResults=10";
    private StringBuilder qryStr;
    private SearchView searchView;
    private static final int BOOK_LOADER_ID = 1;

    private final SearchView.OnQueryTextListener searchViewOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mAdapter.clear();
            qryStr = new StringBuilder();
            qryStr.append("https://www.googleapis.com/books/v1/volumes?q=");
            qryStr.append(query).append("&maxResults=10");
            String string2query = qryStr.toString();
            //On callback call MainActivity.this which implements the interface
            androidx.loader.app.LoaderManager.getInstance(MainActivity.this).restartLoader(BOOK_LOADER_ID,null, MainActivity.this);
            android.content.Loader<Object> loader0 = getLoaderManager().getLoader(BOOK_LOADER_ID);

            if(loader0 != null){
                loader0.forceLoad();
            }
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
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mAdapter = new BookListAdapter(this, new ArrayList<Book>());
        LoaderManager loaderManager = getSupportLoaderManager();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Initialize the loadedr. Pass in the int ID constant defined abocve and pass in null for the bundle.
        //Pass in this activity for the LoaderCallbacks paramter, (Which is bvaid becase this activity implements the LoaderCallback interface)
        //loaderManager.getLoader(BOOK_LOADER_ID);

        //LoaderManager.getInstance(this).initLoader(BOOK_LOADER_ID, null, loaderCallbacks );
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
        return new BookLoader(this, qryStr.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Book>> loader, ArrayList<Book> books) {
        mAdapter.clear();
        if( books != null && !books.isEmpty()){
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
}