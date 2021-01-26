package com.example.booksearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Adapter used for RecyclerView
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder>{

    private LayoutInflater mInflater;
    private final List<Book> mBookList;
    Context context;

    /**
     * BookListAdapter constructor.
     * @param context Takes context of applications
     * @param bookList A list of books.
     */
    public BookListAdapter(Context context, ArrayList<Book> bookList){
        mInflater = LayoutInflater.from(context);
        this.mBookList = bookList;
        this.context = context;
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
        String mAuthor = mBookList.get(position).getAuthor();
        String mPublishedDate = mBookList.get(position).getPublishedYear();
        String mThumbnailUri = mBookList.get(position).getThumbnailUri();

        holder.titleView.setText(mTitle);
        holder.authorView.setText(mAuthor);
        holder.publishedYearView.setText(mPublishedDate);

        if (!mThumbnailUri.toString().isEmpty()){
            Glide.with(context)
                    .load(mThumbnailUri.toString())
                    .override(60,150)
                    .placeholder(R.drawable.ic_baseline_book_24)
                    .into(holder.thumbnailView);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_baseline_book_24)
                    .into(holder.thumbnailView);
            holder.thumbnailView.setImageAlpha(50);
        }


    }

    /**
     * Returns size of book list.
     * @return An integer with size of the list.
     */
    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    /**
     * Inner class of BookListAdapter, for ViewHolder of book object.
     */
    class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView titleView;
        public final TextView authorView;
        public final TextView publishedYearView;
        public final ImageView thumbnailView;
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
            publishedYearView = itemView.findViewById(R.id.publishedDate);
            thumbnailView = itemView.findViewById(R.id.book_thumbnail);
            thumbnailView.setScaleType(ImageView.ScaleType.CENTER);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }


}
