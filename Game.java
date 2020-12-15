import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
import java.util.*;
import java.util.HashMap;
import java.util.Iterator;

import static javafx.application.Application.launch;

//NOTES
// Obstacle outer radius 90.5px
// Obstacle inner radius 63.5 px
// width = 27
// start height 149px
public class Game {

   
    Stage stage;
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private HashMap<String,Integer> colorCode=new HashMap<>();


    private double or=90.5;
    private double ir=63.5;
    private double thickness=or-ir;
    private double bigor=127.5;
    private double bigir=89;
    private double bigthickness=bigor-bigir;
    private Pane appRoot ;
    private Pane gameRoot ;
    private Pane uiRoot ;
    private double starPos=300-or+50;   //star height
    private double colorSwitcherPos;
    private long timeStart;
    private double ballRadius=15;
    ImageView ivStar;
    private boolean ENTER_pressed=false;
    ImageView iv;
    private Player ball;
    int star1;
    int colorSwitcherIndex;
    int obstacleIndex;
    private Label score;
    Button pause;
    boolean paused=false;
    Button saveState;
    ArrayList<Obstacle> obstacle=new ArrayList<>();
    ArrayList<Star> star=new ArrayList<>();
    ArrayList<ColorSwitcher> colorSwitcher=new ArrayList<>();
    AnimationTimer timer;
    public Game(Stage s)
    {
        this.stage=s;
        appRoot = new Pane();
        gameRoot = new Pane();
        uiRoot = new Pane();
        ball=new Player(800);   
        
        int yCor =200;
        for(int i=0;i<10;i++)
        {
        	obstacle.add(new Obstacle(yCor,"Images/obstacle11.png"));
        	star.add(new Star(yCor+120));
        	gameRoot.getChildren().add(obstacle.get(i).iv);
        	gameRoot.getChildren().add(star.get(i).ivStar);
        	
        	if(i%2==0)
        	{
        		colorSwitcher.add(new ColorSwitcher(yCor-75));
        		gameRoot.getChildren().add(colorSwitcher.get(i/2).colorsw);
        	}
        	yCor-=300;
        }
       colorSwitcherPos=colorSwitcher.get(0).getPositionY();
       gameRoot.getChildren().addAll(ball.ballImage);
       star1=0;
       colorSwitcherIndex=0;
       obstacleIndex=0;
    }
    
