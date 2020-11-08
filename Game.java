import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import java.lang.reflect.Array;
import java.util.*;
public class Game extends Application {

    public static void main(String[] args) {
        try{
            launch(args);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            System.exit(0);
        }

    }
    boolean ENTER_pressed=false;


    public void start(Stage mainStage) throws FileNotFoundException {

        mainStage.setTitle("Color Switch Game");

        BorderPane root=new BorderPane();
        Scene mainScene=new Scene(root);
        mainStage.setScene(mainScene);

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
        Player ball=new Player();

        // Setting ball Image in game
        Circle ballImage=new Circle();
        ballImage.setCenterX(300.0f);
        ballImage.setCenterY(500.0f);
        ballImage.setRadius(25.0f);
        ballImage.setFill(Color.RED);
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
        //ball.setImage("C:\\Users\\shell\\IdeaProjects\\colorSwitcj\\Images\\ball.png");
        //ball.render(context);
        mainStage.show();
    }

}
