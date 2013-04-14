package com.installer.command;

import com.installer.InstallerState;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: ravigummadi
 * Date: 1/31/13
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class DependCommand implements ICommand {



    @Override
    public void validate(String command) throws IllegalArgumentException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void parse(String str, InstallerState state) {
        String[] splitArray = str.split(" +");
        if(splitArray.length < 3){
            throw new IllegalArgumentException("Depend takes 2 or more arguments");
        }else{
            String program = splitArray[1];
            state.updateDependency(program,
                    new HashSet<String>(Arrays.asList(Arrays.copyOfRange(splitArray, 2, splitArray.length))));
        }
    }
}
