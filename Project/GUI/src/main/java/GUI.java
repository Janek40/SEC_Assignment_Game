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
import javafx.application.Platform;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class GUI extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
	//Set the window's title
	primaryStage.setTitle("Hello world!");
	
	//add a stack pane
	//StackPane root = new StackPane();
        GridPane root = new GridPane();

	PluginSetup ps = new PluginSetup(root);
	ps.setXY(0,0);
	ps.setPrefWidthHeight(130, 150);
	//This is run in another thread as it could be time consuming
	ps.updatePluginsList();

	//set up a button
	Button btn = new Button();
	btn.setText("Continue");
	btn.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent event)
	    {
	        ListView<String> list = ps.getList();
		if(list!=null)
		{
		    int index = list.getSelectionModel().getSelectedIndex();
		    PluginFinder pf = ps.getOriginalFinder();
		    if(index!=-1 && pf!=null)
		    {
		        System.out.println("Selected: " 
		            + pf.getLocations().get(index));
		    }
		}
	    }
	});
	//add this button to the stack pane
	root.add(btn, 0, 1);
	
	//set the window size and root stack pane
	primaryStage.setScene(new Scene(root, 300, 250));
	
	//Show the window
	primaryStage.show();
    }
    
}
