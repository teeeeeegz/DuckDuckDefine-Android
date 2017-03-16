package au.com.michaeltigas.duckduckdefine.injection.context;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * An annotation used for annotating a module dependency to guarantee returning a type of class object,
 * but specific to the context of an Activity class. There may be many scenarios to inject a
 * common type of class object, but may need differentiation depending context of task
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {
}
