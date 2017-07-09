package au.com.michaeltigas.duckduckdefine.app;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import au.com.michaeltigas.duckduckdefine.BuildConfig;
import au.com.michaeltigas.duckduckdefine.injection.component.ApplicationComponent;
import au.com.michaeltigas.duckduckdefine.injection.component.DaggerApplicationComponent;
import au.com.michaeltigas.duckduckdefine.injection.module.ApplicationModule;
import timber.log.Timber;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Global Application Class
 */
public class DuckDuckDefineApplication extends Application {
    ApplicationComponent applicationComponent;

    /**
     * Return an instance of the DuckDuckDefineApplication class
     *
     * @param context
     * @return Singleton instance of DuckDuckDefineApplication class
     */
    public static DuckDuckDefineApplication getDuckDuckDefineApplication(Context context) {
        return (DuckDuckDefineApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initialiseDependencyInjection();

        if (BuildConfig.DEBUG) {
            initTimberDebugPlant();
        }

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * Attach Application class to DependencyInjection
     */
    private void initialiseDependencyInjection() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    /**
     * Return an instance of this class' instantiated ApplicationComponent object
     *
     * @return Instance of ApplicationComponent
     */
    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    /**
     * Set the ApplicationComponent object to the parameter ApplicationComponent object.
     * This method is only used when passing a Mock instance of ApplicationComponent for test purposes
     *
     * @param applicationComponent Instance of ApplicationComponent
     */
    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    /**
     * Attach a Timber debug tree, to enable manipulation of log output text
     */
    private void initTimberDebugPlant() {
        Timber.plant(new Timber.DebugTree() {

            /**
             * Overrides the log statement output, and prefix's the Line Number in focus
             *
             * @param element Line Number
             * @return Log Output
             */
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ':' + element.getLineNumber();
            }
        });
    }
}
