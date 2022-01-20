package chess;

import chess.moves.ChessMove;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author devang
 */
public class ChessGameHistory {
    List<String> gameHistory;
    private int numMovesMade;
    
    // remove this after implementing using algebraic chess notation//
    private ChessMove mostRecentMove = null;                        //
                                                                    //
    public void setMostRecentMove(ChessMove move)                   //
    {                                                               //
        mostRecentMove = move;                                      //
        numMovesMade++;                                             //
    }                                                               //
                                                                    //
    public ChessMove getMostRecentMove()                            //
    {                                                               //
        return mostRecentMove;                                      //
    }                                                               //
                                                                    //
    public int getNumMovesMade()                                    //
    {                                                               //
        return numMovesMade;                                        //
    }                                                               //
    // remove this after implementing using algebraic chess notation//
    
    
    public ChessGameHistory()
    {
        gameHistory = new ArrayList<>();
        numMovesMade = 0;
    }
    
    public void addHistory(String item)
    {
        gameHistory.add(item);
        numMovesMade++;
    }
    
    public List<String> getHistory()
    {
        return gameHistory;
    }
    
    public String getMostRecent()
    {
        if (!gameHistory.isEmpty())
            return gameHistory.get(gameHistory.size()-1);
        return null;
    }
    
    public int getNumMoves()
    {
        return numMovesMade;
    }
}
