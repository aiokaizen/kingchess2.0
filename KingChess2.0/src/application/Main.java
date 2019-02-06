package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Board;
import model.Game;

public class Main extends Application {
	
	public static Stage primaryStage;
	
	public static void main(String[] args) {
		
//		String answer = JOptionPane.showInputDialog("say hi!");
//		JOptionPane.showMessageDialog(null, answer);
		launch(args);
	}

	@Override
	public void start(Stage arg) throws Exception {
		primaryStage = new Stage();
		Scene scene = new Scene(Board.createContent());
		scene.getStylesheets().add("assets/css/style.css");
		primaryStage.getIcons().add(new Image("assets/img/white_knight.png"));
		primaryStage.setScene(scene);
		primaryStage.setTitle("King Chess");
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println("key " + event.getText() + " pressed");
				if(event.getCode() == KeyCode.R) {
					Game.initGame();
				}
				else if(event.getCode() == KeyCode.RIGHT) {
					System.out.println("Right pressed");
				}
				else if(event.getCode() == KeyCode.R) {
					System.out.println("R pressed");
				}
			}
		});
		
//		MusicPlayer player = new MusicPlayer(true, "Soundtrack2", "Soundtrack3", "Soundtrack1"); //"catch_01", "move_01", "move_02",  
//		ThreadPool pool = new ThreadPool(1);
//		pool.runTask(player);
//		pool.join();
		primaryStage.show();
	}
}
