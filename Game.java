import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import java.lang.reflect.Array;
import java.util.*;
//public class Game extends Application {
/*
 * Game class, has the game GUI
 */
public class Game
{
	Stage stage;
	boolean ENTER_pressed=false;
	boolean paused=false;
	int Score=0;
	public Game(Stage s)
	{
		this.stage=s;
	}
	public void displayGameWindow() throws FileNotFoundException
	{
		
		BorderPane root=new BorderPane(); 
		HBox hbox = new HBox(); hbox.setSpacing(20); 
		BackgroundFill background_fill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY); 
		Background background = new Background(background_fill);
		hbox.setBackground(background); 
		Button pause=new Button("Pause"); Button saveState=new Button("Save Game State");
		Label ycor=new Label("");ycor.setTextFill(Color.web("#0076a3"));
		ycor.setFont(new Font("Arial", 15));
		
		saveState.setOnAction(e->App.bStartMenu());
		Label score=new Label("Score : 0");score.setTextFill(Color.web("#0076a3"));
		score.setFont(new Font("Arial", 15));
		hbox.getChildren().addAll(pause,saveState,score,ycor);
		root.setTop(hbox);
        Scene mainScene=new Scene(root,600,600);
        stage.setScene(mainScene);
        Canvas canvas=new Canvas(600,600);
        GraphicsContext context=canvas.getGraphicsContext2D();  // Performs all drawing operations on canvas

        root.setCenter(canvas);
        context.setFill(Color.BLACK);
        context.fillRect(0,0,600,600);




        mainScene.setOnKeyPressed(key->{
                    KeyCode keyCode=key.getCode();
                    if(keyCode.equals(keyCode.CONTROL)){
                        ENTER_pressed=true;
                    }
                }
        );
        

        mainScene.setOnKeyReleased(key->{
                    KeyCode keyCode=key.getCode();
                    if(keyCode.equals(keyCode.CONTROL)){
                        ENTER_pressed=false;
                    }
                }
        );    
        Player ball=new Player(0);

        // Setting ball Image in game
        Circle ballImage=new Circle();
        ballImage.setCenterX(300.0f);
        ballImage.setCenterY(500.0f);
        ballImage.setRadius(25.0f);
        ballImage.setFill(Color.YELLOW);
        root.getChildren().add(ballImage);

        ball.setPosition(500.0f);   // setting Y position in Player class


        // Making ball move according to key input
        
        //TODO: to create arraylist of Obstacle and add obstacle1 to it

        // Creating obstacle1 and setting its position
        ImageView iv = new ImageView();
        Obstacle obstacle1=new Obstacle(200);
        obstacle1.setImage("Images/obstacle1.png");
        iv.setImage(obstacle1.getImage());
        iv.setX(44);
        iv.setY(200);
        root.getChildren().add(iv);

        // Obstacle Animation
        RotateTransition rt = new RotateTransition(Duration.millis(4000), iv);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();  
        
       
        //star
        
        ImageView ivStar = new ImageView();
        Star star1=new Star(320);
        star1.setImage("Images/star.png");
        ivStar.setImage(star1.getImage());
        ivStar.setX(275);
        ivStar.setY(320);ivStar.setFitHeight(50);
        ivStar.setFitWidth(50);
        root.getChildren().add(ivStar);
        AnimationTimer gameloop=new AnimationTimer() {
            @Override
            public void handle(long now) {     // this method called every 1/60th of second
                double speed=0;
                if(ENTER_pressed==true){
                    speed=-150;  // want 150 pixels per second move up
                    

                }
                else if(ball.getPositionY()<600-25){
                    speed=75;
                    System.out.println(ball.getPositionY());
                   // if(star1.getPositionY()-ball.getPositionY()<10 && (star1.getPositionY()>ball.getPositionY()))
                       if(Math.abs(ball.getPositionY()-star1.getPositionY())<10 && star1.getPositionY()<1000)
                    	{
                    	  
                    	ball.score++;
                    	root.getChildren().remove(ivStar);
                    	System.out.println(ball.getPositionY()+"here"+star1.getPositionY());
                    	star1.setPosition(10000000);
                    	}
                    
                }
                ball.addToPosition(speed/60.0);
                ballImage.setCenterY(ball.getPositionY());
                ycor.setText(""+ball.getPositionY());
                score.setText("Score: "+ball.score);
            }
        };
        

        gameloop.start();

        
        
        pause.setOnAction(e->{paused =true;gameloop.stop();pauseWindow(gameloop);});
        stage.show();  

	}
	public void pauseWindow(AnimationTimer gameloop)
	{
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Pause");
		window.setMinWidth(250);
		Button b=new Button("Resume");
		b.setOnAction(e->{ gameloop.start(); window.close();});
		VBox pauseLayout=new VBox(); pauseLayout.getChildren().add(b);
		window.setScene(new Scene(pauseLayout,100,100));
		window.showAndWait();
		
	}
	
	
	
	

  /*  public static void main(String[] args) {
        try{
            launch(args);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            System.exit(0);
        }

    } */
    


  /*  public void start(Stage mainStage) throws FileNotFoundException {

        mainStage.setTitle("Color Switch Game");
        Button pause=new Button("pause");
        BorderPane root=new BorderPane();
        Scene mainScene=new Scene(root);
        mainStage.setScene(mainScene);
        root.getChildren().add(pause);

        Canvas canvas=new Canvas(600,600);
       GraphicsContext context=canvas.getGraphicsContext2D();  // Performs all drawing operations on canvas

        root.setCenter(canvas);
        context.setFill(Color.BLACK);
        context.fillRect(0,0,600,600);




        mainScene.setOnKeyPressed(key->{
                    KeyCode keyCode=key.getCode();
                    if(keyCode.equals(keyCode.ENTER)){
                        ENTER_pressed=true;
                    }
                }
        );
        

        mainScene.setOnKeyReleased(key->{
                    KeyCode keyCode=key.getCode();
                    if(keyCode.equals(keyCode.ENTER)){
                        ENTER_pressed=false;
                    }
                }
        );
        Player ball=new Player(0);

        // Setting ball Image in game
        Circle ballImage=new Circle();
        ballImage.setCenterX(300.0f);
        ballImage.setCenterY(500.0f);
        ballImage.setRadius(25.0f);
        ballImage.setFill(Color.YELLOW);
        root.getChildren().add(ballImage);

        ball.setPosition(500.0f);   // setting Y position in Player class


        // Making ball move according to key input
        AnimationTimer gameloop=new AnimationTimer() {
            @Override
            public void handle(long now) {     // this method called every 1/60th of second
                double speed=0;
                if(ENTER_pressed==true){
                    speed=-150;                // want 150 pixels per second move up

                }
                else if(ball.getPositionY()<600-25){
                    speed=75;
                }
                ball.addToPosition(speed/60.0);
                ballImage.setCenterY(ball.getPositionY());
            }
        };

        gameloop.start();




        //TODO: to create arraylist of Obstacle and add obstacle1 to it

        // Creating obstacle1 and setting its position
        ImageView iv = new ImageView();
        Obstacle obstacle1=new Obstacle(200);
        obstacle1.setImage("Images/obstacle1.png");
        iv.setImage(obstacle1.getImage());
        iv.setX(44);
        iv.setY(200);
        root.getChildren().add(iv);

        // Obstacle Animation
        RotateTransition rt = new RotateTransition(Duration.millis(3000), iv);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();



        mainStage.show();
    }*/

}