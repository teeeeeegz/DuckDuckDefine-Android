package au.com.michaeltigas.duckduckdefine.feature.common;

import android.support.v7.app.AppCompatActivity;

import au.com.michaeltigas.duckduckdefine.app.DuckDuckDefineApplication;
import au.com.michaeltigas.duckduckdefine.injection.component.ActivityComponent;
import au.com.michaeltigas.duckduckdefine.injection.component.DaggerActivityComponent;
import au.com.michaeltigas.duckduckdefine.injection.module.ActivityModule;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Common base class for all Activity classes to extend from, providing common functionality
 */
public class BaseActivity extends AppCompatActivity {
    private ActivityComponent activityComponent;

    /**
     * Return an instance of ActivityComponent, to interface with injectable components via Dagger
     *
     * @return Instance of ActivityComponent
     */
    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(DuckDuckDefineApplication.getDuckDuckDefineApplication(this).getApplicationComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }

        return activityComponent;
    }
}
