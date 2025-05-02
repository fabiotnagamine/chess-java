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
		
		Position p = new Position(0 , 0);
		
		if(getColor() == Color.WHITE) {
			
			//N1
			p.setValues(position.getRow() - 2, position.getColumn() - 1);
			if(getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//N2
			p.setValues(position.getRow() - 2, position.getColumn() + 1);
			if(getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//N3
			p.setValues(position.getRow() - 1, position.getColumn() - 2);
			if(getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//N4
			p.setValues(position.getRow() - 1, position.getColumn() + 2);
			if(getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//N5
			p.setValues(position.getRow() + 1, position.getColumn() - 2);
			if(getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//N6
			p.setValues(position.getRow() + 1, position.getColumn() + 2);
			if(getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//N7
			p.setValues(position.getRow() + 2, position.getColumn() - 1);
			if(getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//N8
			p.setValues(position.getRow() + 2, position.getColumn() + 1);
			if(getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
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
