package model;

import java.util.ArrayList;

import enums.Side;

public class Move {

	private static ArrayList<Move> whiteMoves = new ArrayList<Move>();
	private static ArrayList<Move> blackMoves = new ArrayList<Move>();
	private Piece piece;
	private Tile oldPos;
	private Tile newPos;
	private boolean isCatch;
	private boolean isCheck;
	private boolean isPromote;
	private boolean isMate;
	private int castling;
	private String move;

	
	
	public static ArrayList<Move> getWhiteMoves() {
		return whiteMoves;
	}

	public static ArrayList<Move> getBlackMoves() {
		return blackMoves;
	}

	public Piece getPiece() {
		return piece;
	}

	public Tile getOldPos() {
		return oldPos;
	}

	public Tile getNewPos() {
		return newPos;
	}
	
	public boolean getIsCatch() {
		return isCatch;
	}

	public boolean getIsCheck() {
		return isCheck;
	}

	public boolean getIsPromote() {
		return isPromote;
	}

	public boolean getIsMate() {
		return isMate;
	}

	public boolean getCastling() {
		return isMate;
	}

	public String getMove() {
		return move;
	}
	
	private void setMove() {
		
		if(castling == -1) {
			move = getPieceChar(piece);
			
	//		move += Character.toChars(oldPos.getXPos() + 97).toString() + oldPos.getYPos();
			
			if(isCatch) {
				if(move.equals(""))
					move += String.valueOf(Character.toChars(oldPos.getXPos() + 96));
				move += "x";
			}
			
			move += String.valueOf(Character.toChars(newPos.getXPos() + 96)) + String.valueOf(9 - newPos.getYPos());
			
			
			if(isPromote) {
				move += "=";
				getPieceChar(newPos.getPiece());
			}
			
			if(isMate)
				move += "#";
			else if(isCheck)
				move += "+";
		}
		else if(castling == 0)
			move = "O-O";
		else
			move = "O-O-O";
		
		System.out.println(move);
	}
	
	

	private Move(Piece piece, Tile oldPos, Tile newPos, boolean isCatch, boolean isCheck, boolean isPromote, boolean isMate, int castling) {
		this.piece = piece;
		this.oldPos = oldPos;
		this.newPos = newPos;
		this.isCatch = isCatch;
		this.isCheck = isCheck;
		this.isPromote = isPromote;
		this.castling = castling;
		setMove();
	}
	
	
	public static Move move(Piece piece, Tile oldPos, Tile newPos, boolean isCatch, boolean isCheck, boolean isPromote, boolean isMate, int castling) {
		 // castling 0 for king side, 1 for queen side -1 if the move is not castling
		Move move = new Move(piece, oldPos, newPos, isCatch, isCheck, isPromote, isMate, castling);
		
		if(piece.getSide() == Side.Black)
			blackMoves.add(move);
		else
			whiteMoves.add(move);
		
		return move;
	}
	
	public static String getPieceChar(Piece piece) {
		String move;
		if(piece instanceof King)
			move = "K";
		else if(piece instanceof Queen)
			move = "Q";
		else if(piece instanceof Rook)
			move = "R";
		else if(piece instanceof Knight)
			move = "N";
		else if(piece instanceof Bishop)
			move = "B";
		else
			move = "";
		
		return move;
	}

}
