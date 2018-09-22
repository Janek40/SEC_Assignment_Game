public class Timeouts
{
    private int numSeconds;

    public Timeouts(int numSeconds)
    {
        this.numSeconds = numSeconds;    
    }

    public void start()
    {
        Thread t1 = new Thread(() ->
	{
	    try
	    {
	        for(int i=numSeconds;i>=0;i--)
		{
		    System.out.println(i);
		    Thread.sleep(1000);
		}
	    }
	    catch(InterruptedException e)
	    {
	        System.out.println("AHHH LEMME SLEEP");
	    }
	});
	t1.start();
    }
}
