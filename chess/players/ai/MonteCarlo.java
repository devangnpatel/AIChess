package chess.players.ai;

import chess.ChessBoardState;
import chess.moves.ChessMove;
import chess.players.ChessPlayerCPU;
import game.utility.Properties;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author devang
 */
public class MonteCarlo {
    private ChessPlayerCPU cpuPlayer;
    private Properties.PlayerColor cpuPlayerColor;
    
    public class Moves {
        public ChessMove move;
        public int totalScore;
        public int numTrials;
        
        public Moves(ChessMove move)
        {
            this.move = move;
            this.totalScore = 0;
            this.numTrials = 0;
        }
        
        public void addSampleScore(int score)
        {
            this.totalScore += score;
            this.numTrials += 1;
        }
        
        public int getScore()
        {
            if (numTrials > 0)
                return (int)Math.floorDiv(totalScore,numTrials);
            else
                return Integer.MIN_VALUE;
        }
    }
    
    public MonteCarlo(ChessPlayerCPU player, Properties.PlayerColor playerColor)
    {
        cpuPlayer      = player;
        cpuPlayerColor = playerColor;
    }

    public ChessMove evaluate()
    {
        List<ChessMove> validMoves = cpuPlayer.getValidMoves(cpuPlayer.getBoardState(),cpuPlayerColor);
        List<Moves> monteCarloScores = new ArrayList<>();
        int numPossibleMoves = validMoves.size();
        for (int i=0;i<numPossibleMoves;i++)
        {
            monteCarloScores.add(new Moves(validMoves.get(i)));
        }
        
        long startTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        
        //while (currentTime - startTime < ChessAI.maxTime)
        for (int t = 0; t < ChessAI.numTrials;t++)
        {
            int moveIndex = (int)Math.floor(Math.random()*validMoves.size());
            if (validMoves.isEmpty()) break;
            ChessMove move = validMoves.get(moveIndex);
            
            ChessBoardState tempBoardState = ChessBoardState.copy(cpuPlayer.getBoardState());
            ((ChessMove)move).commitMove(tempBoardState);

            int tempScore = monteCarloTrial(0,Properties.oppositeColor(cpuPlayerColor),tempBoardState);
            monteCarloScores.get(moveIndex).addSampleScore(tempScore);
            currentTime = System.currentTimeMillis();
        }
        
        int bestScore = Integer.MIN_VALUE;
        
        List<ChessMove> bestMoves  = new ArrayList<>();
        for (int m = 0; m < monteCarloScores.size(); m++)
        {
            if (monteCarloScores.get(m).getScore() > bestScore)
            {
                bestScore = monteCarloScores.get(m).getScore();
                bestMoves.clear();
                bestMoves.add(monteCarloScores.get(m).move);
            }
            else if (monteCarloScores.get(m).getScore() == bestScore)
            {
                bestMoves.add(monteCarloScores.get(m).move);
            }
        }
        if (bestMoves.isEmpty()) return null;
        
        return bestMoves.get((int)Math.floor(Math.random()*bestMoves.size()));

    }
    
    
    private int monteCarloTrial(int depth, Properties.PlayerColor playerColor, ChessBoardState boardState)
    {
        int score = ChessAI.evaluate(boardState,playerColor);
                   
        if (depth >= ChessAI.maxMonteCarloDepth)
        {
            return score;
        }
        
        if (score == Integer.MIN_VALUE) return -10000;
        if (score == Integer.MAX_VALUE) return  10000;

        List<ChessMove> validMoves = cpuPlayer.getValidMoves(boardState,playerColor);
        if (validMoves.isEmpty()) return score;
        ChessMove move = validMoves.get((int)Math.floor(Math.random()*validMoves.size()));
        ChessBoardState tempBoardState = ChessBoardState.copy(boardState);
        move.commitMove(tempBoardState);

        return monteCarloTrial(depth+1,Properties.oppositeColor(playerColor),tempBoardState);
    }

}
