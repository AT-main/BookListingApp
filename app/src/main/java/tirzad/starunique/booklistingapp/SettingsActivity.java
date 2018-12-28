package tirzad.starunique.booklistingapp;

import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class BookListingPreferenceFragment
            extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference searchTerm = findPreference(getString(R.string.settings_search_term_key));
            searchTerm.setOnPreferenceChangeListener(this);
            searchTerm.setSummary(PreferenceManager
                    .getDefaultSharedPreferences(searchTerm.getContext())
                    .getString(searchTerm.getKey(), ""));

            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            orderBy.setOnPreferenceChangeListener(this);
            orderBy.setSummary(PreferenceManager.getDefaultSharedPreferences(orderBy.getContext())
                    .getString(orderBy.getKey(), ""));

            Preference printType = findPreference(getString(R.string.settings_print_type_key));
            printType.setOnPreferenceChangeListener(this);
            printType.setSummary(PreferenceManager.getDefaultSharedPreferences(printType.getContext())
                    .getString(printType.getKey(), ""));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {

            String stringValue = o.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else preference.setSummary(stringValue);

            return true;
        }
    }
}
