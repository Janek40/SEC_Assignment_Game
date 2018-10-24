/***************************************************************************
* Author: Janek Joyce
* Last Updated: 21/09/2018
* Purpose: Handles the main plugin/quiz window's list of selectable plugins.
*          This code is intended for the 2018 semester 1 SEC assignment
***************************************************************************/

import java.io.IOException;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class PluginSetup
{
    private ListView<String> list = new ListView<String>();
    private GridPane root;
    private boolean added = false;
    
    private volatile Object key = new Object();
    private volatile PluginFinder pf;
    private volatile boolean loaded = false;

    private List<String> places;
    private List<String> contains;

    private ErrorGUI error = new ErrorGUI();

    public PluginSetup(GridPane root, List<String> places, List<String> contains)
    {
        this.root = root;
	this.places = places;
	this.contains = contains;
        //Make the list contain the text 'loading...' so the user can see someone is happening
        List<String> loadingList = new ArrayList<String>(1);
	loadingList.add("loading...");
	updateList(loadingList);
    }
    
	//Given a list of places and what the plugin names contain
	//find them, and add them to the user's list
    public void updatePluginsList()
    {
        Thread t1 = new Thread(() ->
	{
	    boolean inError = false;
            pf = new PluginFinder(places, contains);
	
	    try
	    {
	        pf.find();
	    }
	    catch(IOException e)
	    {
		error.showError("Plugins directory could not be found");
		inError=true;
	    }
        
	    //No plugins
	    if(!inError && pf.getfileNames().size()==0)
	    {
	       error.showError("There are no plugins in the given folder");
	    }
        
            synchronized(key)
	    {
	        loaded = true;
	    }
        
	    updateList(pf.removeExtension(4));
	});
	t1.start();
    }

   
    public ListView<String> getList()
    {
        //if they are still being loaded
	//or the plugins could not be found
	synchronized(key)
	{
	    if(!loaded)
	    {
                return null;
	    }
	}
	return this.list;
    }

    public PluginFinder getOriginalFinder()
    {
        synchronized(key)
	{
	    if(!loaded)
	    {
                return null;
	    }
	}
        return pf;
    }
    
	//Set the X and Y position this list is at
    public void setXY(int x, int y)
    {
        Platform.runLater(() ->
	{
	    root.setColumnIndex(list, x);
	    root.setRowIndex(list, y);
	});
    }
    
	//set the list's prefered x and y dimensions
    public void setPrefWidthHeight(int xmin, int ymin)
    {
        Platform.runLater(() ->
	{
	    list.setPrefHeight(ymin);
	    list.setPrefWidth(xmin);
	});
    }
    
	//converts a List<String> to ObservableList<String>
    private ObservableList<String> convertToItems(List<String> inItems)
    {
        return FXCollections.observableList(inItems);
    }
    
	//Updates the list with this list of items
    public void updateList(List<String> inItems)
    {
        ObservableList<String> items = convertToItems(inItems);
        Platform.runLater(new Runnable()
	{
	    @Override
	    public void run()
	    {
	        list.setItems(items);
		if(!added)
		{
		    root.add(list, 0, 0);
		    added = true;
		}
	    }
	});
    }
}
