package com.example.booksearch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class QueryUtils {

    private static final String query = "{\"kind\":\"books#volumes\",\"totalItems\":1814,\"items\":[{\"kind\":\"books#volume\",\"id\":\"qKFDDAAAQBAJ\"" +
            ", \"etag\":\"4QrBGtFnq2M\",\"volumeInfo\":{\"title\":\"Android\",\"authors\":[\"P.K. Dixit\"],\"publisher\":\"Vikas Publishing House\"}}]}";

    private QueryUtils(){

    }

    public static LinkedList<Book> extractBooks(){
        //
        LinkedList<Book> books = new LinkedList<>();
        //TODO: try to parse the string for the JSON response
        try{
            JSONObject baseJsonResponse = new JSONObject(query);
            JSONArray items = baseJsonResponse.getJSONArray("items");
            for(int i=0;i<items.length();i++){
                JSONObject currentItem = items.getJSONObject(i);
                String bookId = currentItem.getString("id");
                JSONObject volumeInfo = currentItem.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authors = volumeInfo.getJSONArray("authors");
                LinkedList<String> authorNames = new LinkedList<String>();
                for(int j=0;j<authors.length();j++){
                    authorNames.add(authors.getString(j));
                }
                Book book = new Book(bookId,title,authorNames);
                books.add(book);
            }

            //
        }catch (JSONException e){
            Log.e("QueryUtils","Problem parsing the book JSON response", e);
        }
        return books;
    }
}
