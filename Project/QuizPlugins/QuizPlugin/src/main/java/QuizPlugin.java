import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Map;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import java.util.HashMap;


public abstract class QuizPlugin
{
    private String name;
    private Map<String, QuestionType> types = new HashMap<String, QuestionType>();

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
	places.add(System.getProperty("user.dir") + "/QuestionTypes/");
	    List<String> contains = new ArrayList<String>();
	    contains.add(".jar");
        PluginFinder pf = new PluginFinder(places, contains);
	pf.find();
        
	PluginLoader<QuestionType> loader = new PluginLoader<QuestionType>();
        List<String> locations = pf.getLocations();
	for(int i=0;i<locations.size();i++)
	{
	    QuestionType question = loader.loadPlugin(locations.get(i));
	    if(question!=null)
	    {
	        types.put(question.getName(), question);
	    }
	}
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
