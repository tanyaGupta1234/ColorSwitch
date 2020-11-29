import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
//import com.sun.javafx.jmx.MXNodeAlgorithm;
//import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public  class GameComponent implements Serializable {


    private double positionY;

    public GameComponent(double y){
        positionY=y;
    }
    public void setPosition(double y){
        positionY=y;
    }





    public void addToPosition(double v){
        this.positionY+=v;
    }
    public double getPositionY(){
        return positionY;
    }


}
