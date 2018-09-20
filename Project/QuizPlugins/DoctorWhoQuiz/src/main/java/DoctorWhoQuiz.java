import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Map;

public class DoctorWhoQuiz extends QuizPlugin
{

    public DoctorWhoQuiz()
    {
        super("DoctorWhoQuiz");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void runQuiz()
    {
        System.out.println("Quiz running");
	Thread t1 = new Thread(() ->
	{
	    List<String> places = new ArrayList<String>();
	        places.add(System.getProperty("user.dir") + "/QuestionTypes/");
	    List<String> contains = new ArrayList<String>();
	        contains.add("Plugin");
		contains.add(".class");
            PluginFinder pf = new PluginFinder(places, contains);
            try
	    {
	        pf.find();
	    }
	    catch(IOException e)
	    {
	        ErrorGUI err = new ErrorGUI();
		err.showError(e.toString());
		return;
	    }

	    try
	    {
	        PluginLoader loader = new PluginLoader();
		Map<String, QuestionType> types = loader.loadPlugins(pf.getLocations());

		QuestionType mcp = types.get("MultiChoicePlugin");
		if(mcp==null)
		{
		    throw new ClassNotFoundException("Could not load MultiChociePlugin");
		}
		System.out.println("HERE: " + mcp.getName());
            }
	    catch(ClassNotFoundException e)
	    {
	        ErrorGUI err = new ErrorGUI();
		err.showError(e.toString());
		return;
	    }
	});
	t1.start();
    }
}
