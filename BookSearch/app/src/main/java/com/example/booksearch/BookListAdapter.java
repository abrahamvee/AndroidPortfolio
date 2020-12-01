package com.example.booksearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Adapter used for RecyclerView
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder>{

    /**
     * Inner class of BookListAdapter, for ViewHolder of book object.
     */
    class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView titleView;
        public final TextView authorView;
        public final TextView priceView;
        public final TextView isEbookView;
        final BookListAdapter mAdapter;

        /**
         * Constructor of BookViewHolder object.
         * @param itemView The itemView for one book object.
         * @param adapter The adapter that is used for book objects.
         */
        public BookViewHolder(View itemView, BookListAdapter adapter){
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            authorView = itemView.findViewById(R.id.authors);
            priceView = itemView.findViewById(R.id.price);
            isEbookView = itemView.findViewById(R.id.ebook);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private LayoutInflater mInflater;
    private final ArrayList<Book> mBookList;

    /**
     * BookListAdapter constructor.
     * @param context Takes context of applications
     * @param bookList A list of books.
     */
    public BookListAdapter(Context context, ArrayList<Book> bookList){
        mInflater = LayoutInflater.from(context);
        this.mBookList = bookList;
    }

    /**
     * Empties adapter.
     */
    public void clear(){
        mBookList.clear();
    }

    /**
     * Adds all books to adapter.
     * @param books List of books to add.
     */
    public void addAll(ArrayList<Book> books){
        mBookList.addAll(books);
    }

    /**
     * Used on creating of BookViewHolder
     * @param parent BookViewHolder
     * @param viewType
     * @return A BookViewHolder object
     */
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.book_item,parent, false);
        return new BookViewHolder(mItemView, this);
    }

    /**
     * Binds (sets content of ViewHolder)
     * @param holder Holder to work on
     * @param position Position of the book that belongs to the ViewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull BookListAdapter.BookViewHolder holder, int position) {
    String mTitle = mBookList.get(position).getTitle();
    String mAuthor = mBookList.get(position).getAuthors();
    String mPrice = mBookList.get(position).getPrice().toString();
    boolean mIsEbook = mBookList.get(position).getIsEbook();

    holder.titleView.setText(mTitle);
    holder.authorView.setText(mAuthor);
    holder.priceView.setText(mPrice);
    if(mIsEbook) holder.isEbookView.setText("Ebook");
    }

    /**
     * Returns size of book list.
     * @return An integer with size of the list.
     */
    @Override
    public int getItemCount() {
        return mBookList.size();
    }

}
