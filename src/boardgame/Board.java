package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1)
			throw new BoardException("Error creating board: there must be at least 1 columns and 1 row ");
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public Piece piece(int row, int columns) {
		return pieces[row][columns];
	}

	public Piece piece(Position position) {
		if (!positionExists(position))
			throw new BoardException("Position not on the board");
		return pieces[position.getRow()][position.getColumn()];

	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public void placePiece(Piece piece, Position pos) {
		if (thereIsPiece(pos))
			throw new BoardException(
					"The piece cannot be relocated because there is already a piece in that position." + pos);
		pieces[pos.getRow()][pos.getColumn()] = piece;
		piece.position = pos;

	}

	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	private boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	private boolean thereIsPiece(Position position) {
		if(!positionExists(position))
			throw new BoardException("Position not on the board");
		return piece(position) != null;
	}

}
