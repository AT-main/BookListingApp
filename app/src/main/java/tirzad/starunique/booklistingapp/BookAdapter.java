package tirzad.starunique.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by StarUnique on 14/08/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }


        final Book currentBook = getItem(position);
//        DecimalFormat formatter = new DecimalFormat("0000");


        TextView yearTextView = (TextView) listItemView.findViewById(R.id.year_tv);
        yearTextView.setText((currentBook.getYear()).substring(0, 4));

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_tv);
        titleTextView.setText(currentBook.getTitle());

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_tv);
        authorTextView.setText(currentBook.getAuthor());

//        I need to be careful to always convert the value of other primary types rather than String
        TextView pagesTextView = (TextView) listItemView.findViewById(R.id.pages_tv);
        if (currentBook.getPages() == -1)
            pagesTextView.setText("N/A");
        else pagesTextView.setText(String.valueOf(currentBook.getPages()));


        return listItemView;
    }
}
