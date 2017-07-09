package au.com.michaeltigas.duckduckdefine.injection.module;

import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import au.com.michaeltigas.duckduckdefine.BuildConfig;
import au.com.michaeltigas.duckduckdefine.data.remote.DuckDuckGoApiService;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Provide Application level Network-related dependencies in this module
 */
@Module
public class NetworkModule {

    /**
     * Provide instance of HttpUrl, with a parsed API Property URL value from the gradle build config
     *
     * @return Instance of HttpUrl
     */
    @Provides @Singleton
    HttpUrl provideBaseUrl() {
        return HttpUrl.parse(BuildConfig.DUCKDUCKGO_API_URL);
    }

    /**
     * Provide instance of Moshi
     *
     * @return Instance of Moshi
     */
    @Provides @Singleton
    Moshi provideMoshi() {
        return new Moshi.Builder()
                .build();
    }

    /**
     * Provide an instance of OkHttpClient for use with API interfaces/libraries
     *
     * @return Initialised instance of OkHttpClient
     */
    @Provides @Singleton
    OkHttpClient provideOkHttpClient() {
        // Enable/Disable HTTP log statements
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();
    }

    /**
     * Provide instance of Retrofit, utilising Moshi as the JSON converter to connect
     * to the DuckDuckGoAPI
     *
     * @param baseUrl HttpUrl instance
     * @param client OkHttpClient instance
     * @param moshi Moshi instance
     * @return Instance of Retrofit
     */
    @Provides @Singleton
    Retrofit provideRetrofit(HttpUrl baseUrl, OkHttpClient client, Moshi moshi) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * Provide instance of Retrofit, interfaced with DuckDuckGoApiService
     *
     * @param retrofit Instance of Retrofit
     * @return Instance of Retrofit DuckDuckGoApiService interface
     */
    @Provides @Singleton DuckDuckGoApiService provideDuckDuckGoApiService(Retrofit retrofit) {
        return retrofit.create(DuckDuckGoApiService.class);
    }
}
