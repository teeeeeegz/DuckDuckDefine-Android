package au.com.michaeltigas.duckduckdefine.injection.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * Provide scoping of the ActivityComponent interface for ActivityModule module dependencies
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
