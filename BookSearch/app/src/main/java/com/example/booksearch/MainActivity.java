package com.example.booksearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private RecyclerView mRecyclerView;
    private BookListAdapter mAdapter;
    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";
    private static final int BOOK_LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new BookListAdapter(this, new ArrayList<Book>());
        LoaderManager loaderManager = getSupportLoaderManager();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Initialize the loadedr. Pass in the int ID constant defined abocve and pass in null for the bundle.
        //Pass in this activity for the LoaderCallbacks paramter, (Which is bvaid becase this activity implements the LoaderCallback interface)
        LoaderManager.getInstance(this).initLoader(BOOK_LOADER_ID, null, this);

    }

    @NonNull
    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, @Nullable Bundle bundle){
        //Log.d(LOG_TAG,"onCreateLoader");
        return new BookLoader(this, BOOK_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> books) {

        mAdapter.clear();
        if( books != null && !books.isEmpty()){
            mAdapter.addAll(books);
            mAdapter.notifyDataSetChanged();
        }
        Log.d(LOG_TAG, "onLoadFinsihed");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Book>> loader) {
        Log.d(LOG_TAG, "onLoadReset");
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();

    }


}