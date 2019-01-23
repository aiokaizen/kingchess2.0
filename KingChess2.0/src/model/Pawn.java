package model;

import java.util.ArrayList;

import Theme.Theme;
import enums.Side;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Pawn extends Piece {
	
	private static Pawn[] pawns = new Pawn[16];
	private boolean moved;
	
	private Pawn(int x, int y, Side side) {
		super(x, y, side);
		if(side == Side.Black)
			setImage(new Image("/assets/img/black_pawn.png"));
		else
			setImage(new Image("/assets/img/white_pawn.png"));
	}
	
	private Tile getTile() {
		return Tile.getTiles()[getXPos() - 1][getYPos() - 1]; 
	}
	
	public static Pawn initPawn(Side side) {
		
		int[] x = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
		int[] y = new int[] {2, 7};
		
		if(side == Side.Black) {
			for(int i = 0; i < x.length; i++) {
				if(pawns[i] == null) {
					pawns[i] = new Pawn(x[i], y[0], side);
					return pawns[i];
				}
				else {
					if(pawns[i].getXPos() != x[i] && pawns[i].getYPos() != y[0]) {
						pawns[i].setXPos(x[i]);
						pawns[i].setYPos(y[0]);
						return pawns[i];
					}
				}
			}
		}
		else {
			for(int i = x.length; i < x.length * 2; i++) {
				if(pawns[i] == null) {
					pawns[i] = new Pawn(x[i - x.length], y[1], side);
				}
				else {
					if(pawns[i].getXPos() != x[i - x.length] && pawns[i].getYPos() != y[1]) {
						pawns[i].setXPos(x[i - x.length]);
						pawns[i].setYPos(y[1]);
					}
				}
				return pawns[i];
			}
		}
		
		return null;
	}

	@Override
	public void move(Tile tile) {
		if((this.getYPos() < 7 && this.getSide() == Side.White) || (this.getYPos() > 2 && this.getSide() == Side.Black))
		moved = true;
		System.out.println("move");
		if((this.getYPos() == 1 && this.getSide() == Side.White) || (this.getYPos() == 8 && this.getSide() == Side.Black))
			promote();
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
		int[][] positions;
		if(this.getSide() == Side.Black) {
			positions = (!moved) ? new int[][] {
				new int[] {x, y + 1},
				new int[] {x, y + 2}
			} : new int[][] { new int[] {x, y + 1} };
		}
		else {
			positions = (!moved) ? new int[][] {
				new int[] {x, y - 1},
				new int[] {x, y - 2}
			} : new int[][] { new int[] {x, y - 1} };
		}
		
		for(int i = 0; i < positions.length; i++) {
			if(positions[i][0] <= 8 && positions[i][0] > 0 && positions[i][1] <= 8 && positions[i][1] > 0) {				
				if(!Tile.getTiles()[positions[i][0] - 1][positions[i][1] - 1].hasPiece()) {
					tiles.add(Tile.getTiles()[positions[i][0] - 1][positions[i][1] - 1]);
				}
				else
					break;
			}
		}
		
		x -= 1;
		y -= 1;
		// checking if the pawn can catch
		if(y > 0 && this.getSide() == Side.White) {
			if(x < 7 && Tile.getTiles()[x + 1][y - 1].hasPiece() && Tile.getTiles()[x + 1][y - 1].getPiece().getSide() != Side.White)
				tiles.add(Tile.getTiles()[x + 1][y - 1]);
			if(x > 0 && Tile.getTiles()[x - 1][y - 1].hasPiece() && Tile.getTiles()[x - 1][y - 1].getPiece().getSide() != Side.White)
				tiles.add(Tile.getTiles()[x - 1][y - 1]);
		}
		
		if(y < 7 && this.getSide() == Side.Black) {
			if(x < 7 && Tile.getTiles()[x + 1][y + 1].hasPiece() && Tile.getTiles()[x + 1][y + 1].getPiece().getSide() != Side.Black)
				tiles.add(Tile.getTiles()[x + 1][y + 1]);
			if(x > 0 && Tile.getTiles()[x - 1][y + 1].hasPiece() && Tile.getTiles()[x - 1][y + 1].getPiece().getSide() != Side.Black)
				tiles.add(Tile.getTiles()[x - 1][y + 1]);
		}
		
		return tiles;
	}
	
	public void promote() {
		
		Pane root = (Pane) this.getParent().getParent().getParent();
		
		VBox parent = new VBox(20);
		HBox pieces = new HBox(0);
		String side;
		if(this.getSide() == Side.Black)
			side = "black";
		else
			side = "white";
		ImageView[] images = new ImageView[] {
				new ImageView(new Image("/assets/img/" + side + "_queen.png")),
				new ImageView(new Image("/assets/img/" + side + "_rook.png")),
				new ImageView(new Image("/assets/img/" + side + "_knight.png")),
				new ImageView(new Image("/assets/img/" + side + "_bishop.png"))
		};
		AnchorPane[] imgConts = new AnchorPane[] {
				new AnchorPane(images[0]),
				new AnchorPane(images[1]),
				new AnchorPane(images[2]),
				new AnchorPane(images[3])
		};
		for(int i = 0; i < images.length; i++) {
			images[i].setFitWidth(70);
			images[i].setFitHeight(70);
			imgConts[i].setMaxWidth(70);
			imgConts[i].setMaxHeight(70);
			imgConts[i].getStyleClass().add("promotion-button");
			imgConts[i].setId("btn" + (i + 1));
			imgConts[i].setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					AnchorPane pane = (AnchorPane)e.getSource();
					if(pane.getId().equals("btn1")) {	   // Queen
						Queen.promote(getTile());
					}
					else if(pane.getId().equals("btn2")) { // Rook
						Rook.promote(getTile());
					}
					else if(pane.getId().equals("btn3")) { // Knight
						Knight.promote(getTile());
					}
					else if(pane.getId().equals("btn4")) { // Bishop
						Bishop.promote(getTile());
					}
					
					root.getChildren().remove(parent);
				}
				
			});
		}
		parent.setPrefWidth(root.getPrefWidth());
		parent.setPrefHeight(root.getPrefHeight());
		parent.setAlignment(Pos.CENTER);
		pieces.setAlignment(Pos.CENTER);
		pieces.setMaxWidth(4.5 * images[0].getFitWidth());
		pieces.setPrefHeight(1.5 * images[0].getFitHeight());
		pieces.getStyleClass().add("promotion-container");
		pieces.setStyle("-fx-background-color: #" + Theme.getLightColor().toString().substring(2) + "; -fx-border-style: solid; -fx-border-width: 3; fx-border-color: #" + Theme.getDarkColor().toString().substring(2) + ";");
		pieces.getChildren().addAll(imgConts);
		parent.getChildren().addAll(pieces);
		root.getChildren().add(parent);
	}
}
