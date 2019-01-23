package model;

import java.util.ArrayList;

import enums.Side;
import javafx.scene.image.Image;

public class Bishop extends Piece {
	
	private static Bishop[] bishops = new Bishop[4];
	private static Bishop[] promotedPawns = new Bishop[16];
	
	private Bishop(int x, int y, Side side) {
		super(x, y, side);
		if(side == Side.Black)
			setImage(new Image("/assets/img/black_bishop.png"));
		else
			setImage(new Image("/assets/img/white_bishop.png"));
	}
	
	public static Bishop initBishop(Side side) {
		
		int[] x = new int[]{3, 6};
		int[] y = new int[]{1, 8};
		
		if(side == Side.Black) {
			if(bishops[0] == null) {
				bishops[0] = new Bishop(x[0], y[0], side);
				return bishops[0];
			}
			else if(bishops[1] == null) {
				bishops[1] = new Bishop(x[1], y[0], side);
				return bishops[1];
			}
			else {
				if(bishops[0].getXPos() != x[0] && bishops[0].getYPos() != y[0]) {
					bishops[0].setXPos(x[0]);
					bishops[0].setYPos(y[0]);
					return bishops[0];
				}
				else {
					bishops[1].setXPos(x[1]);
					bishops[1].setYPos(y[0]);
					return bishops[1];
				}
			}
		}
		else {
			if(bishops[2] == null) {
				bishops[2] = new Bishop(x[0], y[1], side);
				return bishops[2];
			}
			else if(bishops[3] == null) {
				bishops[3] = new Bishop(x[1], y[1], side);
				return bishops[3];
			}
			else {
				if(bishops[2].getXPos() != x[0] && bishops[2].getYPos() != y[1]) {
					bishops[2].setXPos(x[0]);
					bishops[2].setYPos(y[1]);
					return bishops[2];
				}
				else {
					bishops[3].setXPos(x[1]);
					bishops[3].setYPos(y[1]);
					return bishops[3];
				}
			}
		}
	}

	@Override
	public void move(Tile tile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void capture(Tile tile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Tile> getAllowedMoves() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		// Get Moves in diagonal Axis
		int steps = (getXPos() >= getYPos()) ? getYPos() : getXPos();
		for(int i = steps - 1; i > 0; i--) {
			if(Tile.getTiles()[getXPos() - (steps - i + 1)][getYPos() - (steps - i + 1)].hasPiece()) {
				if(Tile.getTiles()[getXPos() - (steps - i + 1)][getYPos() - (steps - i + 1)].getPiece().getSide() != this.getSide()) {
					tiles.add(Tile.getTiles()[getXPos() - (steps - i + 1)][getYPos() - (steps - i + 1)]);
				}
				break;
			}
			else {
				tiles.add(Tile.getTiles()[getXPos() - (steps - i + 1)][getYPos() - (steps - i + 1)]);
			}
		}
		steps = (getXPos() <= getYPos()) ? 8 - getYPos() : 8 - getXPos(); 
		for(int i = 0; i < steps; i++) {
			if(Tile.getTiles()[getXPos() + i][getYPos() + i].hasPiece()) {
				if(Tile.getTiles()[getXPos() + i][getYPos() + i].getPiece().getSide() != this.getSide()) {
					tiles.add(Tile.getTiles()[getXPos() + i][getYPos() + i]);
				}
				break;
			}
			else {
				tiles.add(Tile.getTiles()[getXPos() + i][getYPos() + i]);
			}
		}
		
		// Moves on other diagonal axis
		steps = (8 - getXPos() >= getYPos()) ? getYPos() - 1 : 8 - getXPos();
		for(int i = 1; i <= steps; i++) {
			if(Tile.getTiles()[getXPos() + i - 1][getYPos() - i - 1].hasPiece()) {
				if(Tile.getTiles()[getXPos() + i - 1][getYPos() - i - 1].getPiece().getSide() != this.getSide()) {
					tiles.add(Tile.getTiles()[getXPos() + i - 1][getYPos() - i - 1]);
				}
				break;
			}
			else {
				tiles.add(Tile.getTiles()[getXPos() + i - 1][getYPos() - i - 1]);
			}
		}
		steps = (8 - getYPos() < getXPos()) ? 8 - getYPos() : getXPos() - 1;
		for(int i = 1; i <= steps; i++) {
			if(Tile.getTiles()[getXPos() - i - 1][getYPos() + i - 1].hasPiece()) {
				if(Tile.getTiles()[getXPos() - i - 1][getYPos() + i - 1].getPiece().getSide() != this.getSide()) {
					tiles.add(Tile.getTiles()[getXPos() - i - 1][getYPos() + i - 1]);
				}
				break;
			}
			else {
				tiles.add(Tile.getTiles()[getXPos() - i - 1][getYPos() + i - 1]);
			}
		}
		
		return tiles;
	}
	
	public static void promote(Tile tile) {
		for(int i = 0; i < promotedPawns.length; i++) {
			if(promotedPawns[i] == null) {
				promotedPawns[i] = new Bishop(tile.getXPos(), tile.getYPos(), tile.getPiece().getSide());
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
