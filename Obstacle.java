import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Obstacle  extends GameComponent
{
	ImageView iv,iv2;
	
	private double xball=300;
	private double or=90.5;
    public Obstacle(double y,String imgSource ) 
    
    {
    	super(y);
    	iv = new ImageView();
        try
        {
        	setImage(imgSource);
        }
        catch(Exception e) {System.out.println("obstacle image not set");}
        iv.setImage(getImage());
        iv.setX(xball-or);
        iv.setY(y);
        
        RotateTransition rt = new RotateTransition(Duration.millis(4000), iv);  // in 4s 1 rotation done
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
        
    }
public Obstacle(double y,String imgSource, int x ) 
   
    {
    	super(y);
    	iv = new ImageView();
        try
        {
        	setImage(imgSource);
        }
        catch(Exception e) {System.out.println("obstacle image not set");}
        iv.setImage(getImage());
        iv.setX(xball-or-70);
        iv.setY(y);
        
        RotateTransition rt = new RotateTransition(Duration.millis(4000), iv);  // in 4s 1 rotation done
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
        
    }
    public Obstacle(double y,String imgSource1,String imgSource2 )

    {

        super(y);
        double bigor=127.5;
        iv = new ImageView();
        iv2=new ImageView();
        Image image2=new Image(imgSource2);
        try
        {
            setImage(imgSource1);
        }
        catch(Exception e) {System.out.println("obstacle image not set");}
        iv.setImage(getImage());
        iv.setX(xball-or);
        iv.setY(y);

        iv2.setImage(image2);
        iv2.setX(xball-bigor);
        iv2.setY(y-50);

        RotateTransition rt = new RotateTransition(Duration.millis(4000), iv);  // in 4s 1 rotation done
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();

        RotateTransition rt1 = new RotateTransition(Duration.millis(4000), iv2);  // in 4s 1 rotation done
        rt1.setByAngle(-360);
        rt1.setCycleCount(Animation.INDEFINITE);
        rt1.setInterpolator(Interpolator.LINEAR);
        rt1.play();
    }
}

