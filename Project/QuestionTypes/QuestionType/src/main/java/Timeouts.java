import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Timeouts
{
    private int numSeconds;
    private GridPane root;
    private String labelText = "Time left: ";
    private Label label;

    public Timeouts(int numSeconds, GridPane root)
    {
        this.numSeconds = numSeconds;    
	this.root = root;
	this.label = new Label();
	this.label.setText(labelText);
    }

    public Integer start()
    {
        Platform.runLater(() ->
	{
	    root.add(label, 0, 6);
	});
	try
	{
	    for(int i=numSeconds;i>=0;i--)
	    {
		updateTimeout(i);
		Thread.sleep(1000);
	    }
	 }
	 //no worries
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
