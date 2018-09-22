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
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public abstract void runQuiz(Scene root, Stage primaryStage);

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
