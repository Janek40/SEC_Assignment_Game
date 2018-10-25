/***************************************************************************
* Author: Janek Joyce
* Last Updated: 25/10/2018
* Purpose: To show current question, any previews, and handle timeouts
*          This code is intended for the 2018 semester 1 SEC assignment
***************************************************************************/

import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;

import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class Question
{
    //This pane has been populated by the correct buttons etc already!
    protected GridPane regularRoot;
    protected GridPane previewRoot;
    private ExecutorService executor = Executors.newFixedThreadPool(1);

    public Question(GridPane regularRoot, GridPane previewRoot)
    {
        this.regularRoot = regularRoot;
	this.previewRoot = previewRoot;
    }

    public void showPreview(Stage primaryStage)
    {
        Platform.runLater(() ->
	{
	    Scene sc = primaryStage.getScene();
	    GridPane oldRoot = (GridPane)sc.getRoot();
	    oldRoot.add(previewRoot, 0, 8);
	});
    }
    
    //Function starts the questioning 
    //It takes in the time the user has before the question times out
    //the stage to write the buttons/information to
    public Future<Integer> invoke(int time, Stage primaryStage, int qNum)
    throws ClassNotFoundException, InterruptedException
    {
	GameLogic.turn.put(qNum);
       	
	return executor.submit(() ->
	{
	        //EXIT
		if(GameLogic.endGame.peek()!=null)
		{
		    executor.shutdown();
		    return 0;
		}
		
                //while it is not that question's turn
		while(GameLogic.turn.peek()!=qNum)
	        {
	            int k=0;
		    //We need to find the second item in the list
		    //This will need to show a preview
		    //if it exists or has been invoked that is!
	            for (Integer i : GameLogic.turn)
		    {
		        if(k==1)
		        {
		            //i'm the second one in the list!
			    if(i==qNum)
			    {
				showPreview(primaryStage);
			    }
			    break;
		        }
		        k++;
		    }
		    //Wait for a question to be finished
	            synchronized(GameLogic.key)
	            {
		        GameLogic.key.wait();
	            }
		    //exit!
		    //When the question removes all turns this means
		    //they pressed the exit button, or there are no more questions left
		    if(GameLogic.endGame.peek()!=null)
		    {
		        executor.shutdown();
			return 0;
		    }
	        }
	    return invokeFull(time, primaryStage, qNum);
	});
    }

    //When it is a certain question's turn we need to handle timeouts and the submit/next buttons
    private Integer invokeFull(int time, Stage primaryStage, int qNum)
    {
   	//Show the question
	Platform.runLater(() ->
	{
	    primaryStage.setScene(new Scene(regularRoot, 500, 500));
	    primaryStage.show();
	});
	
	//Let the preview know that we are ready
	synchronized(GameLogic.key)
	{
	    GameLogic.key.notifyAll();
	}

	//Start an executor service
	//one thread will be a timeout, the other will be waiting for the question result
	ExecutorService e 
	   = new ThreadPoolExecutor(2, 2, 5000, TimeUnit.MILLISECONDS, 
             new ArrayBlockingQueue<Runnable>(10));
         

	//List of jobs to start
	List<Callable<Result>> list = new ArrayList<>();
        //if we have a limited amount of time
	if(time>0)
	{
            list.add(() ->
	    {
	        Timeouts timeout = new Timeouts(time, regularRoot);
	        Integer fail = timeout.start();
		return new Result("Timeout", fail);
	    });
	}
	//wait for the user's result to appear
	list.add(() ->
	{
	    Integer userMark = GameLogic.score.take();
	    return new Result("User", userMark);
	});
         
	try
	{
	    Integer mark=null;
	    //This gets the result of the first job to finish
	    //The rest are stopped!
	    //So if the user submits before the time is out the timeout is canceled
	    Result result = e.invokeAny(list);
	    //if the result was a timeout
	    if(result.getName().equals("Timeout"))
	    {
                    ObservableList<Node> items = regularRoot.getChildren();
                    Button submit=null;
		    Button next=null;
		    //find the submit and next buttons
		    //we have to fire them somehow
		    for (Node n : items)
		    {
		        Object data = n.getUserData();
			if(data!=null)
			{
			    if(((String)data).equals("SUBMIT"))
			    {
			        submit = (Button)n;
			    }
			    if(((String)data).equals("NEXT"))
			    {
			        next = (Button)n;
			    }
			}
		    }
		    if(submit!=null && next!=null)
		    {
		        //make sure they are enabled!
		        submit.setDisable(false);
			submit.fire();
			next.setDisable(false);
		        next.fire();
			//They will check the answer and pass it into the score list
			//get the result here:
		        mark = GameLogic.score.take();
		    }
		    else
		    {
		        mark = 0;
		    }
	    }
	    //if the user submits before a timeout
	    else
	    {
	        mark = (Integer)result.getResult();
	    }
	    //inform the other questions that we are finished!
	    //They may now write on the screen
	    synchronized(GameLogic.key)
	    {
	        //MEANS RESTART | EXIT
		if(GameLogic.endGame.peek()!=null)
		{
		    //cleanup!
		    GameLogic.turn.clear();
		}
		//else this question's turn is over
		else
		{
		    GameLogic.turn.remove(qNum);
		}
		GameLogic.key.notifyAll();
	    }
	    //close down this question's executors
	    //otherwise they will still be open when we close the program...
	    e.shutdownNow();
	    executor.shutdown();
	    return mark;
	}
	catch(InterruptedException ex)
	{
	    System.out.println("Unable to complete operation");
	    return null;
	}
	catch(ExecutionException ex)
	{
	    System.out.println("Unable to complete operation");
	    return null;
	}
    }
}
