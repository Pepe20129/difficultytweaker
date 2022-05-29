package io.github.pepe20129.difficultytweaker.utils.annotations;

import io.github.pepe20129.difficultytweaker.utils.CommandRegistration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares the minimum and maximum values that an {@link Integer} field can take<br>
 * <br>
 * Note that as a limitation of Java, this only accepts constant values,<br>
 * for non-constant values, use {@link CommandRegistration#specialValueModifiers}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BoundedInteger {
	int min() default Integer.MIN_VALUE;
	int max() default Integer.MAX_VALUE;
}