/***************************************************************************
* Author: Janek Joyce
* Last Updated: 24/10/2018
* Purpose: To Allow questions to load plugins, show errors, and move back to the main menu easily
*          This code is intended for the 2018 semester 1 SEC assignment
***************************************************************************/


import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Map;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import java.util.HashMap;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.application.Platform;


public abstract class QuizPlugin
{
    private String name;
    private Map<String, QuestionType> types = new HashMap<String, QuestionType>();
    
    protected volatile Object key = new Object();
    protected volatile boolean stop = false;

    public QuizPlugin(String name)
    {
	GameLogic.turn.clear();
	GameLogic.endGame.clear();
	this.name = name;
    }
    
	//Get the name of the plugin, eg DoctorWhoQuiz
    public String getName()
    {
        return this.name;
    }
    
	//shows any errors
    protected void showError(Exception e)
    {
        ErrorGUI err = new ErrorGUI();
	err.showError(e.getMessage());
    }
	
	//Takes the user back to the main menu
    protected void returnToMain(Scene s, Stage pri)
    {
        Platform.runLater(() ->
	{
	    pri.setScene(s);
	});
    }
	
	//Sets the scene to the given values
    protected GridPane setQuizScene(String title, Stage primaryStage, int winX, int winY)
    {
        GridPane newRoot = new GridPane();
	Platform.runLater(() ->
	{
	    primaryStage.hide();
	    primaryStage.setTitle(title);
	    primaryStage.setScene(new Scene(newRoot, winX, winY));
	    primaryStage.show();
	});
	return newRoot;
    }
    
	//Method needs to be implemented by the quiz
    public abstract void runQuiz(Scene root, Stage primaryStage);
    
	//Loads ALL plugins matching the correct criteria in the plugins folder
    @SuppressWarnings("unchecked")
    protected void loadPlugins() throws IOException, ClassNotFoundException
    {
	List<String> places = new ArrayList<String>();
	places.add(System.getProperty("user.dir") + "/plugins/QuestionTypes/");
	    List<String> contains = new ArrayList<String>();
	    contains.add(".jar");
        PluginFinder pf = new PluginFinder(places, contains);
	pf.find();
        
	PluginLoader<QuestionType> loader = new PluginLoader<QuestionType>();
        List<String> locations = pf.getLocations();
	List<String> names = pf.removeExtension(4);
	for(int i=0;i<locations.size();i++)
	{
	    try
	    {
	        QuestionType question = loader.loadPlugin(locations.get(i), names.get(i), QuestionType.class);
	        if(question!=null)
	        {
	            types.put(question.getName(), question);
	        }
	    }
	    catch(ClassNotFoundException e)
	    {
	        System.out.println("Unable to load a class");
	    }
	}
    }
    
	//Loads a SINGLE plugin from the given directory
    @SuppressWarnings("unchecked")
    protected QuestionType loadPlugin(String pluginName) throws IOException, ClassNotFoundException
    {
        QuestionType loadedPlugin = null;

	List<String> places = new ArrayList<String>();
	places.add(System.getProperty("user.dir") + "/plugins/QuestionTypes/");
	    List<String> contains = new ArrayList<String>();
	    contains.add(".jar");
	    contains.add(pluginName);
        PluginFinder pf = new PluginFinder(places, contains);
	pf.find();
        
	PluginLoader<QuestionType> loader = new PluginLoader<QuestionType>();
        List<String> locations = pf.getLocations();
	List<String> names = pf.removeExtension(4);

	//there may be multiple plugins with a similar part of the name
	int pluginIndex = 0;
	if(names.size()>1)
	{
	    for(int i=0;i<names.size();i++)
	    {
	        if(pluginName.equals(names.get(i)))
		{
		    pluginIndex = i;
		}
	    }
	}
	try
	{
 	    loadedPlugin = loader.loadPlugin(locations.get(pluginIndex), names.get(pluginIndex), QuestionType.class);
	    if(loadedPlugin==null)
	    {
	        throw new ClassNotFoundException("Unable to load one of your plugins");
	    }
	}
	catch(ClassNotFoundException e)
	{
	    throw new ClassNotFoundException("Unable to load one of your plugins");
	}
	return loadedPlugin;
    }
    
	//Displays the end of quiz result to the user
    protected void displayResult(String title, String header, String message)
    {
        Platform.runLater(() ->
	{
	    Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
	    alert.setTitle(title);
	    alert.setHeaderText(header);
	    alert.showAndWait();
	});
    }

    //get a certain quiz plugin from the list, after loadPlugins() was called
    protected QuestionType get(String key) throws ClassNotFoundException
    {
        QuestionType question = types.get(key);
	if(question==null)
	{
	    throw new ClassNotFoundException(key + " was not found!");
	}
	return question;
    }
}
