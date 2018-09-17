package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.Board;
import main.Color;
import main.Game;
import main.Player;

public class Main extends Application {
	Board board;
	Player p1;
	Player p2;
	
	void startGame(Stage stage, Board board) {
		BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 512, 430);
        stage.setTitle("ChessFantasy");
        stage.setScene(scene);
        scene.getStylesheets().add(
        		getClass().getResource("css/style.css").toExternalForm()
        );
        
        this.board = board;
        root.setCenter(board);
        
        MenuBar menuBar = new MenuBar();
        
        Menu file = new Menu("File");
        MenuItem newGame = new MenuItem("New Game");
        MenuItem close = new MenuItem("Close");
        newGame.setOnAction(e -> {
			try {
				restartCallback(stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
        close.setOnAction(e -> closeCallback());
        
        file.getItems().addAll(newGame, close);
        menuBar.getMenus().addAll(file);
        
        root.setTop(menuBar);
        
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(6));
        vbox.setSpacing(5);
        
        Text turn = new Text("Player: WHITE");
        
        Text title = new Text("Game status");
        title.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);
        vbox.getChildren().add(turn);
        root.setLeft(vbox);
        board.setTurn(turn);
        
        stage.show();
	}
	
	public void closeCallback() {
		System.exit(0);
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.p1 = new Player(Color.WHITE);
		this.p2 = new Player(Color.BLACK);
		Board newBoard = new Board(this.p1, this.p2, this);
        this.startGame(stage, newBoard);
    }
	
	public void restartCallback(Stage stage) throws Exception {
		stage.close();

		this.p1 = new Player(Color.WHITE);
		this.p2 = new Player(Color.BLACK);
		// choose whether to keep the same players or create new ones
		this.startGame(new Stage(), new Board(this.p1, this.p2, this));
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}
