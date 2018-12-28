package tirzad.starunique.booklistingapp;

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
import java.util.List;

import static tirzad.starunique.booklistingapp.BookActivity.LOG_TAG;

/**
 * Created by StarUnique on 14/08/2017.
 */

public final class QueryUtils {


    private QueryUtils() {

    }

    public static List<Book> fetchBookData(String requestUrl) {
        List<Book> books = null;
        Log.e(LOG_TAG, "Start Processing with url address: \n" + requestUrl);

        URL urlAddress = createURL(requestUrl);
        String response = null;
        try {
            response = makeHTTPRequest(urlAddress);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        books = extractBooksFromString(response);
        return books;
    }

    private static URL createURL(String urlAddress) {
        URL url = null;
        try {
            url = new URL(urlAddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonString = "";

        if (url == null) return jsonString;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonString = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error respose code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to make a connection!");

//            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();

            if (inputStream != null) inputStream.close();
        }


        return jsonString;
    }

    private static String readFromStream(InputStream is) throws IOException {

        StringBuilder sb = new StringBuilder();

        if (is != null) {
            InputStreamReader isReader = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader bReader = new BufferedReader(isReader);
            String line = bReader.readLine();
            while (line != /*""*/null) {
                sb.append(line);
                line = bReader.readLine();
            }
        }
        return sb.toString();
    }

    private static ArrayList<Book> extractBooksFromString(String jsonString) {
        ArrayList<Book> extractedBooks = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(jsonString);
            JSONArray itemsArray = rootObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject item = itemsArray.getJSONObject(i);
                JSONObject infoObject = item.getJSONObject("volumeInfo");

                String title = "N/A";
                String author = "N/A";
                String year = "N/A ";
                int pages = -1;
                String url = "";

                if (infoObject.has("title")) title = infoObject.getString("title");
                if (infoObject.has("authors"))
                    author = (String) infoObject.getJSONArray("authors").get(0);
                if (infoObject.has("publishedDate"))
                    year = (infoObject.getString("publishedDate")).substring(0, 4);
                if (infoObject.has("pageCount")) pages = infoObject.getInt("pageCount");
                if (infoObject.has("infoLink")) url = infoObject.getString("infoLink");

                extractedBooks.add(new Book(title, author, year, pages, url));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);

        }


        return extractedBooks;

    }
}
