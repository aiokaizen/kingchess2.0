package model;

import java.util.ArrayList;

import audio.MusicPlayer;
import enums.Side;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public abstract class Piece extends ImageView {

	private int xPos;
	private int yPos;
	private Side side;
	private Tile initialPos;
	private boolean isCaptured;
	private boolean isDragged;
	private static MusicPlayer sfx;
	
	public Tile getInitialPosition() {
		return initialPos;
	}

	public int getXPos() {
		return xPos;
	}

	public void setXPos(int x) {
		this.xPos = x;
	}

	public int getYPos() {
		return yPos;
	}

	public void setYPos(int y) {
		this.yPos = y;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public boolean getIsDragged() {
		return isDragged;
	}

	public void setIsDragged(boolean isDragged) {
		this.isDragged = isDragged;
	}

	public boolean getIsCaptured() {
		return isCaptured;
	}

	public void setIsCaptured(boolean isCaptured) {
		this.isCaptured = isCaptured;
	}

	public String getPiecePos() {
		String pos;
		pos = String.valueOf(Character.toChars(getXPos() + 96)) + String.valueOf(9 - getYPos());
		return pos;
	}

	public void capture() {
		this.isCaptured = true;
		// if(getSide() == Side.Black) {
		// this.setLayoutY((16 - Game.getCapturedBlackPieces()) * (Tile.TILE_SIZE / 4)
		// );
		// Game.captureBlackPiece();
		// }
		// else {
		// this.setLayoutY((16 - Game.getCapturedBlackPieces()) * (Tile.TILE_SIZE / 4)
		// );
		// Game.captureWhitePiece();
		// }
		// System.out.println("capturing");
		// this.setLayoutX((Tile.TILE_SIZE * Board.BOARD_WIDTH) + 5);
		// System.out.println(this.getLayoutY());
		// this.setFitWidth(Tile.TILE_SIZE / 4);
		// this.setFitHeight(Tile.TILE_SIZE / 4);

		relocate(-Tile.TILE_SIZE, -Tile.TILE_SIZE);
		this.setVisible(false);
	}

	{
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Tile.initTilesStyle();
				Tile.setTargeted(((Piece) e.getSource()).getSide(), ((Piece) e.getSource()).getAllowedMoves());
			}
		});

		this.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setIsDragged(true);
				((Piece) e.getSource()).toFront();
			}
		});

		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				double mouseX = e.getX();
				double mouseY = e.getY();
				Piece piece = (Piece) e.getSource();
				piece.setX(mouseX - piece.getFitWidth() / 2);
				piece.setY(mouseY - piece.getFitHeight() / 2);
				setIsDragged(true);
			}
		});

		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Piece piece = (Piece) e.getSource();
				if (piece.getIsDragged()) {
					int newX = ((Double) ((piece.getX() + piece.getLayoutX() + (piece.getFitWidth() / 2))
							/ Tile.TILE_SIZE)).intValue();
					int newY = ((Double) ((piece.getY() + piece.getLayoutY() + (piece.getFitHeight() / 2))
							/ Tile.TILE_SIZE)).intValue();
					if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
						Tile newTile = Tile.getTiles()[newX][newY];
						move(piece, newTile, false);
					} else {
						Board.showMessage("Illegal move!");
						piece.relocate((piece.getXPos() - 1) * Tile.TILE_SIZE, (piece.getYPos() - 1) * Tile.TILE_SIZE);
					}
				}
			}
		});
	}

	protected Piece(int x, int y, Side side) {
		setXPos(x);
		setYPos(y);
		setSide(side);
		initialPos = Tile.getTile(x - 1, y - 1);
		setFitWidth(Tile.TILE_SIZE);
		setFitHeight(Tile.TILE_SIZE);
		setLayoutX((x - 1) * Tile.TILE_SIZE);
		setLayoutY((y - 1) * Tile.TILE_SIZE);
	}

	public static void move(Piece piece, Tile tile, boolean forceMove) {

			if (piece.getAllowedMoves().contains(tile) || forceMove) {
				if (Game.getTurn() == piece.getSide()) {
					boolean go = false;
					if(forceMove)
						go = true;
					else if(!isCheck(piece, tile))
						go = true;
					else
						go = false;
						
					if (go) {
					Tile oldTile = Tile.getTiles()[piece.getXPos() - 1][piece.getYPos() - 1];
					int sfxNum = (tile.getXPos() + tile.getYPos()) % 2 + 1;

					// checking if the destination tile has a piece in it to perform a catch
					if (tile.hasPiece()) {
						tile.getPiece().capture();
						sfx = new MusicPlayer(false, "catch_0" + sfxNum);
						if(!forceMove) {
							Board.showMessage(piece.getSide().toString() + ": "
									+ Move.move(piece, oldTile, tile, true, false, false, false, -1).getMove());
						}
					} else {
						if(!forceMove) {
							if(piece instanceof King) {
								if(piece.getXPos() + 2 == tile.getXPos())
									Board.showMessage(piece.getSide().toString() + ": "
											+ Move.move(piece, oldTile, tile, false, false, false, false, 0).getMove());
								else if(piece.getXPos() - 2 == tile.getXPos())
									Board.showMessage(piece.getSide().toString() + ": "
											+ Move.move(piece, oldTile, tile, false, false, false, false, 1).getMove());
							}
							else
								Board.showMessage(piece.getSide().toString() + ": "
									+ Move.move(piece, oldTile, tile, false, false, false, false, -1).getMove());
						}
						sfxNum = (tile.getXPos() + tile.getYPos()) % 8 + 1;
						sfx = new MusicPlayer(false, "move_0" + sfxNum);
					}

					oldTile.setPiece(null);
					tile.setPiece(piece);
					piece.relocate((tile.getXPos() - 1) * Tile.TILE_SIZE, (tile.getYPos() - 1) * Tile.TILE_SIZE);
					piece.setXPos(tile.getXPos());
					piece.setYPos(tile.getYPos());
					piece.setIsDragged(false);
					sfx.run();

					if (piece instanceof King) {
						if (oldTile.getXPos() + 2 == piece.getXPos()) {
							System.out.println("castling king side");
							move(Tile.getTiles()[piece.getXPos()][piece.getYPos() - 1].getPiece(),
									Tile.getTiles()[piece.getXPos() - 2][piece.getYPos() - 1], true);
						} else if (oldTile.getXPos() - 2 == piece.getXPos()) {
							System.out.println("castling queen side");
							move(Tile.getTiles()[piece.getXPos() - 3][piece.getYPos() - 1].getPiece(),
									Tile.getTiles()[piece.getXPos()][piece.getYPos() - 1], true);
						}
					}

					if (piece.getSide() == Side.White)
						Game.setTurn(Side.Black);
					else
						Game.setTurn(Side.White);
				} else {
					Board.showMessage("Checkkkk!");
					piece.relocate((piece.getXPos() - 1) * Tile.TILE_SIZE, (piece.getYPos() - 1) * Tile.TILE_SIZE);
				}
			} else {
					Board.showMessage("It is " + Game.getTurn() + "'s turn to play!");
					piece.relocate((piece.getXPos() - 1) * Tile.TILE_SIZE, (piece.getYPos() - 1) * Tile.TILE_SIZE);
				}
		} else {
				Board.showMessage("Illegal move!");
				piece.relocate((piece.getXPos() - 1) * Tile.TILE_SIZE, (piece.getYPos() - 1) * Tile.TILE_SIZE);
			}
		if (tile != null) {
			piece.move(tile);
//			isCheck();
		}
	}

	public void move(Tile tile) {
		int newX = ((Double) ((getX() + getLayoutX() + (getFitWidth() / 2)) / Tile.TILE_SIZE)).intValue();
		int newY = ((Double) ((getY() + getLayoutY() + (getFitHeight() / 2)) / Tile.TILE_SIZE)).intValue();

		Tile.getTiles()[getXPos() - 1][getYPos() - 1].setPiece(null);
		tile.setPiece(this);
		relocate(newX * Tile.TILE_SIZE, newY * Tile.TILE_SIZE);
		setXPos(newX + 1);
		setYPos(newY + 1);
		setIsDragged(false);
		sfx.run();
	}

	public abstract void capture(Tile tile);

	public abstract ArrayList<Tile> getAllowedMoves();
	
	// returns -1 if no check, 0 black checked, 1 white checked
	public static int isCheck() {
		int check = -1;
		boolean isBlackChecked = false;
		boolean isWhiteChecked = false;
		King whiteKing = King.getWhiteKing();
		King blackKing = King.getBlackKing();
//		whiteKing.setInCheck(false);
//		blackKing.setInCheck(false);
		Tile blackKingTile = Tile.getTile(King.getBlackKing());
		Tile whiteKingTile = Tile.getTile(King.getWhiteKing());

		for(Node node : Game.pieceGroup.getChildren()) {
			Piece piece = (Piece) node;
			if (piece != null) {
				if(piece.getAllowedMoves().contains(whiteKingTile)
						&& piece.getSide() != Side.White
						&& !piece.isCaptured) {
					isWhiteChecked = true;
					whiteKing.setInCheck(true);
					System.out.println("this is check: " + whiteKingTile.getTileCode());
					break;
				}
				if(piece.getAllowedMoves().contains(blackKingTile)
						&& piece.getSide() != Side.Black
						&& !piece.isCaptured) {
					isBlackChecked = true;
					blackKing.setInCheck(true);
					System.out.println("this is checkk: " + blackKingTile.getTileCode());
					break;
				}
			}
		}
		if(isWhiteChecked)
			check = 1;
		else if(isBlackChecked)
			check = 0;
		
		return check;
	}

	public static boolean isCheck(Piece piece, Tile destination) {

		boolean isCheck = false;
		Piece destinationPiece = null;
		Piece king = null;
		Tile kingTile = null;
		Tile pieceTile = Tile.getTiles()[piece.getXPos() - 1][piece.getYPos() - 1];
		
		pieceTile.setPiece(null);
		if(destination.getPiece() != null) {
			destinationPiece = destination.getPiece();
			destinationPiece.setIsCaptured(true);
		}
		destination.setPiece(piece);
		
		for(Node node : Game.pieceGroup.getChildren()) {
			if(node instanceof King && ((Piece) node).getSide() == piece.getSide()) {
				king = (King) node;
				if(piece instanceof King)
					kingTile = destination;
				else
					kingTile = Tile.getTiles()[king.getXPos() - 1][king.getYPos() - 1];
				break;
			}
		}

		for(Node node : Game.pieceGroup.getChildren()) {
			Piece p = (Piece) node;
			if (p != null) {
				if(p.getAllowedMoves().contains(kingTile)
						&& p.getSide() != piece.getSide()
						&& !p.isCaptured) {
					isCheck = true;
					((King) king).setInCheck(true);
					System.out.println("checkk: " + kingTile.getTileCode());
					break;
				}
			}
		}
		if(!isCheck)
			((King) king).setInCheck(false);
		pieceTile.setPiece(piece);
		if(destinationPiece != null)
			destinationPiece.setIsCaptured(false);
		destination.setPiece(destinationPiece);
		
		isCheck();

		return isCheck;
	}

}
