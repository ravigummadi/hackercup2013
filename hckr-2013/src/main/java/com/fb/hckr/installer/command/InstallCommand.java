package com.fb.hckr.installer.command;

import com.fb.hckr.installer.InstallerState;

/**
 * Created with IntelliJ IDEA.
 * User: ravigummadi
 * Date: 1/31/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstallCommand implements ICommand {

    @Override
    public void validate(String command) throws IllegalArgumentException {

    }

    @Override
    public void parse(String str, InstallerState state) {
        String[] splitArray = str.split(" +");
        if(splitArray.length != 2){
            throw new IllegalArgumentException("Incorrect number of arguments to Intall");
        }else{
            state.installProgram(splitArray[1]);
        }
    }
}
