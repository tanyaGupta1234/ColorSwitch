import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static javafx.application.Application.launch;


public class Game1 extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();




    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();
    private  Obstacle obstacle1;
    private  Obstacle obstacle2;
    private  Obstacle obstacle3;
    private boolean ENTER_pressed=false;

    private Circle ballImage;

    private void initContent() throws FileNotFoundException {
        Rectangle bg = new Rectangle(600, 600);
        ballImage=new Circle();
        ballImage.setRadius(25.0f);
        ballImage.setTranslateY(500.0f);
        ballImage.setTranslateX(300.0f);

        ballImage.setFill(Color.YELLOW);
        gameRoot.getChildren().add(ballImage);

        //TODO: for level add add all the game components
        obstacle1=new Obstacle(200);
        obstacle2=new Obstacle(-200);
        obstacle3=new Obstacle(-400);

        // Creating obstacle1 and setting its position
        ImageView iv = new ImageView();
        obstacle1.setImage("Images/obstacle1.png");
        iv.setImage(obstacle1.getImage());
        iv.setX(50);
        iv.setY(200);
        gameRoot.getChildren().add(iv);

        ImageView iv2 = new ImageView();
        obstacle2.setImage("Images/obstacle1.png");
        iv2.setImage(obstacle2.getImage());
        iv2.setX(50);
        iv2.setY(-200);
        gameRoot.getChildren().add(iv2);

        ImageView iv3 = new ImageView();
        obstacle3.setImage("Images/obstacle1.png");
        iv3.setImage(obstacle2.getImage());
        iv3.setX(50);
        iv3.setY(-400);
        gameRoot.getChildren().add(iv3);

        // Obstacle Animation
        RotateTransition rt = new RotateTransition(Duration.millis(3000), iv);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();


        ballImage.translateYProperty().addListener((observable, oldValue, newValue) -> {
            int offset=newValue.intValue();
            System.out.println(offset);
            if(offset<150 && offset%150<=3){
                System.out.println(offset);
                gameRoot.setLayoutY(150-offset);
            }
        });

        appRoot.getChildren().addAll(bg, gameRoot, uiRoot);
    }

    private void update() {


            double speed=0;
            if(ENTER_pressed==true){
                speed=-150;                // want 150 pixels per second move up

            }
            else if(ballImage.getTranslateY()<600-25){
                speed=75;
            }
            ballImage.setTranslateY(ballImage.getTranslateY()+speed/60.0);
            //ballImage.setCenterY(ballImage.getTranslateY());

    }



    public void start(Stage primaryStage) throws Exception {
        initContent();

        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(key->{
                    KeyCode keyCode=key.getCode();
                    if(keyCode.equals(keyCode.ENTER)){
                        ENTER_pressed=true;
                    }
                }
        );

        scene.setOnKeyReleased(key->{
                    KeyCode keyCode=key.getCode();
                    if(keyCode.equals(keyCode.ENTER)){
                        ENTER_pressed=false;
                    }
                }
        );
        primaryStage.setTitle("Tutorial 14 Platformer");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Making ball move according to key input
                update();


            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
