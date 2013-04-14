package com.google.codejam;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gummadi
 * Date: 4/13/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class TicTacToe {

    public static void main(String[] args){
        try{
            List<String> allLines = FileUtils.readLines(new File("src/main/resources/input.txt"));
            List<String> allOutput = new ArrayList<String>();
            TicTacToe bs = new TicTacToe();
            bs.process(allLines,allOutput);
            FileUtils.writeLines(new File("src/main/resources/output.txt"),allOutput);
            System.out.println("Completed Successfully");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void process(List<String> inputList, List<String> output){
        int totalGames = Integer.parseInt(inputList.get(0));
        for(int i=1; i <= totalGames; i++){
            inputList.remove(0);
            TicTacBoard tacBoard = new TicTacBoard(getNextFourStrings(inputList));
            STATUS status = tacBoard.getGameStatus();
            output.add("Case #" + i + ": " + status);
        }

    }

    private List<String> getNextFourStrings(List<String> inputList){
        Iterator<String> inputIterator = inputList.iterator();
        List<String> result = new ArrayList<String>();
        int i=0;
        while(i<4 && inputIterator.hasNext()){
            result.add(inputIterator.next());
            inputIterator.remove();
            i++;
        }
        return result;
    }

}

enum STATUS {
    XWON("X won"), OWON("O won"), DRAW("Draw"), INCOMPLETE("Game has not completed");

    private final String name;

    STATUS(String str){
        this.name = str;
    }

    public String toString(){
        return name;
    }
}

class TicTacBoard{

    char[][] board = new char[4][4];

    public TicTacBoard(List<String> boardState){
        populateBoard(boardState);
    }

    private void populateBoard(List<String> boardState){
        for(int i=0; i < 4; i++){
            for(int j=0; j < 4; j++){
                board[i][j] = boardState.get(i).charAt(j);
            }
        }
    }

    public STATUS getGameStatus(){
        STATUS returnStatus;
        boolean isGameIncomplete = false;

        returnStatus = checkAllRows();
        if(returnStatus == STATUS.OWON || returnStatus == STATUS.XWON)
            return returnStatus;
        if(returnStatus == STATUS.INCOMPLETE)
            isGameIncomplete = true;

        returnStatus = checkAllColumns();
        if(returnStatus == STATUS.OWON || returnStatus == STATUS.XWON)
            return returnStatus;
        if(returnStatus == STATUS.INCOMPLETE)
            isGameIncomplete = true;

        returnStatus = checkDiagonals();
        if(returnStatus == STATUS.OWON || returnStatus == STATUS.XWON)
            return returnStatus;
        if(returnStatus == STATUS.INCOMPLETE)
            isGameIncomplete = true;

        if(isGameIncomplete)
            return STATUS.INCOMPLETE;
        else
            return STATUS.DRAW;
    }

    private STATUS checkAllRows(){
        boolean isGameIncomplete = false;
        for(int i=0; i < 4; i++){
            STATUS returnStatus = checkRow(i);
            if(returnStatus == STATUS.OWON || returnStatus == STATUS.XWON)
                return returnStatus;
            if(returnStatus == STATUS.INCOMPLETE)
                isGameIncomplete = true;
        }
        if(isGameIncomplete)
            return STATUS.INCOMPLETE;
        else
            return STATUS.DRAW;
    }

    private STATUS checkRow(int row){
        int xCount = 0;
        int tCount = 0;
        int oCount = 0;
        for(int y=0; y < 4; y++){
            if(board[row][y] == 'X')
                xCount++;
            else if(board[row][y] == 'O')
                oCount++;
            else if(board[row][y] == 'T')
                tCount++;
        }
        return computeStatusFromCounts(xCount,oCount,tCount);
    }

    private STATUS checkAllColumns(){
        boolean isGameIncomplete = false;
        for(int i=0; i < 4; i++){
            STATUS returnStatus = checkColumn(i);
            if(returnStatus == STATUS.OWON || returnStatus == STATUS.XWON)
                return returnStatus;
            if(returnStatus == STATUS.INCOMPLETE)
                isGameIncomplete = true;
        }
        if(isGameIncomplete)
            return STATUS.INCOMPLETE;
        else
            return STATUS.DRAW;
    }

    private STATUS checkColumn(int column){
        int xCount = 0;
        int tCount = 0;
        int oCount = 0;
        for(int x=0; x < 4; x++){
            if(board[x][column] == 'X')
                xCount++;
            else if(board[x][column] == 'O')
                oCount++;
            else if(board[x][column] == 'T')
                tCount++;
        }
        return computeStatusFromCounts(xCount,oCount,tCount);
    }

    private STATUS checkDiagonals(){
        STATUS returnStatus;
        boolean isGameIncomplete = false;
        returnStatus = checkDiagonalFromTopLeft();
        if(returnStatus == STATUS.OWON || returnStatus == STATUS.XWON)
            return returnStatus;
        if(returnStatus == STATUS.INCOMPLETE)
            isGameIncomplete = true;
        returnStatus = checkDiagonalFromTopRight();
        if(returnStatus == STATUS.OWON || returnStatus == STATUS.XWON)
            return returnStatus;
        if(returnStatus == STATUS.INCOMPLETE)
            isGameIncomplete = true;

        if(isGameIncomplete)
            return STATUS.INCOMPLETE;
        else
            return STATUS.DRAW;
    }

    private STATUS checkDiagonalFromTopLeft(){
        int xCount = 0;
        int tCount = 0;
        int oCount = 0;
        for(int i=0; i < 4; i++){
            if(board[i][i] == 'X')
                xCount++;
            else if(board[i][i] == 'O')
                oCount++;
            else if(board[i][i] == 'T')
                tCount++;
        }
        return computeStatusFromCounts(xCount,oCount,tCount);
    }

    private STATUS checkDiagonalFromTopRight(){
        int xCount = 0;
        int tCount = 0;
        int oCount = 0;
        for(int i=0; i < 4; i++){
            if(board[i][3-i] == 'X')
                xCount++;
            else if(board[i][3-i] == 'O')
                oCount++;
            else if(board[i][3-i] == 'T')
                tCount++;
        }
        return computeStatusFromCounts(xCount,oCount,tCount);
    }


    private STATUS computeStatusFromCounts(int xCount, int oCount, int tCount){
        if(xCount + tCount == 4)
            return STATUS.XWON;
        else if(oCount + tCount == 4)
            return STATUS.OWON;
        else if(oCount + xCount + tCount == 4)
            return STATUS.DRAW;
        else
            return STATUS.INCOMPLETE;
    }

}
