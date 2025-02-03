package chess;

import boardgame.Position;

/**
 * matrix_row = 8 - chess_row
 * 
 * Unicode
 * 'a' =  0041 
 * 'b' =  0042
 * 'c' =  0043
 * ...
 * 
 * 'a' - 'a' = 0
 * 'b' - 'a' = 1
 * 'c' - 'a' = 2
 * ...
 * 
 * martrix_column = chess_column - 'a'
 * 
 * 
 * a = 0
 * b = 1
 * c = 2
 * 
 */

public class ChessPosition {
	private char column = ' ';
	private Integer row = 0;

	public ChessPosition(char column, Integer row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException(
					"The value of columns must be higher than 'a' or lower than 'h' and the value of the "
					+ "rows must be higher than 1 or lower than 8");
		}
		this.column = column;
		this.row = row;
	}
	
	

	public char getColumn() {
		return column;
	}



	public Integer getRow() {
		return row;
	}



	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	protected static ChessPosition fromPosition(Position pos) {
		return new ChessPosition((char)('a' - pos.getColumn()), 8 - pos.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row;
	}

}