    public Game(Stage s, SaveData data)
    {
        this.stage=s;
        appRoot = new Pane();
        gameRoot = new Pane();
        uiRoot = new Pane();
        ball=new Player(data.ballpos);
        ball.currentColor=data.ballColor;
        ball.setColor();
        ball.score=data.score;
        star1=data.star1;
        colorSwitcherIndex=data.colorSwitcherIndex;
        obstacleIndex=data.obstacleIndex;
        int yCor =200;

        //TODO  : check why not adding
        obstacle.add(new Obstacle(yCor,"Images/obstacle11.png","Images/obstacle21.png"));
        gameRoot.getChildren().add(obstacle.get(0).iv);
        gameRoot.getChildren().add(obstacle.get(0).iv2);



        yCor-=300;
        for(int i=1;i<10;i++)
        {
        	obstacle.add(new Obstacle(yCor,"Images/obstacle11.png"));
        	star.add(new Star(yCor+120));
        	gameRoot.getChildren().add(obstacle.get(i).iv);
        	if(i>=star1)gameRoot.getChildren().add(star.get(i).ivStar);
        	
        	if(i%2==0)
        	{
        		colorSwitcher.add(new ColorSwitcher(yCor-75));
        		gameRoot.getChildren().add(colorSwitcher.get(i/2).colorsw);
        	}
        	yCor-=300;
        }

        colorSwitcherPos=colorSwitcher.get(colorSwitcherIndex).getPositionY();
        starPos=star.get(star1).starPos;
       
       gameRoot.getChildren().addAll(ball.ballImage);
       
    }
    private void initContent() throws FileNotFoundException {

        // Top menu in game
        
        HBox hbox = new HBox(); hbox.setSpacing(20);
        BackgroundFill background_fill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        hbox.setBackground(background);
        pause=new Button("Pause"); saveState=new Button("Save Game State");
        javafx.scene.control.Label ycor=new javafx.scene.control.Label("");ycor.setTextFill(Color.web("#0076a3"));
        ycor.setFont(new javafx.scene.text.Font("Arial", 15));

        saveState.setOnAction(e->App.bStartMenu());
        score=new Label("Score : "+ball.score);score.setTextFill(Color.web("#0076a3"));
        score.setFont(new Font("Arial", 15));
        hbox.getChildren().addAll(pause,saveState,score,ycor);
        hbox.setLayoutY(0);
        uiRoot.getChildren().add(hbox);


        Rectangle bg = new Rectangle(600, 900);
     
        timeStart = System.currentTimeMillis();

 

        
        colorCode.put("pink",4);
        colorCode.put("purple",1);
        colorCode.put("yellow",2);
        colorCode.put("blue",3);


        // Moving screen with ball (scrolling property)
        ball.ballImage.translateYProperty().addListener((observable, oldValue, newValue) -> {
            int offset=newValue.intValue();
            System.out.println(offset);
            if(offset<150 && offset%150<=3){
                System.out.println(offset);
                gameRoot.setLayoutY(150-offset);
            }
        });

        appRoot.getChildren().addAll(bg, gameRoot, uiRoot);


    }
    public void displayGameWindow() throws FileNotFoundException 
    {
        initContent();

        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(key->{
                    KeyCode keyCode=key.getCode();
                    if(keyCode.equals(keyCode.CONTROL)){
                        ENTER_pressed=true;
                    }
                }
        );

        scene.setOnKeyReleased(key->
        {
                    KeyCode keyCode=key.getCode();
                    if(keyCode.equals(keyCode.CONTROL))
                    {
                        ENTER_pressed=false;
                    }
        }
        );
        stage.setTitle("Color Switch!");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() 
        {
            @Override
            public void handle(long now) {
                // Making ball move according to key input
                update();


            }
        };
        this.timer=timer;
        pause.setOnAction(e->{paused =true;timer.stop();pauseWindow(timer);});
       // saveState.setOnAction(e->App.bStartMenu());
        saveState.setOnAction(e->savingGame());
        timer.start();
    }

    private void update() 
    {

            // Changing position of ball
            double speed=0;
            if(ENTER_pressed==true){
                speed=-150;                // want 150 pixels per second move up

            }
            else if(ball.ballImage.getTranslateY()<600-25)
            {
                speed=75;
            }
            ball.ballImage.setTranslateY(ball.ballImage.getTranslateY()+speed/60.0);
            checkStarTouched();
            checkColorSwitcherTouched();
            checkObstacleTouched();
    }
    
    
    
    void checkStarTouched()
    {
    	 if(ball.ballImage.getTranslateY()>=starPos && ball.ballImage.getTranslateY()<=starPos+50){
             gameRoot.getChildren().remove(star.get(star1).ivStar);
             star1++;
             ball.score++;
             score.setText("Score: "+ball.score);
             starPos=100000;
             if(star1<star.size()) starPos=star.get(star1).starPos;

         }
    }
    void checkColorSwitcherTouched()
    {
    	 if(ball.ballImage.getTranslateY()>=colorSwitcherPos && ball.ballImage.getTranslateY()<=colorSwitcherPos+50)
    	 {
    		 ball.changeColor();
             colorSwitcherIndex++;
             colorSwitcherPos=100000;
             if(colorSwitcherIndex<colorSwitcher.size()) colorSwitcherPos=colorSwitcher.get(colorSwitcherIndex).getPositionY();
         }
    }
    
