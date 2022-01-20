package chess.players;

import chess.ChessBoardState;
import chess.ChessGame;
import chess.moves.ChessMove;
import chess.pieces.ChessPiece;
import chess.players.ai.ChessAI;
import game.utility.Location;
import game.utility.Properties.PlayerColor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Inherits from a PlayerCPU, a Board Game Player CPU to provide same
 * interface for a phantom AI player
 * and same interface across any game: Checkers, Chess, Othello, Poker, Chinese-Checkers, etc.
 * @author devang
 */
public class ChessPlayerCPU extends ChessPlayer {
    
    private ChessPlayerCPU(ChessGame game, PlayerColor color)
    {
        super(game,color);
    }
    
    /**
     * Creates a Phantom AI Player, in Object factory fashion
     * @param game the game state representation
     * @param color the color for this AI-player
     * @return a newly constructed Phantom AI CPU Player
     */
    public static ChessPlayerCPU create(ChessGame game, PlayerColor color)
    {
        return new ChessPlayerCPU(game,color);
    }
    
    /**
     * Sends move to update this Phantom AI's game state Game state
     * and indicates it is this Phantom AI's turn to Move
     * @param move Move that is made by the Human Player on the current game-state
     */
    @Override
    public void persistMove(ChessMove move)
    {
        String loggerMsg = "human persist move: AI makes next move";
        Logger.getLogger(ChessPlayerCPU.class.getName()).log(Level.FINE,loggerMsg);
        //super.persistMove(move);
        determineMove();
        
    }
    
    /**
     * Called after this Phantom AI picks a move
     * @param move Move that this phantom AI made
     */
    public void commitMove(ChessMove move)
    {
        String loggerMsg = "Phantom AI commit move";
        Logger.getLogger(ChessPlayerCPU.class.getName()).log(Level.FINE,loggerMsg);
        super.commitMove(move);
    }
    
    public void determineMove()
    {
        ChessAI chessAI = new ChessAI(this,this.getColor());
        chessAI.start();
    }
  
    public List<ChessMove> getValidMoves(ChessBoardState boardState,PlayerColor playerColor)
    {
        List<ChessMove> validMoves = new ArrayList<>();
        List<ChessMove> moves      = null;
        
        for (Location location : Location.allLocations())
        {
            ChessPiece piece = (ChessPiece)boardState.getPiece(location);
            if (piece != null && playerColor.equals(piece.getColor()))
            {
                moves = piece.getValidMoves(location,boardState,((ChessGame)game).getGameHistory());
                if (moves != null && !moves.isEmpty())
                    validMoves.addAll(moves);
            }
        }
        
        return validMoves;
    }
    public ChessBoardState getBoardState()
    {
        return ((ChessGame)game).getBoardState();
    }
}
