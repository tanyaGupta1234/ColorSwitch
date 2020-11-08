import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameComponent {

    private double positionY;
    private Image image;
    public GameComponent(){
        positionY=0;
    }
    public void setPosition(double y){
        positionY=y;
    }



    public void setImage(String filename) throws FileNotFoundException {
        FileInputStream inputstream = new FileInputStream(filename);
        image=new Image(inputstream);
    }

    public void render(GraphicsContext context,int x){
        context.drawImage(image,x,positionY);
    }

    public void addToPosition(double v){
        this.positionY+=v;
    }
    public double getPositionY(){
        return positionY;
    }
}