    void checkObstacleTouched()
    {
    	long elapsedTime=System.currentTimeMillis()-timeStart;
        long r=elapsedTime/1000;    // number of quarter rotations completed
       
        int presentBottomColor=1+(int)(r%4);
        int presentTopColor=1+(presentBottomColor+1)%4;
        System.out.println("Present bottom col:"+presentBottomColor);
        double ballTopPosition=ball.ballImage.getTranslateY()-ballRadius;
        double ballBottomPosition=ball.ballImage.getTranslateY()+ballRadius;

        // if ball is in top part of obstacle

        if((ballBottomPosition>=obstacle.get(obstacleIndex).iv.getY() &&ballBottomPosition<=obstacle.get(obstacleIndex).iv.getY()+thickness) ||
                (ballTopPosition>=obstacle.get(obstacleIndex).iv.getY() &&ballTopPosition<=obstacle.get(obstacleIndex).iv.getY()+thickness))
        
        {
        	
          
        	if(presentTopColor!=ball.getColor())
        	{
        	
                System.out.println("obstacle touched");
                timer.stop();
                obstacleHitWindow(timer);
                
            }
            else{System.out.println("passing");}
            
            obstacleIndex++;
        	if(obstacleIndex==obstacle.size()) {obstacleIndex--; }
        }

        // if ball is in bottom part of obstacle

    if((ballBottomPosition>=obstacle.get(obstacleIndex).iv.getY()+2*or-thickness &&ballBottomPosition<=obstacle.get(obstacleIndex).iv.getY()+2*or) ||
            (ballTopPosition>=obstacle.get(obstacleIndex).iv.getY()+2*or-thickness &&ballTopPosition<=obstacle.get(obstacleIndex).iv.getY()+2*or))
    {
       
    	if(presentBottomColor!=ball.getColor())
        {
    		
            System.out.println("obstacle touched"+presentBottomColor+" "+"ball"+ball.getColor());
            timer.stop();
            obstacleHitWindow(timer);
        }
        else
        {
            System.out.println("passing");
        }
    }
    	
    }
    
