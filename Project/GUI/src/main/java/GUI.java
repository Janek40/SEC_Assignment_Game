import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI extends Application
{
    public GUI(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Hello world!");
	Button btn = new Button();
	btn.setText("Say herro there");
	btn.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent event)
	    {
	        System.out.println("Hello world!");
	    }
	});
	
	StackPane root = new StackPane();
	root.getChildren().add(btn);
	primaryStage.setScene(new Scene(root, 300, 250));
	primaryStage.show();
    }
}
