import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Map;


public abstract class QuizPlugin
{
    private String name;
    
    public QuizPlugin(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public abstract void runQuiz();

    @SuppressWarnings("unchecked")
    protected Map<String, QuestionType> loadPlugins() throws IOException, ClassNotFoundException
    {
        List<String> places = new ArrayList<String>();
	places.add(System.getProperty("user.dir") + "/QuestionTypes/");
	    List<String> contains = new ArrayList<String>();
	contains.add("Plugin");
	    contains.add(".class");
        PluginFinder pf = new PluginFinder(places, contains);
	pf.find();

	PluginLoader loader = new PluginLoader();
	Map<String, QuestionType> types = loader.loadPlugins(pf.getLocations());
	return types;
    }
}
