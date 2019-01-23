package model;

import java.util.ArrayList;

import enums.Side;
import javafx.scene.image.Image;

public class Queen extends Piece {
	
	private static Queen blackQueen;
	private static Queen whiteQueen;
	private static Queen[] promotedPawns = new Queen[16];
	
	private Queen(int x, int y, Side side) {
		super(x, y, side);
		if(side == Side.Black)
			setImage(new Image("/assets/img/black_queen.png"));
		else
			setImage(new Image("/assets/img/white_queen.png"));
	}
	
	public static Queen initQueen(Side side) {
		
		int x = 4;
		int y;
		
		if(side == Side.Black) {
			y = 1;
			if(blackQueen == null)
				blackQueen = new Queen(0, 0, side);
			blackQueen.setXPos(x);
			blackQueen.setYPos(y);
			return blackQueen;
		}
		else {
			y = 8;
			if(whiteQueen == null)
				whiteQueen = new Queen(0, 0, side);
			whiteQueen.setXPos(x);
			whiteQueen.setYPos(y);
		
			return whiteQueen;
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
				promotedPawns[i] = new Queen(tile.getXPos(), tile.getYPos(), tile.getPiece().getSide());
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
