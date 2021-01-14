package com.example.booksearch;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book. Holds information such as: title, author's name and publication year.
 * Thumbnail and additional information
 */

public class Book {

    //String for the book's title
    private final String mTitle;
    //String for the author's name
    private final String mAuthor;
    //String for the year of publication
    private final String mPublishedYear;
    //Uri for the thumbnail image of book
    private final Uri mThumbnailUri;


    /**
     * Book initializer
     * @param title A string with the book title.
     * @param author A String with the author's name
     * @param publishedYear A string with the publication year
     * @param thumbnailUri A string with the link to a thumbnail image for the book
     *
     */

    Book(String title, String author, String publishedYear, String thumbnailUri){
        mTitle = title;
        mAuthor = author;
        mPublishedYear = publishedYear;
        mThumbnailUri = Uri.parse(thumbnailUri);
    }

    String getTitle(){
        return mTitle;
    }

    String getAuthor(){
        return mAuthor;
    }

    String getPublishedYear(){
        return mPublishedYear;
    }

    Uri getThumbnailUri(){
        return mThumbnailUri;
    }
}
