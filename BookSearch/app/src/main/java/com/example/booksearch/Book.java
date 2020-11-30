package com.example.booksearch;

import java.util.List;

public class Book {

    private String bookId;
    private String title;
    private List<String> authors;
    private String thumbnail;

    public Book(String bookId, String title, List<String> authors){
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
    }

    public String getTitle(){return title;}

    public List<String> getAuthors(){return authors;}

}
