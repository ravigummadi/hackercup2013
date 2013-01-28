package com.fb.hckr;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ravigummadi
 * Date: 1/25/13
 * Time: 10:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class BalancedSmileys {

    public static void main(String[] args){
        try{
            List<String> allLines = FileUtils.readLines(new File("src/main/resources/input.txt"));
            List<String> allOutput = new ArrayList<String>();
            BalancedSmileys bs = new BalancedSmileys();
            bs.process(allLines,allOutput);
            FileUtils.writeLines(new File("src/main/resources/output.txt"),allOutput);
            System.out.println("Completed Successfully");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }


    private void process(List<String> inputList, List<String> outputList){
        int count = Integer.parseInt(inputList.get(0));
        for(int i=1;i<= count;i++){
            String currentLine = inputList.get(i);
            String output = isValidString(currentLine,0,0,0) ? "YES" : "NO";
            outputList.add(String.format("Case #%s: %s", i + "", output));
        }
    }

    private boolean isValidString(String input,int currentIndex, int openBraceCount, int closeBraceCount){

        for(int i=currentIndex; i < input.length(); i++){
            char c = input.charAt(i);
            if(c == '('){
                openBraceCount++;
            }else if(c == ')'){
                closeBraceCount++;
                if(closeBraceCount > openBraceCount){
                    return false;
                }
            }else if(c == ':'){
                if(i+1 < input.length()){
                    char next = input.charAt(i+1);
                    if(next == ')'){
                        return isValidString(input,i+2,openBraceCount,closeBraceCount) ||
                                isValidString(input,i+1,openBraceCount,closeBraceCount);
                    }else if(next == '('){
                        return isValidString(input,i+2,openBraceCount,closeBraceCount) ||
                                isValidString(input,i+1,openBraceCount,closeBraceCount);
                    }
                }
                continue;
            }else if( (c >= 'a' && c <= 'z') ||  c == ' '){
                continue;
            }else{
                return false;
            }
        }

        if(openBraceCount == closeBraceCount){
            return true;
        }
        else{
            return false;
        }
    }

}
