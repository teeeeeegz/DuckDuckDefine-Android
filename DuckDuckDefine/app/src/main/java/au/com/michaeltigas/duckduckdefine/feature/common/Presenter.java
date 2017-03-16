package au.com.michaeltigas.duckduckdefine.feature.common;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Common base interface, to share common methods amongst presenters of base-class-type MvpView
 */
public interface Presenter<V extends MvpView> {
    void attachView(V mvpView);
    void detachView();
}
