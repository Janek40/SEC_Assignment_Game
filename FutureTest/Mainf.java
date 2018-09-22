import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Mainf
{
    public static void main(String[] args)
    {
        Question q = new Question("What is my name?", "Janek");
	Future<String> ans = q.ask();
	try
	{
	    try
	    {
	        System.out.println(ans.get(5000, TimeUnit.MILLISECONDS));
            }
	    catch(TimeoutException e)
	    {
	        System.out.println("Too late!");
	    }
	}
	catch(InterruptedException e)
	{
	    System.out.println("Interrupted");
	}
	catch(ExecutionException e)
	{
	    System.out.println(e.toString());
	}
	q.shutdown();
    }
}
