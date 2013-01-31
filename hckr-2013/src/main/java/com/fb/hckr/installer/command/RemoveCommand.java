package com.fb.hckr.installer.command;

import com.fb.hckr.installer.InstallerState;

/**
 * Created with IntelliJ IDEA.
 * User: ravigummadi
 * Date: 1/31/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoveCommand implements ICommand {
    @Override
    public void validate(String str) throws IllegalArgumentException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void parse(String str, InstallerState state) {
        String[] splitArray = str.split(" +");
        if(splitArray.length != 2){
            throw new IllegalArgumentException("Incorrect number of arguments to Remove");
        }else{
            state.removeProgram(splitArray[1]);
        }
    }
}
