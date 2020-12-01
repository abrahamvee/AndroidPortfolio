package com.example.booksearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Book object that contains title, authors, price and if it is available as eBook.
 */

public class Book {

    private String bookId;
    private String title;
    private String authors;
    private double price;
    private boolean isEbook;
    private String thumbnail;

    /**
     * Constructor for book object
     * @param bookId alfanumeric ID for book
     * @param title Title of the book
     * @param authors Authors of the book.
     * @param price Retail price of the book
     * @param isEbook Boolean Indicates if the book is an eBook or not.
     */
    public Book(String bookId, String title, String authors, double price, boolean isEbook){
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isEbook = isEbook;
    }

    /**
     * Overloaded constructor for book object.
     * @param bookId
     * @param title
     * @param authors
     */
    public Book(String bookId, String title, String authors){
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
    }

    /**
     * Getter method for the book title.
     * @return the title of the book.
     */
    public String getTitle(){return title;}

    /**
     * Getter method for the book's authors.
     * @return Author or authors of the book.
     */
    public String getAuthors(){return authors;}

    /**
     * Getter method for the price of the book.
     * @return Price of the book as a double.
     */
    public Double getPrice(){return price;}

    /**
     * Getter method for boolean value that indicates if book is available as ebook.
     * @return boolean value isEbook
     */
    public boolean getIsEbook(){return isEbook;}
}
