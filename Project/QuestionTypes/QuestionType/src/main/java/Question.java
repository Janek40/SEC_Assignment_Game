import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;

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
    protected GridPane root;
    private ExecutorService executor = Executors.newFixedThreadPool(1);

    public Question(GridPane root)
    {
        this.root = root;
    }

    public Future<Integer> invoke(int time, Stage primaryStage, LinkedBlockingQueue<Integer> score)
    {
	//Show the question
	Platform.runLater(() ->
	{
	    primaryStage.setScene(new Scene(root, 500, 500));
	    primaryStage.show();
	});
	//Start an executor service
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
	        Timeouts timeout = new Timeouts(time, root);
	        Integer fail = timeout.start();
		return new Result("Timeout", fail);
	    });
	}
	//wait for the user's result to appear
	list.add(() ->
	{
	    Integer userMark = score.take();
	    return new Result("User", userMark);
	});
         
	//different executor running this
	//waits for EITHER the timeout to occur, which returns 0, or the 
	//result the user pressed next() on
        return executor.submit(() ->
        {
	    Integer mark=null;
	    Result result = e.invokeAny(list);
	    if(result.getName().equals("Timeout"))
	    {
		    System.out.println("timeout!");
		    boolean submitted = false;
                    ObservableList<Node> items = root.getChildren();
                    Button submit=null;
		    Button next=null;
		    for (Node n : items)
		    {
		        Object data = n.getUserData();
			if(data!=null)
			{
			    if(((String)data).equals("SUBMIT"))
			    {
			        System.out.println("Have submit");
			        submit = (Button)n;
			    }
			    if(((String)data).equals("NEXT"))
			    {
			        System.out.println("Have next");
			        next = (Button)n;
			    }
			}
		    }
		    if(submit!=null && next!=null)
		    {
		        System.out.println("Firing");
		        submit.setDisable(false);
			submit.fire();
			next.setDisable(false);
		        next.fire();
			System.out.println("Fired");
		        mark = score.take();
		    }
		    else
		    {
		        mark = 0;
		    }
	    }
	    else
	    {
	        mark = (Integer)result.getResult();
	        System.out.println("USER SUBMIT");
	    }
	    e.shutdownNow();
	    executor.shutdown();
	    return mark;
	});
    }
}
