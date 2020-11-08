import javafx.scene.image.Image;

public class Player extends GameComponent {
    private double speed;


    public Player(){
        speed=0;
    }

    // Vector addition (velocity) in y direction
    public void addToSpeed(double v){
        this.speed+=v;
    }



}
