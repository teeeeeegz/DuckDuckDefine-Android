package au.com.michaeltigas.duckduckdefine.injection.component;

import android.app.Application;
import android.content.Context;

import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import au.com.michaeltigas.duckduckdefine.app.DuckDuckDefineApplication;
import au.com.michaeltigas.duckduckdefine.data.DataManager;
import au.com.michaeltigas.duckduckdefine.data.local.PreferencesHelper;
import au.com.michaeltigas.duckduckdefine.data.remote.DuckDuckGoApiService;
import au.com.michaeltigas.duckduckdefine.injection.context.ApplicationContext;
import au.com.michaeltigas.duckduckdefine.injection.module.ApplicationModule;
import au.com.michaeltigas.duckduckdefine.injection.module.NetworkModule;
import dagger.Component;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Provide references to dependencies that have been defined in the component modules, to enable
 * access via @Inject field annotation injection, or class constructor injection
 */
@Singleton
@Component( modules = {
    ApplicationModule.class,
    NetworkModule.class
})
public interface ApplicationComponent {
    void inject(DuckDuckDefineApplication duckDuckDefineApplication);

    @ApplicationContext Context context();
    Application application();

    // Architecture-based helpers
    DataManager dataManager();
    PreferencesHelper preferencesHelper();

    // Network related
    Moshi moshi();
    HttpUrl httpUrl();
    Retrofit retrofit();
    OkHttpClient okHttpClient();
    DuckDuckGoApiService duckDuckGoApiService();

    // Custom services
    Picasso picasso();
}
