package au.com.michaeltigas.duckduckdefine.feature.search;

import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import au.com.michaeltigas.duckduckdefine.data.DataManager;
import au.com.michaeltigas.duckduckdefine.data.remote.model.SearchDefinition;
import au.com.michaeltigas.duckduckdefine.feature.common.BasePresenter;
import au.com.michaeltigas.duckduckdefine.util.ErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Presenter that handles business logic for SearchActivity
 */
public class SearchPresenter extends BasePresenter<SearchMvpView> {
    private final DataManager dataManager;
    private final CompositeDisposable disposables;

    @Inject
    public SearchPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
        disposables = new CompositeDisposable();
    }

    /**
     * Dispose of disposable instance
     */
    @Override
    public void detachView() {
        super.detachView();
        if (!disposables.isDisposed()) disposables.clear();
    }

    /**
     * Perform search based on param
     *
     * @param term
     */
    public void performSearch(String term) {
        Preconditions.checkNotNull(disposables);

        disposables.add(
            dataManager.getSearchResults(term)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(subscription -> getMvpView().showSearchInProgress())
                    .subscribeWith(new DisposableSubscriber<SearchDefinition>() {
                        @Override
                        public void onNext(SearchDefinition searchDefinition) {
                            getMvpView().showSearchDefinition(term, searchDefinition);
                        }

                        @Override
                        public void onError(Throwable t) {
                            handleRequestError(t, "performSearch()");
                        }

                        @Override
                        public void onComplete() {
                            // Not used
                        }
                    })
        );
    }

    /**
     * Load last search term and display on UI
     */
    public void loadLastSearchTerm() {
        getMvpView().displayLastSearchTerm(dataManager.getLastSearchTerm());
    }

    /**
     * Save last search term
     *
     * @param term
     */
    public void saveLastSearchTerm(String term) {
        dataManager.setLastSearchTerm(term);
    }

    /**
     * Handle request errors gracefully
     *
     * @param throwable
     */
    private void handleRequestError(Throwable throwable, String methodName) {
        Timber.e("Request Error: Class - '%s', Method - '%s', Reason - %s",
                getClass().getName(), methodName, throwable.getMessage());

        if (ErrorUtil.isIoException(throwable)) {
            if (ErrorUtil.isSSLHandshakeException(throwable)) {
                final String errorMessage = "SSL Authentication error";
                getMvpView().handleErrorResult(errorMessage);
                return;
            }

            final String errorMessage = "Not connected to the internet";
            getMvpView().handleErrorResult(errorMessage);
            return;
        }

        if (ErrorUtil.isHttpException(throwable)) {
            HttpException exception = (HttpException) throwable;

            final String errorMessage = String.format("HTTP %s error", exception.code());
            if (isViewAttached()) getMvpView().handleErrorResult(errorMessage);
        }
    }
}
