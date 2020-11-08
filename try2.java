
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class try2 extends Application{
	 Button newGame,existingGame,exit;
	 Scene startMenu,startNewGame,savedGames;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("Color Switch!");
        newGame = new Button("New Game"); existingGame=new Button("Resume Existing Game"); exit=new Button("Exit");
        Button goBack=new Button("go back");  
        Button pauseB=new Button("pause");
        Label myHeading=new Label("Color Switch"); Label newG=new Label("NEW GAME!!!");Label savedG=new Label("SAVED Games!!!");
        VBox startMenuLayout = new VBox(20);  VBox newGameLayout = new VBox(20);VBox savedGameLayout = new VBox(20);
        startMenuLayout.getChildren().addAll(myHeading,newGame,existingGame,exit);startMenuLayout.setAlignment(Pos.CENTER);
        newGameLayout.getChildren().addAll(newG,pauseB);savedGameLayout.getChildren().addAll(savedG,goBack);
        startMenu=new Scene(startMenuLayout,300,250);
        startNewGame=new Scene(newGameLayout,300,250);
        savedGames= new Scene(savedGameLayout,300,250);
        primaryStage.setScene(startMenu);
        newGame.setOnAction(e->
    	{
    		primaryStage.setScene(startNewGame);
    	}
    );
    existingGame.setOnAction(e->
    {
    	primaryStage.setScene(savedGames);
    }); 
    goBack.setOnAction(e->
    {
    	primaryStage.setScene(startMenu);
    });
    pauseB.setOnAction(e->pause.display());
        primaryStage.show();
        
    }
   
   
}