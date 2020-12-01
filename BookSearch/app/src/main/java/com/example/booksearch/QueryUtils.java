package com.example.booksearch;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QueryUtils {

   /** private static final String query = "{\"kind\":\"books#volumes\",\"totalItems\":1814,\"items\":[{\"kind\":\"books#volume\",\"id\":\"qKFDDAAAQBAJ\"" +
            ", \"etag\":\"4QrBGtFnq2M\",\"volumeInfo\":{\"title\":\"Android\",\"authors\":[\"P.K. Dixit\"],\"publisher\":\"Vikas Publishing House\"}}]}";
    **/
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private QueryUtils(){

    }

    public static ArrayList<Book> fetchBooks(String requestURL){
        //Create URL object
        URL url = createURL(requestURL);
        //Perform HTTP request URL and receive a JSON response
        String jsonResponse = null;
        try{
            jsonResponse = makeHTTPRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request",e);
        }
        ArrayList<Book> books = extractBooksFeatures(jsonResponse);
        return books;
    }

    public static ArrayList<Book> extractBooksFeatures(String jsonResponse) {
        ArrayList<Book> books = new ArrayList<>();
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray items = baseJsonResponse.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject currentItem = items.getJSONObject(i);
                String bookId = currentItem.getString("id");
                JSONObject volumeInfo = currentItem.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authors = volumeInfo.getJSONArray("authors");
                //StringBuilder authorNames = new StringBuilder();
                LinkedList<String> authorNames = new LinkedList<String>();
                for (int j = 0; j < authors.length(); j++) {
                    Log.i(LOG_TAG, "GETTING AUTHORS");
                    //authors.getString(i);
                    authorNames.add(authors.getString(j));
                }
                   /** if(j==0){
                        authorNames.append(authors.getString(i));
                    }else if (j == authors.length()-1){
                        authorNames.append(authors.getString(i));
                        authorNames.append('.');
                    }else{
                        authorNames.append(',');
                        authorNames.append(authors.getString(i));
                    }
                }
                JSONObject saleInfo = currentItem.getJSONObject("saleInfo");
                boolean isEbook = saleInfo.getBoolean("isEbook");
                JSONObject retailPrice = saleInfo.getJSONObject("listPrice");
                double amount = retailPrice.getDouble("amount");
                 **/
                Book book = new Book(bookId, title, authorNames.toString());
                Log.d(LOG_TAG,book.getTitle());
                Log.d(LOG_TAG,book.getAuthors());
                books.add(book);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the book JSON response", e);
        }
        return books;
    }

    public static URL createURL(String stringURL){
        URL url = null;
        try{
            url = new URL(stringURL);
        }catch (MalformedURLException exception){
            Log.e(LOG_TAG,"Error with creating URL", exception);
            return  null;
        }
        return url;
    }

    public static String makeHTTPRequest(URL url) throws IOException{
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        if(url==null){
            return jsonResponse;
        }
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG, "Error response code: "+ urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the JSON results", e);
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    /**
     * Conver {@link InputStream} into a String which contains the whole JSON response from the server
     */
    private static String readFromStream(InputStream inputStream)throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
