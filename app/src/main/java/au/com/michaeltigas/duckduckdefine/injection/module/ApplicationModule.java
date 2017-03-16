package au.com.michaeltigas.duckduckdefine.injection.module;

import android.app.Application;
import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import au.com.michaeltigas.duckduckdefine.injection.context.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Provide Application level dependencies in this module, such as singleton objects for injection
 * in any Activity, Fragment, Service etc..
 */
@Module
public class ApplicationModule {
    final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    /**
     * Provide instance of Application class
     *
     * @return Instance of Application
     */
    @Provides
    Application provideApplication() {
        return application;
    }

    /**
     * Provide instance of application-level Context
     *
     * @return Instance of Context in reference to the Application object
     */
    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    /**
     * Provide instance of Picasso
     *
     * Picasso is set to use the OkHttp3 library as its downloader
     *
     * @param application Application object
     * @param client Instance of OkHttpClient
     *
     * @return Instance of Picasso
     */
    @Provides @Singleton
    Picasso providePicasso(Application application, OkHttpClient client) {
        return new Picasso.Builder(application)
                .downloader(new OkHttp3Downloader(client))
                .listener((picasso, uri, e) -> Timber.e(e, "Failed to load image: %s", uri))
                .build();
    }
}
