import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Timeouts
{
    private int numSeconds;
    private GridPane root;
    private String labelText = "Time left: ";
    private Label label;
 //   private LinkedBlockingQueue<Integer> score;

    public Timeouts(int numSeconds, GridPane root/*, LinkedBlockingQueue<Integer> score*/)
    {
        this.numSeconds = numSeconds;    
	this.root = root;
	this.label = new Label();
	this.label.setText(labelText);
        
	/*this.score = score;*/
    }

    public Integer start()
    {
        Platform.runLater(() ->
	{
	    root.add(label, 0, 5);
	});
	try
	{
	    for(int i=numSeconds;i>=0;i--)
	    {
		updateTimeout(i);
		Thread.sleep(1000);
	    }
	 }
	 catch(InterruptedException e)
	 {
	     System.out.println("AHHH LEMME SLEEP");
	 }
	 return 0;
    }

    private void updateTimeout(int time)
    {
        Platform.runLater(() ->
	{
	    label.setText(labelText + time);
	});
    }
}
