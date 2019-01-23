package model;

import enums.Side;
import javafx.scene.Group;

public class Game {
	
	private static Side turn = Side.White;
	public static Piece[][] pieces = new Piece[Board.BOARD_WIDTH][Board.BOARD_HEIGHT];
	public static Group tileGroup = new Group();
	public static Group pieceGroup = new Group();
	private static int capturedWhitePieces = 0;
	private static int capturedBlackPieces = 0;
	
	
	
	public static Side getTurn() {
		return turn;
	}
	public static void setTurn(Side turn) {
		Game.turn = turn;
	}
	public static int getCapturedWhitePieces() {
		return capturedWhitePieces;
	}
	public static void captureWhitePiece() {
		Game.capturedWhitePieces += 1;
	}
	public static int getCapturedBlackPieces() {
		return capturedBlackPieces;
	}
	public static void captureBlackPiece() {
		Game.capturedBlackPieces += 1;
	}
	
	public static void initGame() {
		
		Board.initPieces(Board.getBoard());
		
	}
	
	

}
