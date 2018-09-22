import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.Scanner;

public class Question
{
    private String ques;
    private String ans;

    private ExecutorService executor = Executors.newFixedThreadPool(2);
    public Question(String ques, String ans)
    {
        this.ques = ques;
	this.ans = ans;
    }

    public void shutdown()
    {
        executor.shutdown();
    }

    public Future<String> ask()
    {
        return executor.submit(() ->
	{
            System.out.println(ques);
	    Scanner sc = new Scanner(System.in);
	    String myAns = sc.nextLine();
	    if(!ans.equals(myAns))
	    {
	        return "false";
	    }
	    return "true";
	});
    }
}
