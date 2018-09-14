import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class GUI extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        //Set the window's title
	primaryStage.setTitle("Hello world!");
	
	//add a stack pane
	StackPane root = new StackPane();

        
	ListView<String> list = new ListView<String>();
	ObservableList<String> items = FXCollections.observableArrayList (
	    "Single", "Double", "Suite", "Family App");
	list.setItems(items);
	root.getChildren().add(list);


	//set up a button
	Button btn = new Button();
	btn.setText("Say herro there");
	btn.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent event)
	    {
		System.out.println(list.getSelectionModel().getSelectedIndex());
	    }
	});
	//add this button to the stack pane
	root.getChildren().add(btn);



	//set the window size and root stack pane
	primaryStage.setScene(new Scene(root, 300, 250));
	
	//Show the window
	primaryStage.show();
    }
}
