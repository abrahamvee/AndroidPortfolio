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

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder>{

    class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView titleView;
        public final TextView authorView;
        final BookListAdapter mAdapter;

        public BookViewHolder(View itemView, BookListAdapter adapter){
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            authorView = itemView.findViewById(R.id.authors);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private LayoutInflater mInflater;
    private final ArrayList<Book> mBookList;

    public BookListAdapter(Context context, ArrayList<Book> bookList){
        mInflater = LayoutInflater.from(context);
        this.mBookList = bookList;
    }

    public void clear(){
        mBookList.clear();
    }

    public void addAll(ArrayList<Book> books){
        mBookList.addAll(books);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.book_item,parent, false);
        return new BookViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListAdapter.BookViewHolder holder, int position) {
    String mTitle = mBookList.get(position).getTitle();
    holder.titleView.setText(mTitle);
    String mAuthor = mBookList.get(position).getAuthors();
    holder.authorView.setText(mAuthor);
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

}
