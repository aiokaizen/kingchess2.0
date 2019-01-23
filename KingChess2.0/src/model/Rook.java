package model;

import java.util.ArrayList;

import enums.Side;
import javafx.scene.image.Image;

public class Rook extends Piece {
	
	private static Rook[] rooks = new Rook[4];
	private static Rook[] promotedPawns = new Rook[16];
	private boolean moved;
	
	public boolean isMoved() {
		return moved;
	}
	
	private Rook(int x, int y, Side side) {
		super(x, y, side);
		moved = false;
		if(side == Side.Black)
			setImage(new Image("/assets/img/black_rook.png"));
		else
			setImage(new Image("/assets/img/white_rook.png"));
	}
	
	public static Rook initRook(Side side) {
		
		int[] x = new int[]{1, 8};
		int[] y = new int[]{1, 8};
		
		if(side == Side.Black) {
			if(rooks[0] == null) {
				rooks[0] = new Rook(x[0], y[0], side);
				return rooks[0];
			}
			else if(rooks[1] == null) {
				rooks[1] = new Rook(x[1], y[0], side);
				return rooks[1];
			}
			else {
				if(rooks[0].getXPos() != x[0] && rooks[0].getYPos() != y[0]) {
					rooks[0].setXPos(x[0]);
					rooks[0].setYPos(y[0]);
					return rooks[0];
				}
				else {
					rooks[1].setXPos(x[1]);
					rooks[1].setYPos(y[0]);
					return rooks[1];
				}
			}
		}
		else {
			if(rooks[2] == null) {
				rooks[2] = new Rook(x[0], y[1], side);
				return rooks[2];
			}
			else if(rooks[3] == null) {
				rooks[3] = new Rook(x[1], y[1], side);
				return rooks[3];
			}
			else {
				if(rooks[2].getXPos() != x[0] && rooks[2].getYPos() != y[1]) {
					rooks[2].setXPos(x[0]);
					rooks[2].setYPos(y[1]);
					return rooks[2];
				}
				else {
					rooks[3].setXPos(x[1]);
					rooks[3].setYPos(y[1]);
					return rooks[3];
				}
			}
		}
	}

	@Override
	public void move(Tile tile) {
		if(!moved)
			moved = true;
	}

	@Override
	public void capture(Tile tile) {
		if(!moved)
			moved = true;
	}

	@Override
	public ArrayList<Tile> getAllowedMoves() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		// Get moves in X axis
		for(int i = getXPos() + 1; i <= 8; i++) {
			if(Tile.getTiles()[i - 1][getYPos() - 1].hasPiece()) {
				if(Tile.getTiles()[i - 1][getYPos() - 1].getPiece().getSide() != this.getSide()) {
					tiles.add(Tile.getTiles()[i - 1][getYPos() - 1]);
				}
				break;
			}
			else {
				tiles.add(Tile.getTiles()[i - 1][getYPos() - 1]);
			}
		}
		for(int i = getXPos() - 1; i > 0; i--) {
			if(Tile.getTiles()[i - 1][getYPos() - 1].hasPiece()) {
				if(Tile.getTiles()[i - 1][getYPos() - 1].getPiece().getSide() != this.getSide()) {
					tiles.add(Tile.getTiles()[i - 1][getYPos() - 1]);
				}
				break;
			}
			else {
				tiles.add(Tile.getTiles()[i - 1][getYPos() - 1]);
			}
		}
		
		// get moves in Y axis
		for(int i = getYPos() + 1; i <= 8; i++) {
			if(Tile.getTiles()[getXPos() - 1][i - 1].hasPiece()) {
				if(Tile.getTiles()[getXPos() - 1][i - 1].getPiece().getSide() != this.getSide()) {
					tiles.add(Tile.getTiles()[getXPos() - 1][i - 1]);
				}
				break;
			}
			else {
				tiles.add(Tile.getTiles()[getXPos() - 1][i - 1]);
			}
		}
		for(int i = getYPos() - 1; i > 0; i--) {
			if(Tile.getTiles()[getXPos() - 1][i - 1].hasPiece()) {
				if(Tile.getTiles()[getXPos() - 1][i - 1].getPiece().getSide() != this.getSide()) {
					tiles.add(Tile.getTiles()[getXPos() - 1][i - 1]);
				}
				break;
			}
			else {
				tiles.add(Tile.getTiles()[getXPos() - 1][i - 1]);
			}
		}
		
		return tiles;
	}

	public static void promote(Tile tile) {
		for(int i = 0; i < promotedPawns.length; i++) {
			if(promotedPawns[i] == null) {
				promotedPawns[i] = new Rook(tile.getXPos(), tile.getYPos(), tile.getPiece().getSide());
				tile.getPiece().setLayoutX(-Tile.TILE_SIZE);
				tile.getPiece().setLayoutY(-Tile.TILE_SIZE);
				tile.getPiece().setVisible(false);
				tile.setPiece(promotedPawns[i]);
				Game.pieceGroup.getChildren().add(promotedPawns[i]);
				break;
			}
		}
	}
}
