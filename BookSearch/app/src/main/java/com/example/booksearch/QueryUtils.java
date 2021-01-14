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

/**
 * Utility class used to query the Google Book's API.
 */
public class QueryUtils {

   /** private static final String query = "{\"kind\":\"books#volumes\",\"totalItems\":1814,\"items\":[{\"kind\":\"books#volume\",\"id\":\"qKFDDAAAQBAJ\"" +
            ", \"etag\":\"4QrBGtFnq2M\",\"volumeInfo\":{\"title\":\"Android\",\"authors\":[\"P.K. Dixit\"],\"publisher\":\"Vikas Publishing House\"}}]}";
    **/
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Constructor for QueryUtils.
     */
    private QueryUtils(){

    }

    /**
     * Returns List of Books by using given URL to query API.
     * @param requestURL String containing the URL to make request
     * @return An ArrayList with the books parsed from JSON response
     */
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

    /**
     * Extract the features of the books from the JSON response from the server.
     * @param jsonResponse The response from URL query
     * @return ArrayList with book objects.
     */
    public static ArrayList<Book> extractBooksFeatures(String jsonResponse) {
        ArrayList<Book> books = new ArrayList<>();
        String bookTitle = "";
        String bookAuthor = "";
        String bookPublishingYear = "";
        String bookThumbnail = "";

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray items = baseJsonResponse.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject currentItem = items.getJSONObject(i);
                JSONObject volumeInfo = currentItem.getJSONObject("volumeInfo");
                //Get book's title
                bookTitle = volumeInfo.getString("title");
                //Get the book's authors
                try{
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    StringBuilder authorsStringBuilder = new StringBuilder();
                    authorsStringBuilder.append(authorsArray.getString(0));
                    for(int j=0; j<authorsArray.length(); j++){
                        authorsStringBuilder.append(", ")
                                .append(authorsArray.getString(j));
                    }
                    bookAuthor = authorsStringBuilder.toString();
                }catch (JSONException e){
                    bookAuthor = "Author name not available";
                }
                //Get the book's publishing date
                try{
                    bookPublishingYear = volumeInfo.getString("publishedDate").substring(0,4);
                }catch (JSONException e){
                    bookPublishingYear = "No date available";
                    Log.e("QueryUtils", "Problem parsing the book JSON response", e);
                }
                //Try getting the book cover thumbnail URL
                try{
                    bookThumbnail = volumeInfo.getJSONObject("imageLinks").getString("smallThumbnail");
                }catch (JSONException e){
                    Log.e("QueryUtils", "Problem parsing the book JSON response", e);
                }

                books.add(new Book(bookTitle, bookAuthor, bookPublishingYear, bookThumbnail));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the book JSON response", e);
        }
        return books;
    }

    /**
     * Creates the URL from a give string
     * @param stringURL The string to be converted
     * @return URL object
     */
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

    /**
     * Makes HTTP request
     * @param url URL object to be used for connection
     * @return jsonResponse as String obtained from query.
     * @throws IOException If HTTP connection files
     */
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
     * Convert {@link InputStream} into a String which contains the whole JSON response from the server
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
