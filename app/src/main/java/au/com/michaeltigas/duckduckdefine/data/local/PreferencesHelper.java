package au.com.michaeltigas.duckduckdefine.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import au.com.michaeltigas.duckduckdefine.BuildConfig;
import au.com.michaeltigas.duckduckdefine.injection.context.ApplicationContext;

/**
 * Access instance of SharedPreferences to get and update keys
 */
@Singleton
public class PreferencesHelper {
    private final SharedPreferences sharedPreferences;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        this.sharedPreferences = context.getSharedPreferences(BuildConfig.SHAREDPREFS_NAME, Context.MODE_PRIVATE);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////  ========== PREFERENCE KEYS ========== ///////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    // Device exclusive preferences
    public static final String KEY_LAST_SEARCH           = "KEY_LAST_SEARCH";

    //////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////  ========== PREFERENCE ACCESSOR METHODS ========== /////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Return saved last search value
     */
    public String getLastSearch() {
        return sharedPreferences.getString(KEY_LAST_SEARCH, "");
    }

    /**
     * Save last search value
     *
     * @param term
     */
    public void setLastSearch(String term) {
        sharedPreferences.edit().putString(KEY_LAST_SEARCH, term).apply();
    }
}
