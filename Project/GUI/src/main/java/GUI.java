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
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class GUI extends Application
{
    private final int WINDOW_X = 300;
    private final int WINDOW_Y = 200;
    private final int PLUGIN_LIST_X = 150;
    private final int PLUGIN_LIST_Y = 150;

    @Override
    public void start(Stage primaryStage)
    {
	//Set the window's title
	primaryStage.setTitle("Hello world!");

        GridPane root = new GridPane();
        
	//Creates the list of plugins found
	PluginSetup ps = new PluginSetup(root);
	//location
	ps.setXY(1,0);
	//size
	ps.setPrefWidthHeight(PLUGIN_LIST_X, PLUGIN_LIST_Y);
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
                        PluginLoader loader = new PluginLoader();
			try
			{
			    QuestionType question = loader.loadPlugin(pf.getLocations().get(index));
			    System.out.println(question.getName());
			}
			catch(ClassNotFoundException e)
			{
			    ps.showError(e.toString());
			}
		    }
		}
	    }
	});
	//add this button to the stack pane
	root.add(btn, 1, 1);
	
	//centers elements
	VBox vbox = new VBox();
	vbox.setPadding(new Insets(0, (WINDOW_X-PLUGIN_LIST_X)/2, 0, 0));
	root.add(vbox, 0, 0);


	//set the window size and root stack pane
	primaryStage.setScene(new Scene(root, WINDOW_X, WINDOW_Y));
	
	//Show the window
	primaryStage.show();
    }
    
}
