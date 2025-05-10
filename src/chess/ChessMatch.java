package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

	private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
	private List<ChessPiece> capturedPieces = new ArrayList<>();

	public boolean getCheck() {
		return check;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public int getTurn() {
		return turn;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;

		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		Piece capturedPiece = makeMove(source, target);
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		promoted = null;
		
		if(movedPiece instanceof Pawn) {
			if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) 
					|| (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}else {
			nextTurn();			
		}
		
		if (movedPiece instanceof Pawn &&
			    (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			    enPassantVulnerable = movedPiece;
			} else {
			    enPassantVulnerable = null;
			}
		
		
		
		return (ChessPiece) capturedPiece;

	}

	public ChessPiece replacePromotedPiece(String typeOfPiece) {
		if(promoted == null) {
			throw new IllegalStateException("There is no piece for promotion (NULL)");
		}
		if(!typeOfPiece.equals("Q") && !typeOfPiece.equals("R") 
				&& !typeOfPiece.equals("N") && !typeOfPiece.equals("B")) {
			throw new InvalidParameterException("Invalid type for promotion");
		}
		
		Position pos = promoted.getChessPosition().toPosition();
		ChessPiece newPiece = newPiece(typeOfPiece, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		return newPiece;
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturedPiece != null && enPassantVulnerable == null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add((ChessPiece) capturedPiece);
		}
		if (p instanceof Pawn) {
		    if (source.getColumn() != target.getColumn() && capturedPiece == null) {
		        Position pawnPosition;
		        if (p.getColor() == Color.WHITE) {
		            pawnPosition = new Position(target.getRow() + 1, target.getColumn());
		        } else {
		            pawnPosition = new Position(target.getRow() - 1, target.getColumn());
		        }
		        capturedPiece = board.removePiece(pawnPosition);
		        piecesOnTheBoard.remove(capturedPiece);
		        capturedPieces.add((ChessPiece) capturedPiece);
		    }
		}
		return capturedPiece;
	}

	@SuppressWarnings("unused")
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		board.placePiece(p, source);
		p.decreaseMoveCount();
		
		if(capturedPiece != null) {
			if(p instanceof Pawn && source.getColumn() != target.getColumn() 
					&& capturedPiece == null) {
				Position position = (p.getColor() == Color.WHITE)
						?new Position(target.getRow() + 1, target.getColumn())
						:new Position(target.getRow() - 1, target.getColumn());
				board.placePiece(capturedPiece, target);

			}else {
				board.placePiece(capturedPiece, target);
			}	
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add((ChessPiece) capturedPiece);
		}
	}
	

	private void validateSourcePosition(Position position) {
		if (!board.thereIsPiece(position)) {
			throw new ChessException("Error: Source not found");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("Error: Unable to make move, other player's turn");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Error: There is no possible moves for the chosen piece");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("Error: The chosen piece can't move to target position");
		}
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		piecesOnTheBoard.add(piece);
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	private void nextTurn() {
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece newPiece(String type, Color color) {
		if(type.equals("Q")) return new Queen(board, color);
		if(type.equals("N")) return new Knight(board, color);
		if(type.equals("B")) return new Bishop(board, color);
		return new Rook(board, color);
		
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if(p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalMonitorStateException("There is not king on the board"); 
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()] == true) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
	    if(!testCheck(color)) {
	        return false;
	    }
	    List<Piece> list = piecesOnTheBoard.stream()
	        .filter(x -> ((ChessPiece)x).getColor() == color)
	        .collect(Collectors.toList());
 
	    for(Piece p : list) {
	        boolean[][] mat = p.possibleMoves();
	        for (int i = 0; i < board.getRows(); i++) {
	            for (int j = 0; j < board.getColumns(); j++) {
	                if (mat[i][j]) {
	                    Position source = ((ChessPiece) p).getChessPosition().toPosition();
	                    Position target = new Position(i, j);
	                    Piece capturedPiece = makeMove(source, target);
	                    boolean testCheck = testCheck(color);
	                    undoMove(source, target, capturedPiece);
	                    if (!testCheck) {
	                        return false;
	                    }
	                }
	            }
	        }
	    }
	    return true;
	}


	public ChessPosition getChessPosition(Position position) {
		return ChessPosition.fromPosition(position);
	}

	private void initialSetup() {
		  placeNewPiece('a', 1, new Rook(board, Color.WHITE));
	        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
	        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
	        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
	        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
	        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
	        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
	        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
	        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

	        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
	        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
	        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
	        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
	        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
	        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
	        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}

	public boolean getCheckMate() {
		return checkMate;
	}

}
