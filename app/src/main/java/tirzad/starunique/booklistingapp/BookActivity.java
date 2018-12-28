package tirzad.starunique.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = BookActivity.class.getName();
    private static String GOOGLE_BOOKS_URL;
//    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
//        index = 0;

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?";
//            index++;
            getLoaderManager().initLoader(/*index*/ 1, null, this);

        } else {
            Toast.makeText(this, "NO Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String searchTerm = sharedPrefs.getString(
                getString(R.string.settings_search_term_key), "java");

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),"newest");

        String printType = sharedPrefs.getString(
                getString(R.string.settings_print_type_key), "all");

        Uri baseUri = Uri.parse(GOOGLE_BOOKS_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", searchTerm);
        uriBuilder.appendQueryParameter("orderBy", orderBy);
        uriBuilder.appendQueryParameter("printType", printType);

        return new BookLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        updateUi(books);

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        updateUi(new ArrayList<Book>());
    }

    private void updateUi(List<Book> books) {

        final BookAdapter adapter = new BookAdapter(
                BookActivity.this, books);
        ListView booksListView = (ListView) findViewById(R.id.list);
//        booksListView.removeAllViews();

        booksListView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        // Retrieve the SearchView and plug it into SearchManager

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setttings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

