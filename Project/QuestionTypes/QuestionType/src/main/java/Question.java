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

public class Question
{
    protected GridPane root;
    private ExecutorService executor = Executors.newFixedThreadPool(2);

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
	List<Callable<Integer>> list = new ArrayList<>();
        //if we have a limited amount of time
	if(time>0)
	{
            list.add(() ->
	    {
	        Timeouts timeout = new Timeouts(time, root);
	        return timeout.start();
	    });
	}
	//wait for the user's result to appear
	list.add(() ->
	{
	    return score.take();
	});
         
	//different executor running this
	//waits for EITHER the timeout to occur, which returns 0, or the 
	//result the user pressed next() on
        return executor.submit(() ->
        {
	    Integer result = e.invokeAny(list);
	    e.shutdown();
	    executor.shutdown();
	    return result;
	});
    }
}
