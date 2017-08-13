package com.ringov.yamblzweather.navigation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ringov.yamblzweather.navigation.base.Command;
import com.ringov.yamblzweather.navigation.base.Navigator;
import com.ringov.yamblzweather.navigation.base.NavigatorBinder;
import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.navigation.commands.CommandNavigatorAttached;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.subjects.PublishSubject;

/**
 * Manages app navigation, lifecycle tied to app process. Can hold only one navigator at a time.
 * When Router receives command, it will be executed by current navigator or added to queue,
 * if there is no navigator attached or current navigator can not execute it.
 * When new navigator get attached, holder will try to execute queued commands.
 */
public class RouterHolder implements Router, NavigatorBinder {

    private PublishSubject<Command> commandsFeed;

    @Nullable
    public Navigator navigator;

    public CopyOnWriteArrayList<Command> commandsQueue;

    public RouterHolder() {
        commandsFeed = PublishSubject.create();
        navigator = null;
        commandsQueue = new CopyOnWriteArrayList<>();

        subscribeToCommandsFeed();
    }

    private void subscribeToCommandsFeed() {
        commandsFeed
                .toSerialized()
                .filter(command -> !runCommand(command)) // If command executed, skip
                .filter(command -> !runQueue(command)) // If new navigator attached, skip
                .subscribe(this::addToQueue);
    }

    // Router
    @Override
    public void execute(@NonNull Command command) {
        commandsFeed.onNext(command);
    }

    // NavigatorBinder
    @Override
    public void setNavigator(@NonNull Navigator navigator) {
        this.navigator = navigator;
        commandsFeed.onNext(new CommandNavigatorAttached());
    }

    @Override
    public void removeNavigator() {
        navigator = null;
    }

    // Private logic

    /**
     * @return If navigator = null returns false, if command executed - returns true,
     * if command cannot be executed - false.
     */
    private boolean runCommand(Command command) {
        return navigator != null && navigator.executeCommand(command);
    }

    /**
     * @return true, if new navigator has been attached (tries to execute all commands in queue)
     * else returns false. (Without queue execution)
     */
    private boolean runQueue(Command command) {
        if (command instanceof CommandNavigatorAttached) {
            // Tries to execute commands queue, if success, remove executed command from queue
            for (Command c : commandsQueue)
                if (runCommand(c))
                    removeFromQueue(c);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true, if command was added to queue, else false.
     */
    private boolean addToQueue(Command command) {
        if (command.addToQueue) {
            if (!commandsQueue.isEmpty())
                command.id = commandsQueue.get(commandsQueue.size() - 1).id + 1;
            return commandsQueue.add(command);
        } else {
            return false;
        }
    }

    /**
     * @return true, if command removed successfully, else false
     */
    private boolean removeFromQueue(Command command) {
        return commandsQueue.remove(command);
    }
}
