package au.com.michaeltigas.duckduckdefine.feature.common;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Common base class for Presenters to extend from, of type MvpView, providing common functionality
 * This enables a presenter class to attach an MvpView interface and detatch from it
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {
    private T mvpView;

    @Override
    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public T getMvpView() {
        return mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before " +
                    "requesting data to the Presenter");
        }
    }
}
