import javafx.event.ActionEvent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;
/*
 * This class is the App class and has all the main menu display methods, list of saved games
 */
public class App
{
	static Stage stage;
	List<Game> savedGames=new ArrayList<>();
	static ChoiceBox<String> choicebox=new ChoiceBox<>();
	public App(Stage s)
	{
		stage=s;
	}
	void addSavedGame(Game g)
	{
		savedGames.add(g);
		choicebox.getItems().add("Game: "+(savedGames.size()+1));
	}
public static void  bNewGame() 
{
	VBox newGameLayout = new VBox(20);
	Label newG=new Label("NEW GAME!!!");
	newGameLayout.getChildren().addAll(newG);
	Scene startNewGame=new Scene(newGameLayout,300,250);
	stage.setScene(startNewGame);
	Game g=new Game(stage);
	try {
		
	g.displayGameWindow();
		} 
		catch(Exception e) {System.out.println("exceptionnnnnn");}
	
}
public static void bSavedGames() 
{
	 Button goBack=new Button("go back"); 
	 Label savedG=new Label("SAVED Games!!!");
	 //dummy entries
	 choicebox.getItems().add("Game 1"); choicebox.getItems().add("Game 2");
	 VBox savedGameLayout = new VBox(20); savedGameLayout.setAlignment(Pos.CENTER);
	 savedGameLayout.getChildren().addAll(savedG,choicebox,goBack);
	 Scene savedGames= new Scene(savedGameLayout,600,600);
	 stage.setScene(savedGames);
	 goBack.setOnAction(e->{bStartMenu();});
}
public static void bStartMenu()
{
	//Parent startMenuLayout =FXMLLoader.load(getClass().getResource("testfxml.fxml")); 
	stage.setTitle("Color Switch!");
	Button newGame = new Button("New Game");
	Button existingGame=new Button("Resume Existing Game");
	Button exit=new Button("Exit");
	Label myHeading=new Label("Color Switch"); 
	VBox startMenuLayout = new VBox(20); 
	startMenuLayout.getChildren().addAll(myHeading,newGame,existingGame,exit);startMenuLayout.setAlignment(Pos.CENTER);
    Scene startMenu=new Scene(startMenuLayout,600,600);
    stage.setScene(startMenu);
    newGame.setOnAction(e->{bNewGame();});
    existingGame.setOnAction(e->{bSavedGames();}); 
    exit.setOnAction(e->{stage.close();}); 
}



}

