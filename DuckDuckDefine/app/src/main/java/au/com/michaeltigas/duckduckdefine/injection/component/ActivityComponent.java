package au.com.michaeltigas.duckduckdefine.injection.component;

import au.com.michaeltigas.duckduckdefine.feature.definition.DefinitionActivity;
import au.com.michaeltigas.duckduckdefine.feature.search.SearchActivity;
import au.com.michaeltigas.duckduckdefine.injection.module.ActivityModule;
import au.com.michaeltigas.duckduckdefine.injection.scope.PerActivity;
import dagger.Component;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Provide dependencies to Activity/Fragment classes listed with an interface method below
 */
@PerActivity
@Component(
    dependencies = ApplicationComponent.class,
    modules = ActivityModule.class
)
public interface ActivityComponent {
    // Activity classes
    void inject(SearchActivity searchActivity);
    void inject(DefinitionActivity definitionActivity);
}
