package io.github.sdxqw.winzcmd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is annotation help us to make our commands
 * Really simple to use and useful
 * @author sdxqw
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {
    // The name of the command
    String name();
    // The id of the command
    int id();
}
