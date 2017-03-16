package au.com.michaeltigas.duckduckdefine.injection.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import au.com.michaeltigas.duckduckdefine.injection.context.ActivityContext;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Provide Activity-related class dependencies in this module (including Fragments)
 */
@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    AppCompatActivity provideAppCompatActivity() {
        return (AppCompatActivity) activity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return activity;
    }

    @Provides
    FragmentManager providesFragmentManager(AppCompatActivity activity) {
        return activity.getSupportFragmentManager();
    }
}