    public void obstacleHitWindow(AnimationTimer timer)
    {
    	Stage window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("OOPS!!!");
        window.setMinWidth(250);
        Button b=new Button("Resume game");
        b.setOnAction(e->
        { 
        	 ball.score--;score.setText("Score: "+ball.score);
        	 ball.ballImage.setTranslateY(ball.ballImage.getTranslateY()-40);
        	timer.start(); 
        	window.close();
        });
        Label l=new Label();
        Button q=new Button("Quit");
        q.setOnAction(e->{ App.bStartMenu(); window.close();});
        VBox pauseLayout=new VBox(); 
        if(ball.score==0) { l.setText("You lost!!!");pauseLayout.getChildren().addAll(l,q);}
        else {l.setText("Resume or Quit!"); pauseLayout.getChildren().addAll(l,b,q);}
        window.setScene(new Scene(pauseLayout,200,200));
        window.show();
    	
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
   
    public void savingGame()
    {

        SaveData data = new SaveData();
        data.score = ball.score;
        //data.stars = (ArrayList<Star>)star.clone();
        //data.obstacles=(ArrayList<Obstacle>)obstacle.clone();
        //data.colorSwitchers=(ArrayList<ColorSwitcher>)colorSwitcher.clone();
        double pos=ball.ballImage.getTranslateY();
        data.ballpos=pos;
        data.ballColor=ball.currentColor;
        System.out.println("ball color:"+data.ballColor);
        data.star1=star1;
        data.colorSwitcherIndex=colorSwitcherIndex;
        data.obstacleIndex=obstacleIndex;
        
        try {
            ResourceManager.save(data, "2.save");

        }
        catch (Exception e) {
            System.out.println("Couldn't save: " + e.getMessage());
        }
        timer.stop();
        App.bStartMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





//import javafx.animation.Animation;
//import javafx.animation.AnimationTimer;
//import javafx.animation.Interpolator;
//import javafx.animation.RotateTransition;
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.SnapshotParameters;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.CornerRadii;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.text.Font;
//import javafx.scene.transform.Rotate;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//import java.awt.event.KeyEvent;
//import java.io.FileNotFoundException;
//
//import java.lang.reflect.Array;
//import java.util.*;
////public class Game extends Application {
///*
// * Game class, has the game GUI
// */
//public class Game
//{
//	Stage stage;
//	boolean ENTER_pressed=false;
//	boolean paused=false;
//	int Score=0;
//	public Game(Stage s)
//	{
//		this.stage=s;
//	}
//
//
//	public void displayGameWindow() throws FileNotFoundException
//	{
//
//		BorderPane root=new BorderPane();
//		HBox hbox = new HBox(); hbox.setSpacing(20);
//		BackgroundFill background_fill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
//		Background background = new Background(background_fill);
//		hbox.setBackground(background);
//		Button pause=new Button("Pause"); Button saveState=new Button("Save Game State");
//		Label ycor=new Label("");ycor.setTextFill(Color.web("#0076a3"));
//		ycor.setFont(new Font("Arial", 15));
//
//		saveState.setOnAction(e->App.bStartMenu());
//		Label score=new Label("Score : 0");score.setTextFill(Color.web("#0076a3"));
//		score.setFont(new Font("Arial", 15));
//		hbox.getChildren().addAll(pause,saveState,score,ycor);
//		root.setTop(hbox);
//        Scene mainScene=new Scene(root,600,600);
//        stage.setScene(mainScene);
//        Canvas canvas=new Canvas(600,600);
//        GraphicsContext context=canvas.getGraphicsContext2D();  // Performs all drawing operations on canvas
//
//        root.setCenter(canvas);
//        context.setFill(Color.BLACK);
//        context.fillRect(0,0,600,600);
//
//
//
//
//        mainScene.setOnKeyPressed(key->{
//                    KeyCode keyCode=key.getCode();
//                    if(keyCode.equals(keyCode.CONTROL)){
//                        ENTER_pressed=true;
//                    }
//                }
//        );
//
//
//        mainScene.setOnKeyReleased(key->{
//                    KeyCode keyCode=key.getCode();
//                    if(keyCode.equals(keyCode.CONTROL)){
//                        ENTER_pressed=false;
//                    }
//                }
//        );
//        Player ball=new Player(0);
//
//        // Setting ball Image in game
//        Circle ballImage=new Circle();
//        ballImage.setCenterX(300.0f);
//        ballImage.setCenterY(500.0f);
//        ballImage.setRadius(25.0f);
//        ballImage.setFill(Color.YELLOW);
//        root.getChildren().add(ballImage);
//
//        ball.setPosition(500.0f);   // setting Y position in Player class
//
//
//        // Making ball move according to key input
//
//        //TODO: to create arraylist of Obstacle and add obstacle1 to it
//
//        // Creating obstacle1 and setting its position
//        ImageView iv = new ImageView();
//        Obstacle obstacle1=new Obstacle(200);
//        obstacle1.setImage("Images/obstacle1.png");
//        iv.setImage(obstacle1.getImage());
//        iv.setX(44);
//        iv.setY(200);
//        root.getChildren().add(iv);
//
//        // Obstacle Animation
//        RotateTransition rt = new RotateTransition(Duration.millis(4000), iv);
//        rt.setByAngle(360);
//        rt.setCycleCount(Animation.INDEFINITE);
//        rt.setInterpolator(Interpolator.LINEAR);
//        rt.play();
//
//
//        //star
//
//        ImageView ivStar = new ImageView();
//        Star star1=new Star(320);
//        star1.setImage("Images/star.png");
//        ivStar.setImage(star1.getImage());
//        ivStar.setX(275);
//        ivStar.setY(320);ivStar.setFitHeight(50);
//        ivStar.setFitWidth(50);
//        root.getChildren().add(ivStar);
//        AnimationTimer gameloop=new AnimationTimer() {
//            @Override
//            public void handle(long now) {     // this method called every 1/60th of second
//                double speed=0;
//                if(ENTER_pressed==true){
//                    speed=-150;  // want 150 pixels per second move up
//
//
//                }
//                else if(ball.getPositionY()<600-25){
//                    speed=75;
//                   // System.out.println(ball.getPositionY());
//                   // if(star1.getPositionY()-ball.getPositionY()<10 && (star1.getPositionY()>ball.getPositionY()))
//                       if(Math.abs(ball.getPositionY()-star1.getPositionY())<10 && star1.getPositionY()<1000)
//                    	{
//
//                    	ball.score++;
//                    	root.getChildren().remove(ivStar);
//                    	//System.out.println(ball.getPositionY()+"here"+star1.getPositionY());
//                    	star1.setPosition(10000000);
//                    	}
//
//                }
//                ball.addToPosition(speed/60.0);
//                ballImage.setCenterY(ball.getPositionY());
//                ycor.setText(""+ball.getPositionY());
//                score.setText("Score: "+ball.score);
//            }
//        };
//
//
//        gameloop.start();
//
//
//
//        pause.setOnAction(e->{paused =true;gameloop.stop();pauseWindow(gameloop);});
//        stage.show();
//
//	}
//	public void pauseWindow(AnimationTimer gameloop)
//	{
//		Stage window=new Stage();
//		window.initModality(Modality.APPLICATION_MODAL);
//		window.setTitle("Pause");
//		window.setMinWidth(250);
//		Button b=new Button("Resume");
//		b.setOnAction(e->{ gameloop.start(); window.close();});
//		VBox pauseLayout=new VBox(); pauseLayout.getChildren().add(b);
//		window.setScene(new Scene(pauseLayout,100,100));
//		window.showAndWait();
//
//	}
//
//
//
//
//
//  /*  public static void main(String[] args) {
//        try{
//            launch(args);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            System.exit(0);
//        }
//
//    } */
//
//
//
//  /*  public void start(Stage mainStage) throws FileNotFoundException {
//
//        mainStage.setTitle("Color Switch Game");
//        Button pause=new Button("pause");
//        BorderPane root=new BorderPane();
//        Scene mainScene=new Scene(root);
//        mainStage.setScene(mainScene);
//        root.getChildren().add(pause);
//
//        Canvas canvas=new Canvas(600,600);
//       GraphicsContext context=canvas.getGraphicsContext2D();  // Performs all drawing operations on canvas
//
//        root.setCenter(canvas);
//        context.setFill(Color.BLACK);
//        context.fillRect(0,0,600,600);
//
//
//
//
//        mainScene.setOnKeyPressed(key->{
//                    KeyCode keyCode=key.getCode();
//                    if(keyCode.equals(keyCode.ENTER)){
//                        ENTER_pressed=true;
//                    }
//                }
//        );
//
//
//        mainScene.setOnKeyReleased(key->{
//                    KeyCode keyCode=key.getCode();
//                    if(keyCode.equals(keyCode.ENTER)){
//                        ENTER_pressed=false;
//                    }
//                }
//        );
//        Player ball=new Player(0);
//
//        // Setting ball Image in game
//        Circle ballImage=new Circle();
//        ballImage.setCenterX(300.0f);
//        ballImage.setCenterY(500.0f);
//        ballImage.setRadius(25.0f);
//        ballImage.setFill(Color.YELLOW);
//        root.getChildren().add(ballImage);
//
//        ball.setPosition(500.0f);   // setting Y position in Player class
//
//
//        // Making ball move according to key input
//        AnimationTimer gameloop=new AnimationTimer() {
//            @Override
//            public void handle(long now) {     // this method called every 1/60th of second
//                double speed=0;
//                if(ENTER_pressed==true){
//                    speed=-150;                // want 150 pixels per second move up
//
//                }
//                else if(ball.getPositionY()<600-25){
//                    speed=75;
//                }
//                ball.addToPosition(speed/60.0);
//                ballImage.setCenterY(ball.getPositionY());
//            }
//        };
//
//        gameloop.start();
//
//
//
//
//        //TODO: to create arraylist of Obstacle and add obstacle1 to it
//
//        // Creating obstacle1 and setting its position
//        ImageView iv = new ImageView();
//        Obstacle obstacle1=new Obstacle(200);
//        obstacle1.setImage("Images/obstacle1.png");
//        iv.setImage(obstacle1.getImage());
//        iv.setX(44);
//        iv.setY(200);
//        root.getChildren().add(iv);
//
//        // Obstacle Animation
//        RotateTransition rt = new RotateTransition(Duration.millis(3000), iv);
//        rt.setByAngle(360);
//        rt.setCycleCount(Animation.INDEFINITE);
//        rt.setInterpolator(Interpolator.LINEAR);
//        rt.play();
//
//
//
//        mainStage.show();
//    }*/
//
//}