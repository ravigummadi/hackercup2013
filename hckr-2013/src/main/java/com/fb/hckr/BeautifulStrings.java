package com.fb.hckr;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ravigummadi
 * Date: 1/25/13
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeautifulStrings {


    public static void main(String[] args){
        try{
            List<String> allLines = FileUtils.readLines(new File("src/main/resources/input.txt"));
            List<String> allOutput = new ArrayList<String>();
            BeautifulStrings bs = new BeautifulStrings();
            bs.processLines(allLines,allOutput);
            FileUtils.writeLines(new File("src/main/resources/output.txt"),allOutput);
            System.out.println("Completed Successfully");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void processLines(List<String> allLines, List<String> allOutput){
        int totalLines = Integer.parseInt(allLines.get(0));
        if(totalLines < 5 || totalLines > 50){
            throw new IllegalArgumentException("Number of lines exceed the constraint");
        }
        for(int i=1; i<= totalLines; i++){
            int beautyScore = calculateBeautyScore(allLines.get(i));
            allOutput.add(new String("Case #" + i + ": " + beautyScore));
        }
    }

    private int calculateBeautyScore(String input){
        if(input.length() > 500 || input.length() < 2){
            throw new IllegalArgumentException("Input length is greater than 500 or less than 2");
        }

        Map<Character,Integer> charCountMap = new HashMap<Character, Integer>();
        updateCharCountMap(input, charCountMap);
        return calculateScoreFromMap(charCountMap);
    }

    private void updateCharCountMap(String input, Map<Character,Integer> charCountMap){
        input = input.toLowerCase();
        for(Character c: input.toCharArray()){
            if(c < 'a' || c > 'z'){
                continue;
            }
            if(charCountMap.get(c) == null){
                charCountMap.put(c,1);
            }else{
                int count = charCountMap.get(c);
                charCountMap.put(c,++count);
            }
        }
    }

    private int calculateScoreFromMap(Map<Character,Integer> charCountMap){
        List<Integer> countList = new ArrayList<Integer>(charCountMap.values());
        Collections.sort(countList);
        int score = 0;
        int val = 26;
        for(int i=countList.size()-1; i >= 0; i--){
            score += val*countList.get(i);
            val--;
        }
        return score;
    }





}
