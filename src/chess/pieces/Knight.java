package chess.pieces;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece{



	public Knight(Board board, Color color) {
		super(board, color);
	}


	@Override
	public boolean[][] possibleMoves() {
	    boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

	    int[][] moves = {
	        {-2, -1}, {-2, +1}, {-1, -2}, {-1, +2},
	        {+1, -2}, {+1, +2}, {+2, -1}, {+2, +1}
	    };

	    for (int[] move : moves) {
	        Position p = new Position(position.getRow() + move[0], position.getColumn() + move[1]);
	        if (getBoard().positionExists(p) && (isThereOpponentPiece(p) || !getBoard().thereIsPiece(p))) {
	            mat[p.getRow()][p.getColumn()] = true;
	        }
	    }

	    return mat;
	}

	@Override
	public String toString() {
		return "N";
		
	}

}
