package com.example.booksearch;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private static final String LOG_TAG = BookLoader.class.getName();
    private String mUrl;

    public BookLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

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
        return QueryUtils.fetchBooks(mUrl);
    }
}

