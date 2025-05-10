package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	private boolean possibleEnPassant;
	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	




	public boolean getIsPossibleEnPassant() {
		return possibleEnPassant;
	}



	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

	    if (position == null) {
	        return mat;
	    }
		
		Position p = new Position(0, 0);

		if (getColor() == Color.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() - 2, position.getColumn());
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			if(position.getRow() == 3) {
				Position leftSide = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(leftSide) && isThereOpponentPiece(leftSide) 
						&& getBoard().piece(leftSide) == chessMatch.getEnPassantVulnerable()) {
					mat[leftSide.getRow() - 1][leftSide.getColumn()] = true;
				}
				Position rightSide = new Position(position.getRow(), position.getColumn() + 1);

				if(getBoard().positionExists(rightSide) && isThereOpponentPiece(rightSide) 
						&& getBoard().piece(rightSide) == chessMatch.getEnPassantVulnerable()) {
					mat[rightSide.getRow() - 1][rightSide.getColumn()] = true;
				}
			}
			

		} else {
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() + 2, position.getColumn());
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			if(position.getRow() == 4) {
				Position leftSide = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(leftSide) && isThereOpponentPiece(leftSide) 
						&& getBoard().piece(leftSide) == chessMatch.getEnPassantVulnerable()) {
					mat[leftSide.getRow() + 1][leftSide.getColumn()] = true;
				}
				Position rightSide = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(rightSide) && isThereOpponentPiece(rightSide) 
						&& getBoard().piece(rightSide) == chessMatch.getEnPassantVulnerable()) {
					mat[rightSide.getRow() + 1][rightSide.getColumn()] = true;
				}
			}				

		}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}

}
