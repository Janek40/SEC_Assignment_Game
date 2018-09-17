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
	Thread t1 = new Thread(() ->
	{
	       try
	       {
 	           //Use this line to show the loading text...
		   try{Thread.sleep(5000);}catch(InterruptedException e){}

		   ps.updatePluginsList();
	       }
	       catch(IOException e)
	       {
	           System.out.println(e);
		   return;
	       }
	});
	t1.start();

	//set up a button
	Button btn = new Button();
	btn.setText("Continue");
	btn.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent event)
	    {
	        int index = ps.getList().getSelectionModel().getSelectedIndex();
		if(index!=-1)
		{
		    try
		    {
		        System.out.println("Selected: " 
		            + ps.getOriginalFinder().getLocations().get(index));
		    }
		    catch(IOException e)
		    {
		        System.out.println(e);
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
