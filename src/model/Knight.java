package model;

import java.util.ArrayList;

import enums.Side;
import javafx.scene.image.Image;

public class Knight extends Piece {
	
	private static Knight[] knights = new Knight[4];
	private static Knight[] promotedPawns = new Knight[16];
	
	private Knight(int x, int y, Side side) {
		super(x, y, side);
		if(side == Side.Black)
			setImage(new Image("/assets/img/black_knight.png"));
		else
			setImage(new Image("/assets/img/white_knight.png"));
	}
	
	public static Knight initKnight(Side side) {
		
		int[] x = new int[]{2, 7};
		int[] y = new int[]{1, 8};
		
		if(side == Side.Black) {
			if(knights[0] == null) {
				knights[0] = new Knight(x[0], y[0], side);
				return knights[0];
			}
			else if(knights[1] == null) {
				knights[1] = new Knight(x[1], y[0], side);
				return knights[1];
			}
			else {
				if(knights[0].getXPos() != x[0] && knights[0].getYPos() != y[0]) {
					knights[0].setXPos(x[0]);
					knights[0].setYPos(y[0]);
					return knights[0];
				}
				else {
					knights[1].setXPos(x[1]);
					knights[1].setYPos(y[0]);
					return knights[1];
				}
			}
		
		}
		else {
			if(knights[2] == null) {
				knights[2] = new Knight(x[0], y[1], side);
				return knights[2];
			}
			else if(knights[3] == null) {
				knights[3] = new Knight(x[1], y[1], side);
				return knights[3];
			}
			else {
				if(knights[2].getXPos() != x[0] && knights[2].getYPos() != y[1]) {
					knights[2].setXPos(x[0]);
					knights[2].setYPos(y[1]);
					return knights[2];
				}
				else {
					knights[3].setXPos(x[1]);
					knights[3].setYPos(y[1]);
					return knights[3];
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
		
		// Get all moves
		int x = getXPos();
		int y = getYPos();
		
		int[][] positions = new int[][] {
			new int[] {x - 2, y + 1},
			new int[] {x - 1, y - 2},
			new int[] {x + 1, y - 2},
			new int[] {x + 2, y - 1},
			new int[] {x + 2, y + 1},
			new int[] {x + 1, y + 2},
			new int[] {x - 1, y + 2},
			new int[] {x - 2, y - 1}
		};
		
		for(int i = 0; i < 8; i++) {
			if(positions[i][0] <= 8 && positions[i][0] > 0 && positions[i][1] <= 8 && positions[i][1] > 0) {				
				if(Tile.getTiles()[positions[i][0] - 1][positions[i][1] - 1].hasPiece()) {
					if(Tile.getTiles()[positions[i][0] - 1][positions[i][1] - 1].getPiece().getSide() != this.getSide()) {
						tiles.add(Tile.getTiles()[positions[i][0] - 1][positions[i][1] - 1]);
					}
				}
				else {
					tiles.add(Tile.getTiles()[positions[i][0] - 1][positions[i][1] - 1]);
				}
			}
		}
		
		return tiles;
	}

	public static void promote(Tile tile) {
		for(int i = 0; i < promotedPawns.length; i++) {
			if(promotedPawns[i] == null) {
				promotedPawns[i] = new Knight(tile.getXPos(), tile.getYPos(), tile.getPiece().getSide());
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
