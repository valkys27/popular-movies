package com.udacity.popularmovies.dagger.scope;

import java.lang.annotation.*;

import javax.inject.Scope;

/**
 * Created by tomas on 17.03.2018.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApplication {
}
