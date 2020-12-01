package com.example.booksearch;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Loads a list of books by ussing an ASyncTask to perform the network request to the given URL
 */
public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private static final String LOG_TAG = BookLoader.class.getName();
    private String mUrl;

    /**
     * Constructs a new {@link BookLoader}
     * @param context context of the activity
     * @param url to load data from
     */
    public BookLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    /**
     * this is on background thread, starts the bakground task, using forceLoad
     */
    @Override protected void onStartLoading(){
        Log.d(LOG_TAG, "Start Loading");
        forceLoad();
    }


    @Override
    public ArrayList<Book> loadInBackground(){
        Log.d(LOG_TAG,"Load in Background");
        if (mUrl == null) {
            return null;
        }
        //Perform the network request, parse the response, and extract a list of books
        return QueryUtils.fetchBooks(mUrl);
    }
}

