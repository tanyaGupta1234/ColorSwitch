import java.util.ArrayList;

public class SaveData implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    public int score;
    ArrayList<Star> stars;
    ArrayList<Obstacle> obstacles;
    double ballpos;
}
