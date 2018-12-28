package tirzad.starunique.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import static tirzad.starunique.booklistingapp.BookActivity.LOG_TAG;

/**
 * Created by StarUnique on 14/08/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private String mUrlRequest;

    public BookLoader(Context context, String urlRequest) {
        super(context);
        mUrlRequest = urlRequest;
    }

    @Override
    public List<Book> loadInBackground() {
        Log.e(LOG_TAG, "Start Processing with url address: \n" + mUrlRequest);

        List<Book> books = QueryUtils.fetchBookData(mUrlRequest);
        return books;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
