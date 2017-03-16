package au.com.michaeltigas.duckduckdefine.feature.search;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fernandocejas.arrow.strings.Strings;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

import au.com.michaeltigas.duckduckdefine.R;
import au.com.michaeltigas.duckduckdefine.data.remote.model.SearchDefinition;
import au.com.michaeltigas.duckduckdefine.feature.common.BaseActivity;
import au.com.michaeltigas.duckduckdefine.feature.definition.DefinitionActivity;
import au.com.michaeltigas.duckduckdefine.util.ViewUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * This activity enables a user to search a term, locally store it in the table, and display a definition of it
 */
public class SearchActivity extends BaseActivity implements SearchMvpView {
    @Inject SearchPresenter searchPresenter;
    @Inject SearchAdapter recyclerAdapter;
    @Inject Moshi moshi;

    @BindView(R.id.searchToolbar)           Toolbar toolbar;
    @BindView(R.id.searchCoordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.searchSwipeRefresh)      SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.searchSearchView)        SearchView searchView;
    @BindView(R.id.searchRecyclerView)      RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager; // RecyclerView layout manager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        searchPresenter.attachView(this);
        init();
    }

    @Override
    protected void onDestroy() {
        searchPresenter.detachView();
        super.onDestroy();
    }

    private void init() {
        initialiseToolbar();

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);

        if (recyclerView.getLayoutManager() == null) {
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        swipeRefreshLayout.setColorSchemeColors(ViewUtil.colorPaletteSwipeRefresh());

        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearchForTerm(query); // Search query when enter pressed
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchPresenter.loadLastSearchTerm();
    }

    private void initialiseToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.activity_search));
    }

    /**
     * Perform search for a term
     *
     * @param term
     */
    public void performSearchForTerm(String term) {
        if (Strings.isNullOrEmpty(term)) return;

        searchPresenter.performSearch(term);
    }

    /**
     * Display a snackbar message
     *
     * @param message
     */
    private void showSnackbarMessage(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////  ========== MVP INTERFACE METHODS ========== ////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Display swipe refresh layout when search in progress
     */
    @Override
    public void showSearchInProgress() {
        ViewUtil.setRefreshing(swipeRefreshLayout, true);

    }

    /**
     * Save search term if matching definition returned, and pass definition details to
     * DefinitionActivity via intent
     *
     * @param term
     * @param definition
     */
    @Override
    public void showSearchDefinition(String term, SearchDefinition definition) {
        ViewUtil.setRefreshing(swipeRefreshLayout, false);

        if (Strings.isNullOrEmpty(definition.heading)
                || Strings.isNullOrEmpty(definition.abstractText)) {

            final String noDefinitionMessage = String.format("No definition could be found for %s. Try searching for something else?", term);
            showSnackbarMessage(noDefinitionMessage);
            return;
        }

        recyclerAdapter.insertItem(term);

        Intent definitionIntent = new Intent(this, DefinitionActivity.class);
        definitionIntent.putExtra("definition", moshi.adapter(SearchDefinition.class).toJson(definition));
        startActivity(definitionIntent);
    }

    /**
     * Display last search term in search bar
     *
     * @param lastSearchTerm
     */
    @Override
    public void displayLastSearchTerm(String lastSearchTerm) {
        searchView.setQuery(lastSearchTerm, false);
    }

    /**
     * Handle error results
     *
     * @param message
     */
    @Override
    public void handleErrorResult(String message) {
        ViewUtil.setRefreshing(swipeRefreshLayout, false);
        showSnackbarMessage(message);
    }
}
