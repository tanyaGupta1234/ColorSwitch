import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static javafx.application.Application.launch;




//NOTES
// Obstacle outer radius 90.5px
// Obstacle inner radius 63.5 px
// width = 27
// start height 149px
public class Game1 {

    public Game1(Stage s)
    {
        this.stage=s;
    }
    Stage stage;
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private HashMap<String,Integer> colorCode=new HashMap<>();


    private double or=90.5;
    private double ir=63.5;
    private double thickness=or-ir;
    private double xball=300;
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();
    private double starPos=300-or+50;   //star height
    private int ballColor=3;
    private int obcolor=1;
    private long timeStart;
    private double ballRadius=15;
    ImageView ivStar;
    //TODO: List of obstacles
    private  Obstacle obstacle1;
    private  Obstacle obstacle2;
    private  Obstacle obstacle3;
    private boolean ENTER_pressed=false;
    ImageView iv;
    private Circle ballImage;
    private Player ball;
    private Label score;
    Button pause;
    boolean paused=false;
    Button saveState;
    private void initContent() throws FileNotFoundException {

        // Top menu in game
        ball=new Player(900);   //TODO: initialize correct ball position
        HBox hbox = new HBox(); hbox.setSpacing(20);
        BackgroundFill background_fill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        hbox.setBackground(background);
        pause=new Button("Pause"); saveState=new Button("Save Game State");
        javafx.scene.control.Label ycor=new javafx.scene.control.Label("");ycor.setTextFill(Color.web("#0076a3"));
        ycor.setFont(new javafx.scene.text.Font("Arial", 15));

        saveState.setOnAction(e->App.bStartMenu());
        score=new Label("Score : 0");score.setTextFill(Color.web("#0076a3"));
        score.setFont(new Font("Arial", 15));
        hbox.getChildren().addAll(pause,saveState,score,ycor);
        hbox.setLayoutY(0);
        uiRoot.getChildren().add(hbox);


        Rectangle bg = new Rectangle(600, 900);
        ballImage=new Circle();
        ballImage.setRadius(ballRadius);
        ballImage.setTranslateY(800.0f);
        ballImage.setTranslateX(xball);

        ballImage.setFill(Color.YELLOW);
        gameRoot.getChildren().add(ballImage);

        //star

        ivStar = new ImageView();
        Star star1=new Star(320);
        star1.setImage("Images/star.jpg");
        ivStar.setImage(star1.getImage());
        ivStar.setX(275);
        ivStar.setFitHeight(50);
        ivStar.setY(starPos);
        ivStar.setFitWidth(50);
        gameRoot.getChildren().add(ivStar);
        //TODO: for level add add all the game components
        obstacle1=new Obstacle(300);
        obstacle2=new Obstacle(-200);
        obstacle3=new Obstacle(-400);

        // Creating obstacle1 and setting its position
        iv = new ImageView();
        obstacle1.setImage("file:Images/obstacle11.png");
        iv.setImage(obstacle1.getImage());
        iv.setX(xball-or);
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
        RotateTransition rt = new RotateTransition(Duration.millis(4000), iv);  // in 4s 1 rotation done
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
        timeStart = System.currentTimeMillis();

        RotateTransition rt1 = new RotateTransition(Duration.millis(3000), iv2);
        rt1.setByAngle(360);
        rt1.setCycleCount(Animation.INDEFINITE);
        rt1.setInterpolator(Interpolator.LINEAR);
        rt1.play();

        // Assigning color code  (not needed for now)
        colorCode.put("purple",4);
        colorCode.put("blue",2);
        colorCode.put("yellow",3);
        colorCode.put("pink",1);


        // Moving screen with ball (scrolling property)
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

            // Changing position of ball
            double speed=0;
            if(ENTER_pressed==true){
                speed=-150;                // want 150 pixels per second move up

            }
            else if(ballImage.getTranslateY()<600-25){
                speed=75;
            }
            if(ballImage.getTranslateY()>=starPos && ballImage.getTranslateY()<=starPos+50){
                gameRoot.getChildren().remove(ivStar);
                ball.score++;
                score.setText("Score: "+ball.score);
                starPos=100000;

            }
            ballImage.setTranslateY(ballImage.getTranslateY()+speed/60.0);
            //ballImage.setCenterY(ballImage.getTranslateY());

            // Passing of ball through obstacles
            long elapsedTime=System.currentTimeMillis()-timeStart;
            long r=elapsedTime/1000;    // number of quarter rotations completed
            int presentBottomColor=1+(int)(r%4);
            int presentTopColor=1+(presentBottomColor+1)%4;
            double ballTopPosition=ballImage.getTranslateY()-ballRadius;
            double ballBottomPosition=ballImage.getTranslateY()+ballRadius;

            // if ball is in top part of obstacle

            if((ballBottomPosition>=iv.getY() &&ballBottomPosition<=iv.getY()+thickness) ||
                    (ballTopPosition>=iv.getY() &&ballTopPosition<=iv.getY()+thickness)){
                if(presentTopColor!=ballColor){
                    System.out.println("obstacle touched");
                }
                else{
                    System.out.println("passing");
                }
            }

            // if ball is in bottom part of obstacle

        if((ballBottomPosition>=iv.getY()+2*or-thickness &&ballBottomPosition<=iv.getY()+2*or) ||
                (ballTopPosition>=iv.getY()+2*or-thickness &&ballTopPosition<=iv.getY()+2*or)){
            if(presentBottomColor!=ballColor){
                System.out.println("obstacle touched");
            }
            else{
                System.out.println("passing");
            }
        }



    }


    public void pauseWindow(AnimationTimer gameloop)
    {
        Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Pause");
        window.setMinWidth(250);
        javafx.scene.control.Button b=new Button("Resume");
        b.setOnAction(e->{ gameloop.start(); window.close();});
        VBox pauseLayout=new VBox(); pauseLayout.getChildren().add(b);
        window.setScene(new Scene(pauseLayout,100,100));
        window.showAndWait();

    }



    public void displayGameWindow() throws FileNotFoundException {
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
        stage.setTitle("Color Switch!");
        stage.setScene(scene);

        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Making ball move according to key input
                update();


            }
        };
        pause.setOnAction(e->{paused =true;timer.stop();pauseWindow(timer);});
        saveState.setOnAction(e->App.bStartMenu());
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
