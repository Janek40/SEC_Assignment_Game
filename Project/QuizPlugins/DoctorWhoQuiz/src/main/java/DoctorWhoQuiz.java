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
        System.out.println("Doctor who Quiz running");
	Thread t1 = new Thread(() ->
	{
	    try
	    {
	        super.loadPlugins();
	        QuestionType mcp = super.get("MultiChoicePlugin");
		QuestionType sa = super.get("ShortAnswerPlugin");
		System.out.println(mcp.getName());
		System.out.println(sa.getName());
		System.out.println("Done");
	    }
            catch(IOException e)
	    {
	        ErrorGUI err = new ErrorGUI();
		err.showError(e.getMessage());
		return;
	    }
            catch(ClassNotFoundException e)
	    {
	        ErrorGUI err = new ErrorGUI();
		err.showError(e.getMessage());
		return;
	    }
	});
	t1.start();
    }
}
