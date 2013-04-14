package com.google.codejam;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: gummadi
 * Date: 4/13/13
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Lawnmower {

    public static void main(String[] args){
        try{
            List<String> allLines = FileUtils.readLines(new File("src/main/resources/input.txt"));
            List<String> allOutput = new ArrayList<String>();
            Lawnmower bs = new Lawnmower();
            bs.process(allLines,allOutput);
            FileUtils.writeLines(new File("src/main/resources/output.txt"),allOutput);
            System.out.println("Completed Successfully");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void process(List<String> input, List<String> output){
        int totalLawns = Integer.parseInt(input.get(0));
        input.remove(0);
        for(int i=1; i <= totalLawns; i++){
            String[] dims = input.get(0).split(" ");
            int x = Integer.parseInt(dims[0]);
            int y = Integer.parseInt(dims[1]);
            input.remove(0);
            List<String> configList = getNextXString(input,x);
            Lawn lawn = new Lawn(x,y,configList);
            boolean isValid = lawn.isValid();
            System.out.println(isValid);
            output.add("Case #" + i + ": " + (isValid ? "YES" : "NO"));
        }
    }

    private List<String> getNextXString(List<String> input, int x){
        Iterator<String> inputIterator = input.iterator();
        List<String> result = new ArrayList<String>();
        int i=0;
        while(i<x && inputIterator.hasNext()){
            result.add(inputIterator.next());
            inputIterator.remove();
            i++;
        }
        return result;
    }

}

class Lawn{

    int[][] dims;
    int rows,columns;

    public Lawn(int x, int y, List<String> configuration){
        rows = x;
        columns = y;
        dims = new int[x][y];
        populateLawn(configuration);
    }

    private void populateLawn(List<String> configuration){
        for(int i=0; i < rows; i++){
            String current = configuration.get(i);
            String[] splitArr = current.split(" ");
            for(int j=0; j < columns; j++){
                dims[i][j] = Integer.parseInt(splitArr[j]);
            }
        }
    }

    public boolean isValid(){
        for(int i=0; i < rows; i++){
            if(!checkRow(i))
                return false;
        }
        return true;
    }

    private boolean checkRow(int row){
        if(!isRowSameHeight(row)){
            for(int i=0; i < columns; i++){
                if(dims[row][i] == 1){
                    if(!isColumnSameHeight(i))
                        return false;
                }
            }
        }
        return true;
    }

    private boolean isRowSameHeight(int row){
        Set<Integer> nums = new HashSet<Integer>();
        for(int i=0; i < columns; i++){
            nums.add(dims[row][i]);
        }
        return nums.size() == 1;
    }

    private boolean isColumnSameHeight(int column){
        Set<Integer> nums = new HashSet<Integer>();
        for(int i=0; i < rows; i++){
            nums.add(dims[i][column]);
        }
        return nums.size() == 1;
    }

}
