/***************************************************************************
* Author: Janek Joyce
* Last Updated: 23/09/2018
* Purpose: Used by a quiz question to show a timeout on the window.
*          This code is intended for the 2018 semester 1 SEC assignment
***************************************************************************/

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
	 //just means the user finished the question before the timeout! :D
	 catch(InterruptedException e)
	 {
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
