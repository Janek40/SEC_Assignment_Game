import java.io.IOException;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.List;
import java.util.ArrayList;


public class PluginSetup
{
    private ListView<String> list = new ListView<String>();
    private GridPane root;
    private boolean added = false;
    private boolean inError = false;
    
    private volatile Object key = new Object();
    private volatile PluginFinder pf;
    private volatile boolean loaded = false;

    public PluginSetup(GridPane root)
    {
        this.root = root;

        List<String> loadingList = new ArrayList<String>(1);
	loadingList.add("loading...");
	updateList(loadingList);
    }

    public void updatePluginsList()
    {
        Thread t1 = new Thread(() ->
	{
	    List<String> places = new ArrayList<String>(1);
	    places.add(System.getProperty("user.dir") + "/plugins/");
	    List<String> contains = new ArrayList<String>(2);
	    contains.add("Plugin");
	    contains.add(".class");
	    pf = new PluginFinder(places, contains);
	
	    try
	    {
	        pf.find();
	    }
	    catch(IOException e)
	    {
	        updateWithError("Unable to load plugins");
	        System.out.println("Unable to load plugins");
	    }
        
	    //No plugins
	    if(pf.getfileNames().size()==0)
	    {
	       updateWithError("There are no plugins in plugins/ folder");
	       System.out.println("No plugins");
	    }
            //try{Thread.sleep(5000);}catch(InterruptedException e){}
        
            synchronized(key)
	    {
	        loaded = true;
	        inError = false;
	    }

	    updateList(pf.removeExtension(6));
	});
	t1.start();
    }
    
    public ListView<String> getList()
    {
        //if they are still being loaded
	//or the plugins could not be found
	synchronized(key)
	{
	    if(!loaded || inError)
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
	    if(!loaded || inError)
	    {
                return null;
	    }
	}
        return pf;
    }

    public void setXY(int x, int y)
    {
        Platform.runLater(() ->
	{
	    root.setColumnIndex(list, x);
	    root.setRowIndex(list, y);
	});
    }

    public void setPrefWidthHeight(int xmin, int ymin)
    {
        Platform.runLater(() ->
	{
	    list.setPrefHeight(ymin);
	    list.setPrefWidth(xmin);
	});
    }

    private ObservableList<String> convertToItems(List<String> inItems)
    {
        return FXCollections.observableList(inItems);
    }
    
    private void updateWithError(String error)
    {
        synchronized(key)
	{
	    this.inError = true;
	}
	List<String> errorList = new ArrayList<String>();
	errorList.add(error);
	updateList(errorList);
    }

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
