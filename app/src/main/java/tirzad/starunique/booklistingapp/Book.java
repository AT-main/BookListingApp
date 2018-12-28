package tirzad.starunique.booklistingapp;

/**
 * Created by StarUnique on 14/08/2017.
 */

public class Book {

    /**
     * Created by StarUnique on 13/08/2017.
     */

    private String mTitle;
    private String mAuthor;
    private String mYear;
    private int mPages;
    private String mUrl;

    public Book(String title, String author, String year, int pages, String url) {
        mTitle = title;
        mAuthor = author;
        mYear = year;
        mPages = pages;
        mUrl = url;

    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getYear() {
        return mYear;
    }

    public int getPages() {
        return mPages;
    }

    public String getUrl() {
        return mUrl;
    }
}


