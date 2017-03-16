package au.com.michaeltigas.duckduckdefine.data;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import au.com.michaeltigas.duckduckdefine.data.local.PreferencesHelper;
import au.com.michaeltigas.duckduckdefine.data.remote.DuckDuckGoApiService;
import au.com.michaeltigas.duckduckdefine.data.remote.model.SearchDefinition;
import io.reactivex.Flowable;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Responsible for facilitating communication between business logic (such as Network, Storage, etc.),
 * and Presenter classes
 */
@Singleton
public class DataManager {
    private final DuckDuckGoApiService duckDuckGoApiService;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public DataManager(DuckDuckGoApiService duckDuckGoApiService,
                       PreferencesHelper preferencesHelper) {
        this.duckDuckGoApiService = duckDuckGoApiService;
        this.preferencesHelper = preferencesHelper;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////  ========== PreferencesHelper ONLY METHODS ========== ///////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Return the last search term
     *
     * @return
     */
    public String getLastSearchTerm() {
        return preferencesHelper.getLastSearch();
    }

    /**
     * Update the last search term
     *
     * @param term
     */
    public void setLastSearchTerm(String term) {
        preferencesHelper.setLastSearch(term);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////  ========== DuckDuckGoApiService ONLY METHODS ========== //////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Send a search term request to get a SearchDefinition response
     *
     * @param searchTerm
     * @return
     */
    public Flowable<SearchDefinition> getSearchResults(String searchTerm) {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("q", searchTerm);
        parameters.put("format", "json");
        parameters.put("pretty", "1");
        parameters.put("no_html", "1");
        parameters.put("skip_disambig", "1");

        return duckDuckGoApiService.searchRequest(parameters);
    }
}
