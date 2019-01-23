package model;

import java.util.ArrayList;

import enums.Side;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class King extends Piece {
	
	private static King blackKing;
	private static King whiteKing;
	private boolean moved;
	private boolean inCheck;
	
	public static King getBlackKing() {
		return blackKing;
	}
	
	public static King getWhiteKing() {
		return whiteKing;
	}
	
	public void setInCheck(boolean inCheck) {
		if(inCheck) {
			System.out.println("Colorize the tile"); {
				Tile.getTile(this).setFill(Color.valueOf("#ff0000"));
			}
		}
		this.inCheck = inCheck;
	}
	
	public boolean isInCheck() {
		return inCheck;
	}
	
	public boolean isMoved() {
		return moved;
	}
	
	private King(int x, int y, Side side) {
		super(x, y, side);
		moved = false;
		if(side == Side.Black)
			setImage(new Image("/assets/img/black_king.png"));
		else
			setImage(new Image("/assets/img/white_king.png"));
	}
	
	public static King initKing(Side side) {
		
		int x = 5;
		int y;
		
		if(side == Side.Black) {
			y = 1;
			if(blackKing == null) {
				blackKing = new King(x, y, side);
			}
			blackKing.setXPos(x);
			blackKing.setYPos(y);
			return blackKing;
		}
		else {
			y = 8;
			if(whiteKing == null) {
				whiteKing = new King(x, y, side);
			}
			whiteKing.setXPos(x);
			whiteKing.setYPos(y);
			return whiteKing;
		}
	}

	@Override
	public void move(Tile tile) {
		if(!moved && getInitialPosition() != Tile.getTile(this))
			moved = true;
	}

	@Override
	public void capture(Tile tile) {
		if(!moved)
			moved = true;
	}

	static int ind = 0;
	@Override
	public ArrayList<Tile> getAllowedMoves() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		for(int i = getXPos() - 1; i <= getXPos() + 1; i++) {
			for(int j = getYPos() - 1; j <= getYPos() + 1; j++) {
				if(i >= 1 && i <= 8 && j >= 1 && j <= 8) {
					if(Tile.getTiles()[i - 1][j - 1].hasPiece()) {
						if(Tile.getTiles()[i - 1][j - 1].getPiece().getSide() != this.getSide() && !Tile.getTiles()[i - 1][j - 1].isAttacked(this.getSide())) {
							if(!Piece.isCheck(this, Tile.getTiles()[i - 1][j - 1]))
								tiles.add(Tile.getTiles()[i - 1][j - 1]);
						}
					}
					else {
						tiles.add(Tile.getTiles()[i - 1][j - 1]);
					}
				}
			}
		}

		// Check for castling
		int x0 = 0; // king xPos
		int x1 = 0; // king's xPos + 1 || - 1
		int x2 = 0; // king's xPos + 2 || - 2
		int y = 0;  // king's yPos
		Rook rook = null;
		for(int count = 0; count < 2; count++) {
			if(!this.moved && getSide() == Game.getTurn()) {
					if(count == 0) {
						rook = (Tile.getTiles()[getXPos() + 2][getYPos() - 1].getPiece() != null) ?
								(Rook) Tile.getTiles()[getXPos() + 2][getYPos() - 1].getPiece() : null;
						x0 = getXPos() - 1;
						x1 = getXPos();
						x2 = getXPos() + 1;
						y = getYPos() - 1;
					}
					else {
						rook = (Tile.getTiles()[getXPos() - 5][getYPos() - 1].getPiece() != null) ?
								(Rook) Tile.getTiles()[getXPos() - 5][getYPos() - 1].getPiece() : null;
						x0 = getXPos() - 1;
						x1 = getXPos() - 2;
						x2 = getXPos() - 3;
					}
				if(rook != null) {
					if(!rook.isMoved()) {
						if(Tile.getTiles()[x1][y].getPiece() == null
								&& Tile.getTiles()[x2][y].getPiece() == null) {
							boolean attacked = false;
							ArrayList<Tile> allowedMoves;
							for(Node piece : Game.pieceGroup.getChildren()) {
								Piece aPiece = ((Piece) piece);
								if(aPiece.getSide() != getSide()) {
									allowedMoves = aPiece.getAllowedMoves();
										if(allowedMoves.contains(Tile.getTiles()[x0][y])
												|| allowedMoves.contains(Tile.getTiles()[x1][y])
												|| allowedMoves.contains(Tile.getTiles()[x2][y])) {
											attacked = true;
											break;
										}
								}
							}
							if(!attacked) {
								tiles.add(Tile.getTiles()[x2][y]);
							}
						}
					}
				}
			}
		}
		
		// Remove all the attacked tiles from the collection
//		for(Tile tile : tiles) {
//			System.out.println("iteration");
//			if(tile.isAttacked(getSide())) {
//				tiles.remove(tile);
//				System.out.println("tile attacked");
//			}
//		}
//		for(int i = 0; i < tiles.size(); i++) {
//			ind++;
//			System.out.println("iteration " + ind);
//			if(tiles.get(i).isAttacked(getSide())) {
//				tiles.remove(tiles.get(i));
//				System.out.println("tile attacked");
//				i--;
//			}
//		}
		
		return tiles;
	}
	
	public static boolean isInCheck(King king) {
		boolean inCheck = false;
		for(Node node : Game.pieceGroup.getChildren()) {
			Piece piece = (Piece) node;
			if(piece.getSide() != king.getSide()) {
				if(piece.getAllowedMoves().contains(
						Tile.getTiles()[king.getXPos() - 1][king.getYPos() - 1])) {
					inCheck = true;
					break;
				}
			}
		}
		
		return inCheck;
	}
	
	public static boolean isInCheck(Tile tile, Side side) {
		boolean inCheck = false;
		for(Node node : Game.pieceGroup.getChildren()) {
			Piece piece = (Piece) node;
			if(piece.getSide() != side) {
				if(piece.getAllowedMoves().contains(
						tile)) {
					inCheck = true;
					break;
				}
			}
		}
		
		return inCheck;
	}

}
