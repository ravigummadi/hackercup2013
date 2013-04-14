package com.installer.command;

import com.installer.InstallerState;

/**
 * Created with IntelliJ IDEA.
 * User: ravigummadi
 * Date: 1/31/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListCommand implements ICommand {
    @Override
    public void validate(String command) throws IllegalArgumentException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void parse(String str, InstallerState state) {
        String[] splitArray = str.split(" +");
        if(splitArray.length != 1){
            throw new IllegalArgumentException("Incorrect number of arguments to LIST");
        }else{
            state.listInstalledPrograms();
        }
    }
}
