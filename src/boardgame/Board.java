package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	
	public Piece piece(int row, int columns) {
		return pieces[row][columns];
	}
	
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
		
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public void placePiece(Piece piece, Position pos) {
		pieces[pos.getRow()][pos.getColumn()] = piece;
		piece.position = pos;
	}
	
	

}
