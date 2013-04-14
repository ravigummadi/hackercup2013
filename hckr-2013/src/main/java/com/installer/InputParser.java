package com.installer;

import com.installer.command.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ravigummadi
 * Date: 1/31/13
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class InputParser {

    InstallerState state;
    List<String> inputList;

    public InputParser(List<String> inputList){
        state = new InstallerState();
        this.inputList = inputList;
    }

    public void parse(){
        for(String command : inputList){
            if(command.equals("END")){
                return;
            }
            ICommand commandObj = parseLine(command);
            commandObj.validate(command);
            commandObj.parse(command,state);
        }
    }

    private ICommand parseLine(String command){
        String[] splitArray = command.split(" +");
        if(splitArray.length < 1){
            throw new IllegalArgumentException();
        }else{
            System.out.println(command);
            String commandName = splitArray[0];
            if(commandName.equals("INSTALL")){
                return new InstallCommand();
            }else if(commandName.equals("LIST")){
                return new ListCommand();
            }else if(commandName.equals("REMOVE")){
                return new RemoveCommand();
            }else if(commandName.equals("DEPEND")){
                return new DependCommand();
            }else{
                throw new IllegalArgumentException("Cannont determine the command!");
            }
        }
    }



}
