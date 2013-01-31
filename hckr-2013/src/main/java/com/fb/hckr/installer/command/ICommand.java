package com.fb.hckr.installer.command;

import com.fb.hckr.installer.InstallerState;

/**
 * Created with IntelliJ IDEA.
 * User: ravigummadi
 * Date: 1/31/13
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICommand {
    /**
     * Validates the arguements of a command
     * @param str arguments
     * @throws IllegalArgumentException
     */
    public void validate(String str) throws IllegalArgumentException;

    /**
     * Updates the state of installer based on the arguments of a command
     * @param str arguments of a command
     * @param state
     */
    public void parse(String str, InstallerState state);
}
