package boardgame;

public abstract class Piece {
	protected Position position;
	private Board board;

	public Piece (Board board) {
		this.board = board;
		position = null;
	}
	
	public abstract boolean[][] possibleMoves();

	
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	protected Board getBoard() {
		return board;
	}
}
