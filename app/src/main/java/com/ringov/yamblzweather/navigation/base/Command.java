package com.ringov.yamblzweather.navigation.base;

/**
 * Basic class for navigation commands. Extend from it and add any properties, that you need.
 */
public abstract class Command {

    // Command unique identifier, assigned only if command get queued
    public int id = 0;
    // Should command be added to queue, if it cannot be executed instantly.
    public boolean addToQueue = false;
}
