package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King(Board board, Color color, int moveCount) {
		super(board, color, moveCount);
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
}
