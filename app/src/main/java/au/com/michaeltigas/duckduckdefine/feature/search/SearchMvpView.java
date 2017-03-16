package au.com.michaeltigas.duckduckdefine.feature.search;

import au.com.michaeltigas.duckduckdefine.data.remote.model.SearchDefinition;
import au.com.michaeltigas.duckduckdefine.feature.common.MvpView;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Interface to connect the following classes: SearchActivity and SearchPresenter
 */
public interface SearchMvpView extends MvpView {
    void showSearchInProgress();
    void showSearchDefinition(String term, SearchDefinition searchDefinition);
    void displayLastSearchTerm(String lastSearchTerm);
    void handleErrorResult(String message);
}
