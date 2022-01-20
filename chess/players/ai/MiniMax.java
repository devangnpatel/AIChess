package chess.players.ai;

import chess.ChessBoardState;
import chess.moves.ChessMove;
import chess.players.ChessPlayerCPU;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author devang
 */
public class MiniMax {
    private ChessPlayerCPU cpuPlayer;
    private PlayerColor cpuPlayerColor;

    public class Moves {
        public ChessMove move;
        public int score;
        
        public Moves(ChessMove move, int score)
        {
            this.move  = move;
            this.score = score;
        }
    }
    
    public MiniMax(ChessPlayerCPU cpuPlayer, Properties.PlayerColor playerColor)
    {
        this.cpuPlayer = cpuPlayer;
        cpuPlayerColor = playerColor;
    }

    public ChessMove evaluate()
    {
                
        // this method is the initial "maximizing" call
        int alpha = Integer.MIN_VALUE;
        int beta  = Integer.MAX_VALUE;
        
        List<ChessMove> validMoves = cpuPlayer.getValidMoves(cpuPlayer.getBoardState(),cpuPlayerColor);
        List<Moves> moves = new ArrayList<>();
        
        for (ChessMove move : validMoves)
        {
            ChessBoardState tempBoardState = ChessBoardState.copy(cpuPlayer.getBoardState());
            move.commitMove(tempBoardState);
            
            int score = minimize(0,alpha,beta,Properties.oppositeColor(cpuPlayerColor),tempBoardState);
            moves.add(new Moves(move,score));
        }
        
        int bestScore = Integer.MIN_VALUE;
        List<ChessMove> bestMoves  = new ArrayList<>();
        for (int m = 0; m < moves.size(); m++)
        {
            if (moves.get(m).score > bestScore)
            {
                bestScore = moves.get(m).score;
                bestMoves.clear();
                bestMoves.add(moves.get(m).move);
            }
            else if (moves.get(m).score == bestScore)
            {
                bestMoves.add(moves.get(m).move);
            }
        }
        if (bestMoves.isEmpty()) return null;
        
        return bestMoves.get((int)Math.floor(Math.random()*bestMoves.size()));
    }
    
    private int maximize(int depth, int alpha, int beta, Properties.PlayerColor playerColor, ChessBoardState boardState)
    {
        if (depth > ChessAI.maxDepth)
        {
            return ChessAI.evaluate(boardState,cpuPlayerColor);
        }
        
        int score;
        
        List<ChessMove> validMoves = cpuPlayer.getValidMoves(boardState,playerColor);
        
        for (ChessMove move : validMoves)
        {
            ChessBoardState tempBoardState = ChessBoardState.copy(boardState);
            ((ChessMove)move).commitMove(tempBoardState);
            
            score = minimize(depth+1,alpha,beta,Properties.oppositeColor(playerColor),tempBoardState);

            if (score >= beta) return beta;
            
            if (score > alpha)
            {
                alpha    = score;
            }
            
                   
        }
        
        return alpha;
    }
    
    private int minimize(int depth, int alpha, int beta, Properties.PlayerColor playerColor, ChessBoardState boardState)
    {
        if (depth > ChessAI.maxDepth)
        {
            return -1*ChessAI.evaluate(boardState,cpuPlayerColor);
        }

        int score;
        
        List<ChessMove> validMoves = cpuPlayer.getValidMoves(boardState,playerColor);
        
        for (ChessMove move : validMoves)
        {
            ChessBoardState tempBoardState = ChessBoardState.copy(boardState);
            ((ChessMove)move).commitMove(tempBoardState);

            score = maximize(depth+1,alpha,beta,Properties.oppositeColor(playerColor),tempBoardState);

            if (score <= alpha) return alpha;
            
            if (score < beta)
            {
                beta     = score;
            }
            
            
        }
        
        return beta;
    }
    
}
