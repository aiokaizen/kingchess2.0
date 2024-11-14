package model;

import Theme.Theme;
import application.Main;
import enums.Side;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class Board {
	
	public static final int BOARD_HEIGHT = 8;
	public static final int BOARD_WIDTH = 8;
	private static Label message;
	private static AnchorPane board;
	public static AnchorPane getBoard() {
		return board;
	}

	public static void showMessage(String message) {
		Board.message.setText(message);
	}
	
	public static Parent createContent() {
		Pane root = new Pane();
		AnchorPane board = new AnchorPane();
		Theme.initTheme("classic");
		Background darkBg = new Background(new BackgroundFill(Paint.valueOf(Theme.getDarkColor().darker().darker().toString().substring(2)), null, null));
		board.setPrefWidth(BOARD_WIDTH * Tile.TILE_SIZE);
		board.setPrefHeight(BOARD_HEIGHT * Tile.TILE_SIZE);
		root.setPrefWidth(board.getPrefWidth() + 50);
		root.setPrefHeight(board.getPrefHeight() + 50);
		
		// create top bar
		AnchorPane topBar = new AnchorPane();
		topBar.setBackground(darkBg);
		topBar.setLayoutX(10);
		topBar.setLayoutY(10);
		topBar.setPrefHeight(20);
		topBar.setPrefWidth(board.getPrefWidth() + 30);
		
		// create right bar
		AnchorPane rightBar = new AnchorPane();
		rightBar.setBackground(darkBg);
		rightBar.setLayoutX(board.getPrefWidth() + 30);
		rightBar.setLayoutY(10);
		rightBar.setPrefHeight(board.getPrefHeight() + 40);
		rightBar.setPrefWidth(20);
		
		// Create the buttom bar with Letter indexes
		AnchorPane hEnum = new AnchorPane();
		hEnum.setPrefWidth(board.getPrefWidth());
		hEnum.setPrefHeight(20);
		hEnum.setLayoutX(30);
		hEnum.setLayoutY(Tile.TILE_SIZE * BOARD_WIDTH + 30);
		hEnum.setBackground(new Background(new BackgroundFill(Paint.valueOf(Theme.getDarkColor().darker().darker().toString().substring(2)), null, null)));
		Label number;
		for(int i = 0; i < BOARD_WIDTH; i++) {
			number = new Label(String.valueOf(Character.toChars(i + 97)));
			number.setPrefWidth(Tile.TILE_SIZE);
			number.setPrefHeight(20);
			number.setLayoutX(i * Tile.TILE_SIZE);
			number.setTextFill(Theme.getLightColor());
			number.setLayoutY(0);
			number.setAlignment(Pos.CENTER);
			hEnum.getChildren().add(number);
		}
		
		// Create the vertical bar with number indexes
		AnchorPane vEnum = new AnchorPane();
		vEnum.setPrefHeight(board.getPrefHeight() + 20);
		vEnum.setPrefWidth(20);
		vEnum.setLayoutY(30);
		vEnum.setBackground(darkBg);
		vEnum.setLayoutX(10);
		for(int i = 0; i < BOARD_WIDTH; i++) {
			number = new Label(String.valueOf(i + 1));
			number.setPrefHeight(Tile.TILE_SIZE);
			number.setPrefWidth(20);
			number.setLayoutY((BOARD_WIDTH - 1 - i) * Tile.TILE_SIZE);
			number.setLayoutX(0);
			number.setAlignment(Pos.CENTER);
			number.setTextFill(Theme.getLightColor());
			vEnum.getChildren().add(number);
		}
		
		message = new Label();
		message.setPrefWidth(BOARD_WIDTH * Tile.TILE_SIZE + 60);
		message.setPrefHeight(20);
		message.setLayoutX(0);
		message.setPadding(new Insets(0, 0, 0, 10));
		message.setLayoutY(BOARD_HEIGHT * Tile.TILE_SIZE + 50);
		message.setStyle("-fx-background-color: #" + Theme.getLightColor().toString().substring(2) + "; -fx-text-fill: #" + Theme.getDarkColor().darker().darker().toString().substring(2) + ";");
		
		double minWidth = board.getPrefWidth() + 76;
		double minHeight = board.getPrefHeight() + 110;
		Main.primaryStage.setMinWidth(minWidth);
		Main.primaryStage.setMinHeight(minHeight);
		
//		Main.primaryStage.setMaxWidth(minWidth);
//		Main.primaryStage.setMaxHeight(minHeight);
		root.getChildren().addAll(hEnum, vEnum, topBar, rightBar, message, board);
		board.setLayoutX(30);
		board.setLayoutY(30);
		Background rootBg = new Background(new BackgroundFill(Paint.valueOf("#eee"), null, null)); // Theme.getDarkColor().brighter().toString().substring(2)
		root.setBackground(rootBg);
		
		initBoard(board);
		Board.board = board;
		initPieces(board);
		
		root.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent k) {
				System.out.println("key pressed");
				if(k.getCode() == KeyCode.I) {
					System.out.println("i key pressed");
					initBoard(board);
				}
			}
			
		});
		
		return root;
	}
	
	public static void initBoard(AnchorPane board) {
		for(int i = 0; i < Tile.initTiles().length; i++) {
			Game.tileGroup.getChildren().addAll(Tile.initTiles()[i]);
		}
		
		board.getChildren().addAll(Game.tileGroup);
	}
	
	public static void initPieces(AnchorPane board) {

		for(int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_HEIGHT; j++) {
				Tile tile = Tile.getTiles()[i][j];
				
				// white king
				if(i == 4 && j == 7) {
					King king = King.initKing(Side.White);
					tile.setPiece(king);
					Game.pieceGroup.getChildren().add(king);
				}
				// white queen
				if(i == 3 && j == 7) {
					Queen queen = Queen.initQueen(Side.White);
					tile.setPiece(queen);
					Game.pieceGroup.getChildren().add(queen);
				}
				// white bishop1
				if(i == 2 && j == 7) {
					Bishop bishop = Bishop.initBishop(Side.White);
					tile.setPiece(bishop);
					Game.pieceGroup.getChildren().add(bishop);
				}
				// white bishop2
				if(i == 5 && j == 7) {
					Bishop bishop = Bishop.initBishop(Side.White);
					tile.setPiece(bishop);
					Game.pieceGroup.getChildren().add(bishop);
				}
				// white knight1
				if(i == 1 && j == 7) {
					Knight knight = Knight.initKnight(Side.White);
					tile.setPiece(knight);
					Game.pieceGroup.getChildren().add(knight);
				}
				// white knight2
				if(i == 6 && j == 7) {
					Knight knight = Knight.initKnight(Side.White);
					tile.setPiece(knight);
					Game.pieceGroup.getChildren().add(knight);
				}
				// white rook1
				if(i == 0 && j == 7) {
					Rook rook = Rook.initRook(Side.White);
					tile.setPiece(rook);
					Game.pieceGroup.getChildren().add(rook);
				}
				// white rook2
				if(i == 7 && j == 7) {
					Rook rook = Rook.initRook(Side.White);
					tile.setPiece(rook);
					Game.pieceGroup.getChildren().add(rook);
				}
				// white pawns
				if(j == 6) {
					Pawn pawn = Pawn.initPawn(Side.White);
					tile.setPiece(pawn);
					Game.pieceGroup.getChildren().add(pawn);
				}
				
				
				
				// black king
				if(i == 4 && j == 0) {
					King king = King.initKing(Side.Black);
					tile.setPiece(king);
					Game.pieceGroup.getChildren().add(king);
				}
				// black queen
				if(i == 3 && j == 0) {
					Queen queen = Queen.initQueen(Side.Black);
					tile.setPiece(queen);
					Game.pieceGroup.getChildren().add(queen);
				}
				// black bishop1
				if(i == 2 && j == 0) {
					Bishop bishop = Bishop.initBishop(Side.Black);
					tile.setPiece(bishop);
					Game.pieceGroup.getChildren().add(bishop);
				}
				// black bishop2
				if(i == 5 && j == 0) {
					Bishop bishop = Bishop.initBishop(Side.Black);
					tile.setPiece(bishop);
					Game.pieceGroup.getChildren().add(bishop);
				}
				// white knight1
				if(i == 1 && j == 0) {
					Knight knight = Knight.initKnight(Side.Black);
					tile.setPiece(knight);
					Game.pieceGroup.getChildren().add(knight);
				}
				// white knight2
				if(i == 6 && j == 0) {
					Knight knight = Knight.initKnight(Side.Black);
					tile.setPiece(knight);
					Game.pieceGroup.getChildren().add(knight);
				}
				// black rook1
				if(i == 0 && j == 0) {
					Rook rook = Rook.initRook(Side.Black);
					tile.setPiece(rook);
					Game.pieceGroup.getChildren().add(rook);
				}
				// black rook2
				if(i == 7 && j == 0) {
					Rook rook = Rook.initRook(Side.Black);
					tile.setPiece(rook);
					Game.pieceGroup.getChildren().add(rook);
				}
				// black pawns
				if(j == 1) {
					Pawn pawn = Pawn.initPawn(Side.Black);
					tile.setPiece(pawn);
					Game.pieceGroup.getChildren().add(pawn);
				}
			}
		}
		board.getChildren().addAll(Game.pieceGroup);
	}

}
