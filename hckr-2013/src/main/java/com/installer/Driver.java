package com.installer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ravigummadi
 * Date: 1/31/13
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class Driver {

    public static void main(String[] args){
        try{
            List<String> allLines = FileUtils.readLines(new File("src/main/resources/installer-input.txt"));
            List<String> allOutput = new ArrayList<String>();
            InputParser inputParser = new InputParser(allLines);
            inputParser.parse();
            FileUtils.writeLines(new File("src/main/resources/installer-output.txt"),allOutput);
            System.out.println("Completed Successfully");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

}
