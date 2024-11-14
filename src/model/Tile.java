package model;

import java.util.ArrayList;

import Theme.Theme;
import enums.Side;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

	public static final int TILE_SIZE = 60;
	private static Tile[][] tiles;
	private int XPos;
	private int YPos;
	private int color;
	private Piece piece;
//	private Side targetedBy;
	
	public static Tile[][] getTiles() {
		return tiles;
	}

	public int getXPos() {
		return XPos;
	}

	public void setXPos(int posX) {
		this.XPos = posX;
	}

	public int getYPos() {
		return YPos;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setYPos(int posY) {
		this.YPos = posY;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	{
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Tile.initTilesStyle();
			}
		});
	}
	
	private Tile(int color, int x, int y) { // color value: 0 if the color is dark, 1 if it is light
		
		setWidth(TILE_SIZE);
		setHeight(TILE_SIZE);
		
		relocate((x - 1) * TILE_SIZE, (y - 1) * TILE_SIZE);
		this.setXPos(x);
		this.setYPos(y);
		this.color = color;
		setFill((color == 0) ? Theme.getDarkColor() : Theme.getLightColor());
	}
	
	public static Tile[][] initTiles() {
		if(tiles == null) {
			tiles = new Tile[Board.BOARD_WIDTH][Board.BOARD_HEIGHT];
			for(int i = 1; i <= Board.BOARD_WIDTH; i++) {
				for(int j = 1; j <= Board.BOARD_HEIGHT; j++) {
					Tile tile = new Tile((i + j + 1) % 2, i, j);
					tiles[i - 1][j - 1] = tile;
				}
			}
		}
		
		return tiles;
	}
	
	public boolean hasPiece() {
		return piece != null;
	}
	
	public boolean isAttacked(Side side) {
		Side op = (side == Side.White) ? Side.Black : Side.White;
		
		for(Node node : Game.pieceGroup.getChildren()) {
			Piece piece = (Piece) node;
			if(piece.getSide() == op) {
				if(piece.getAllowedMoves().contains(this)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void initTilesStyle() {
		for(Tile[] tilesRows : Tile.getTiles()) {
			for(Tile tile : tilesRows) {
				if(!(tile.getPiece() != null && tile.getPiece() instanceof King && ((King) tile.getPiece()).isInCheck()))
					tile.setFill((tile.getColor() == 0) ? Theme.getDarkColor() : Theme.getLightColor());
			}
		}
		
		ArrayList<Node> circles = new ArrayList<Node>();
		circles.addAll(((Group) Tile.getTiles()[0][0].getParent()).getChildren());
		for(Node node : circles) {
			if(node instanceof Circle) {
				((Group) Tile.getTiles()[0][0].getParent()).getChildren().remove(node);
			}
		}
	}

	public static void setTargeted(Side side, ArrayList<Tile> tiles) {
		
		initTilesStyle();
		Color color;
//		if(side == Side.White)
			color = Color.valueOf("#D25B5B");
//		else
//			color = Color.valueOf("#541616");
		
		Circle circle;
		for(Tile tile : tiles) {
			circle = new Circle(TILE_SIZE / 8, color);
			((Group) tiles.get(0).getParent()).getChildren().add(circle);
			circle.setLayoutX(tile.getLayoutX() + TILE_SIZE / 2);
			circle.setLayoutY(tile.getLayoutY() + TILE_SIZE / 2);
			circle.toFront();
			
			if(tile.getPiece() != null && tile.getPiece().getSide() != side && !(tile.getPiece() instanceof King))
				tile.setFill(color);
		}
	}
	
	public String getTileCode() {
		String pos;
		pos = String.valueOf(Character.toChars(getXPos() + 96)) + String.valueOf(9 - getYPos());
		return pos;
	}
	
	public static String getTileCode(Tile tile) {
		String pos;
		pos = String.valueOf(Character.toChars(tile.getXPos() + 96)) + String.valueOf(9 - tile.getYPos());
		return pos;
	}
	
	public static Tile getTile(Piece piece) {
		return tiles[piece.getXPos() - 1][piece.getYPos() - 1];
	}
	
	public static Tile getTile(int x, int y) {
		return tiles[x][y];
	}

}
