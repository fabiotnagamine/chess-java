package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		int[][] moves = {
			{-1, 1}, {-1, -1},
			{1, 1}, {1, -1}
		};
		
		for(int[] move: moves) {
			Position p = new Position(position.getRow() + move[0], position.getColumn() + move[1]);
			while (getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
				p.setValues(p.getRow() + move[0], p.getColumn() + move[1]);
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}

//		// nordeste
//		p.setValues(position.getRow() - 1, position.getColumn() + 1);
//		while (getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
//			mat[p.getRow()][p.getColumn()] = true;
//			p.setValues(p.getRow() - 1, p.getColumn() + 1);
//		}
//		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
//			mat[p.getRow()][p.getColumn()] = true;
//		}
//
//		// noroeste
//		p.setValues(position.getRow() - 1, position.getColumn() - 1);
//		while (getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
//			mat[p.getRow()][p.getColumn()] = true;
//			p.setValues(p.getRow() - 1, p.getColumn() - 1);
//		}
//		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
//			mat[p.getRow()][p.getColumn()] = true;
//		}
//
//		// sudeste
//		p.setValues(position.getRow() + 1, position.getColumn() + 1);
//		while (getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
//			mat[p.getRow()][p.getColumn()] = true;
//			p.setValues(p.getRow() + 1, p.getColumn() + 1);
//		}
//		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
//			mat[p.getRow()][p.getColumn()] = true;
//		}
//
//		// sudoeste
//		p.setValues(position.getRow() + 1, position.getColumn() - 1);
//		while (getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
//			mat[p.getRow()][p.getColumn()] = true;
//			p.setValues(p.getRow() + 1, p.getColumn() - 1);
//		}
//		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
//			mat[p.getRow()][p.getColumn()] = true;
//		}

		return mat;
	}
	
	@Override
	public String toString() {
		return "B";
	}

}
