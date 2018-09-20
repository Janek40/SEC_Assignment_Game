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

    private ErrorGUI error = new ErrorGUI();

    @Override
    public void start(Stage primaryStage)
    {
	//Set the window's title
	primaryStage.setTitle("Hello world!");

        GridPane root = new GridPane();
        
	//Creates the list of Quizzes
	//Where to look
	List<String> places = new ArrayList<String>(1);
	    places.add(System.getProperty("user.dir") + "/QuizPlugins/");
	//What a quiz plugin should look like
	List<String> contains = new ArrayList<String>(2);
	    contains.add("Quiz");
	    contains.add(".class");
	PluginSetup ps = new PluginSetup(root, places, contains);
	//location
	ps.setXY(1,0);
	//size
	ps.setPrefWidthHeight(PLUGIN_LIST_X, PLUGIN_LIST_Y);
	//Find the plugins
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
		    //get the selected quiz
		    int index = list.getSelectionModel().getSelectedIndex();
		    //contains exact locations and filenames
		    PluginFinder pf = ps.getOriginalFinder();
		    //if the index isn't invalid and plugin finder is not null
		    if(index!=-1 && pf!=null)
		    {
		        System.out.println("Selected: " 
		            + pf.getLocations().get(index));
			//Load the quiz
                        PluginLoader<QuizPlugin> loader = new PluginLoader<QuizPlugin>();
			try
			{
			    QuizPlugin quiz = loader.loadPlugin(pf.getLocations().get(index));
			    //This is run on another thread
			    quiz.runQuiz();
			}
			catch(ClassNotFoundException e)
			{
			    error.showError(e.toString());
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
