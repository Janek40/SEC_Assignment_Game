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
    public void runQuiz()
    {
        System.out.println("Quiz running");
	Thread t1 = new Thread(() ->
	{
	    try
	    {
	        Map<String, QuestionType> plugins = loadPlugins();
	        QuestionType mcp = plugins.get("MultiChoicePlugin");
		if(mcp==null)
		{
		}
		System.out.println(mcp.getName());
	    }
            catch(IOException e)
	    {
	        ErrorGUI err = new ErrorGUI();
		err.showError(e.toString());
		return;
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
