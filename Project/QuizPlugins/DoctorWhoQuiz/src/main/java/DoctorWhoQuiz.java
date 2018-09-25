import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Map;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class DoctorWhoQuiz extends QuizPlugin
{
    private final int WINDOW_X = 500;
    private final int WINDOW_Y = 500;
    //private volatile Boolean key = false;
    private volatile SafeBoolean key = new SafeBoolean(false);

    public DoctorWhoQuiz()
    {
        super("DoctorWhoQuiz");
    }

    @Override
    public void runQuiz(Scene prevScene, Stage primaryStage)
    {
	System.out.println("Doctor who Quiz running");
	Thread t1 = new Thread(() ->
	{
	    GridPane newRoot = new GridPane();
	    Platform.runLater(() ->
	    {
	        primaryStage.setTitle("Doctor who quiz");
	        primaryStage.setScene(new Scene(newRoot, WINDOW_X, WINDOW_Y));
	        primaryStage.show();
	    });
	    
	    try
	    {
	        int myScore = 0;
		super.loadPlugins();
	        QuestionType mcp = super.get("MultiChoice");
		QuestionType sa = super.get("ShortAnswer");
                
		Question q1 = mcp.makeQuestion("What is my name?", new String[] { "Janek", "Tom" }, 0);
		Question q2 = mcp.makeQuestion("What is my middle name?", new String[] { "Bob", "Karl" }, 1);
		Question q3 = mcp.makeQuestion("What is my last name?", new String[] { "Joyce", "Scott" }, 0);
		Question q4 = mcp.makeQuestion("What is 2+2", new String[] { "4", "5", "6" }, 0);
                
		LinkedBlockingQueue<Integer> turn = new LinkedBlockingQueue<Integer>();

		try
		{
		    //time to complete, primary stage, list to keep track of turns, a key, the turn
		    Future<Integer> q1Ans = q1.invoke(0, primaryStage, turn, key, 1);
		    Future<Integer> q2Ans = q2.invoke(20, primaryStage, turn, key, 2);
		    Future<Integer> q3Ans = q3.invoke(30, primaryStage, turn, key, 3);
		    Future<Integer> q4Ans = q4.invoke(30, primaryStage, turn, key, 4);

		    myScore += q1Ans.get();
		    myScore += q2Ans.get();
		    myScore += q3Ans.get();
		    myScore += q4Ans.get();
		}
		catch(InterruptedException e)
		{
		    System.out.println("Interrupted! unable to add to your score");
		}
		displayResult("Results", "Doctor who quiz result", "You scored " + myScore + " out of a maximum " + 4 + " points!");
		System.out.println("Done");
		returnToMain(prevScene, primaryStage);
	    }
            catch(IOException e)
	    {
	        ErrorGUI err = new ErrorGUI();
		err.showError(e.getMessage());
		returnToMain(prevScene, primaryStage);
		return;
	    }
            catch(ClassNotFoundException e)
	    {
	        ErrorGUI err = new ErrorGUI();
		err.showError(e.getMessage());
		returnToMain(prevScene, primaryStage);
		return;
	    }
	    catch(ExecutionException e)
	    {
                ErrorGUI err = new ErrorGUI();
		err.showError(e.getMessage());
		returnToMain(prevScene, primaryStage);
		return;
	    }
	});
	t1.start();
    }

    private void returnToMain(Scene prevScene, Stage primaryStage)
    {
        Platform.runLater(() ->
        {
            primaryStage.setScene(prevScene);
        });
    }
}
