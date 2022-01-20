package chess.players.ai;

import chess.ChessBoardState;
import chess.moves.ChessMove;
import chess.pieces.ChessPiece;
import chess.pieces.PieceBishop;
import chess.pieces.PieceKnight;
import chess.pieces.PiecePawn;
import chess.pieces.PieceQueen;
import chess.pieces.PieceRook;
import chess.pieces.PieceKing;
import chess.players.ChessPlayerCPU;
import game.utility.Location;
import game.utility.Properties.PlayerColor;

/**
 *
 * @author devang
 */
public class ChessAI extends Thread {
    public static int maxDepth = 2;
    public static int maxQDepth = 1;
    public static int maxTime = 5000;
    public static int numTrials = 15000;
    public static int maxMonteCarloDepth = 30;

    PlayerColor cpuColor;
    ChessPlayerCPU cpuPlayer;
    
    public ChessAI(ChessPlayerCPU chessPlayer,PlayerColor playerCPUColor)
    {
        cpuPlayer = chessPlayer;
        cpuColor = playerCPUColor;
    }
    
    public static int evaluate(ChessBoardState boardState,PlayerColor playerCPUColor)
    {
        int score = 0;

        for (Location location : Location.allLocations())
        {
            ChessPiece piece = (ChessPiece)boardState.getPiece(location);
            if (piece == null) continue;
            if (piece.getColor() == playerCPUColor)
                score += scorePieceValue(piece,location,boardState);
            else if (piece.getColor() != playerCPUColor)
                score -= scorePieceValue(piece,location,boardState);
        }
        
        return score;
    }
    
    private static int scorePieceValue(ChessPiece piece,Location pieceLocation,ChessBoardState boardState)
    {
        int score = 0;
        
        if (piece == null)
        {
            // do nothing
        }
        else
        {
            if (piece instanceof PiecePawn)   score = 100;
            if (piece instanceof PieceKnight) score = 300;
            if (piece instanceof PieceBishop) score = 300;
            if (piece instanceof PieceRook)   score = 500;
            if (piece instanceof PieceQueen)  score = 900;
            if (piece instanceof PieceKing)   score = 9000;
        }
        
        //if (piece.check(pieceLocation, boardState)) score -= 3;
        //if (piece.isProtected(pieceLocation, boardState)) score += 4;
        //if (((PieceKing)piece).hasCastled) score += 275;

        return score;
    }

    @Override
    public void run()
        {
            /*
            MonteCarlo montecarlo = new MonteCarlo(cpuPlayer,cpuColor);
            ChessMove move = montecarlo.evaluate();
            */


            MiniMax minimax = new MiniMax(cpuPlayer,cpuColor);
            ChessMove move = minimax.evaluate();

            cpuPlayer.commitMove(move);
        }
}
