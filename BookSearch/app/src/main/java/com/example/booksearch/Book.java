package com.example.booksearch;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String bookId;
    private String title;
    private String authors;
    private double price;
    private boolean isEbook;
    private String thumbnail;

    public Book(String bookId, String title, String authors, double price, boolean isEbook){
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isEbook = isEbook;
    }

    public Book(String bookId, String title, String authors){
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
    }

    public String getTitle(){return title;}

    public String getAuthors(){return authors;}

}
