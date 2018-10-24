/***************************************************************************
* Author: Janek Joyce
* Last Updated: 24/10/2018
* Purpose: To serve as an example quiz
*          This code is intended for the 2018 semester 1 SEC assignment
***************************************************************************/

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
	    GridPane newRoot = super.setQuizScene("Doctor who quiz", primaryStage, WINDOW_X, WINDOW_Y);
	    
	    try
	    {
	        int myScore = 0;
                
		QuestionType mcp = super.loadPlugin("MultiChoice");
		QuestionType sa = super.loadPlugin("ShortAnswer");
                
		Question q0 = sa.makeQuestion("What is?", "poop");
		Question q1 = mcp.makeQuestion("What is my name?", new String[] { "Janek", "Tom" }, 0);
		Question q2 = mcp.makeQuestion("What is my middle name?", new String[] { "Bob", "Karl" }, 1);
		Question q3 = mcp.makeQuestion("What is my last name?", new String[] { "Joyce", "Scott" }, 0);
		Question q4 = mcp.makeQuestion("What is 2+2", new String[] { "4", "5", "6" }, 0);
                

		try
		{
                    //timeout time, and the stage to write to
		    Future<Integer> q0Ans = q0.invoke(0, primaryStage, 0);
		    Future<Integer> q1Ans = q1.invoke(0, primaryStage, 1);
		    Future<Integer> q2Ans = q2.invoke(20, primaryStage, 2);
                    Future<Integer> q3Ans = q3.invoke(30, primaryStage, 3);
		    Future<Integer> q4Ans = q4.invoke(30, primaryStage, 4);

                    myScore += q0Ans.get();
		    myScore += q1Ans.get();
		    myScore += q2Ans.get();
		    myScore += q3Ans.get();
		    myScore += q4Ans.get();
		}
		catch(InterruptedException e)
		{
		    System.out.println("Interrupted! unable to add to your score");
		}
		//title, Header, message
		displayResult("Results", "Doctor who quiz result", "You scored " + myScore + " out of a maximum " + 4 + " points!");
	    }
	    
            catch(IOException e)
	    {
		showError(e);
	    }
            
	    catch(ClassNotFoundException e)
	    {
		showError(e);
	    }
	    catch(ExecutionException e)
	    {
		showError(e);
	    }
	    catch(Exception e)
	    {
                showError(e);
	    }
	    //return to the main screen
	    returnToMain(prevScene, primaryStage);
	});
	t1.start();
    }
}
