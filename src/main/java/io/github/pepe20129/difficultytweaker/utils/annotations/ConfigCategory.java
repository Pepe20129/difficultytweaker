package io.github.pepe20129.difficultytweaker.utils.annotations;

import io.github.pepe20129.difficultytweaker.utils.Config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares the category a {@link Config.ConfigEntry} belongs to
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfigCategory {
	String value();
}