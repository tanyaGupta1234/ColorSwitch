import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameComponent {

    private double positionY;
    private Image image;
    public GameComponent(double y){
        positionY=y;
    }
    public void setPosition(double y){
        positionY=y;
    }



    public void setImage(String filename) throws FileNotFoundException {
        image=new Image(filename);

    }

    public Image getImage(){
        return image;
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
