
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class pause 
{
	public static void display()
	{
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Pause");
		window.setMinWidth(250);
		Button b=new Button("Resume");
		b.setOnAction(e->window.close());
		VBox pauseLayout=new VBox(); pauseLayout.getChildren().add(b);
		window.setScene(new Scene(pauseLayout,100,100));
		window.showAndWait();
		
	}

}
