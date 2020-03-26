package com.ringov.yamblzweather.navigation.base;

/**
 * Implement this interface in class, that handles app navigation (usually activity class)
 */
public interface Navigator {

    /**
     * @return true, if command was executed, false, if wasn't.
     */
    boolean executeCommand(Command command);
}
