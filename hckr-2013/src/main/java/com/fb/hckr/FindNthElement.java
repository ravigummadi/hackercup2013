package com.fb.hckr;

import com.google.common.primitives.Ints;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ravigummadi
 * Date: 1/26/13
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class FindNthElement {

    public static void main(String[] args){
        try{
            List<String> allLines = FileUtils.readLines(new File("src/main/resources/input.txt"));
            List<String> allOutput = new ArrayList<String>();
            FindNthElement fne = new FindNthElement();
            fne.process(allLines, allOutput);
            FileUtils.writeLines(new File("src/main/resources/output.txt"),allOutput);
            System.out.println("Completed Successfully");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void process(List<String> inputList, List<String> outputList){
        int count = Integer.parseInt(inputList.get(0));
        if(count > 20){
            throw new IllegalArgumentException("Input set is more than 20");
        }
        for(int i=1,j=1;i<=count;i++,j=j+2){
            String firstLine = inputList.get(j);
            String secondLine = inputList.get(j+1);
            int result = processOneInput(firstLine, secondLine);
            String outputStr = String.format("Case #%s: %s",i,result);
            System.out.println(outputStr);
            outputList.add(outputStr);
        }
    }

    private int processOneInput(String firstLine, String secondLine){
        String[] firstLineArr = firstLine.split(" ");
        int n = Integer.parseInt(firstLineArr[0]);
        int k = Integer.parseInt(firstLineArr[1]);

        String[] secondLineArr = secondLine.split(" ");
        int a = Integer.parseInt(secondLineArr[0]);
        int b = Integer.parseInt(secondLineArr[1]);
        int c = Integer.parseInt(secondLineArr[2]);
        int r = Integer.parseInt(secondLineArr[3]);

        try{
            checkBoundaries(n,k,a,b,c,r);
        }catch(IllegalArgumentException ile){
            return 0;
        }

        int[] m = initializeAndPopulateArray(n,k,a,b,c,r);
        populateFromKtoN(m,k,n);
        return m[n-1];
    }

    private void checkBoundaries(int n, int k, int a, int b, int c,int r){
        if(k < 1 || k > Math.pow(10,5)){
            throw new IllegalArgumentException("k is out of boundary");
        }
        if(n < k || n > Math.pow(10,9)){
            throw new IllegalArgumentException("n is out of boundary");
        }
        if(a < 0 || b < 0 || c < 0 || r < 1 ||
                a > Math.pow(10,9) || b > Math.pow(10,9) || c > Math.pow(10,9) || r > Math.pow(10,9)){
            throw new IllegalArgumentException("One of (a,b,c,r) is out of boundary");
        }
    }

    private int[] initializeAndPopulateArray(int n, int k, int a, int b, int c, int r){
        int[] m = new int[n];
        m[0] = a;
        for(int i=1; i < k; i++){
            m[i] = (b * m[i-1] + c) % r;
        }
        return m;
    }

    private void populateFromKtoN(int[] m,int k, int n){
        for(int i=k; i<n; i++){
            m[i] = getLowestAvailable(m,i-k,i);
        }
    }

    private int getLowestAvailable(int[] m, int low, int high){
        int[] choppedM = Arrays.copyOfRange(m, low, high);
        SortedSet<Integer> uniqueSet = new TreeSet<Integer>(Ints.asList(choppedM));
        int lowestAvaliable = 1;
        for(int current : uniqueSet){
            if(current<=0) continue;
            if(current != lowestAvaliable){
                return lowestAvaliable;
            }else{
                lowestAvaliable = current+1;
            }
        }
        return lowestAvaliable;
    }

}
