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
	    boolean inError = false;
	    List<String> places = new ArrayList<String>(1);
	    places.add(System.getProperty("user.dir") + "/QuestionTypes/");
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
		showError("Plugins directory could not be found");
		inError=true;
	    }
        
	    //No plugins
	    if(!inError && pf.getfileNames().size()==0)
	    {
	       showError("There are no plugins in the given folder");
	    }
            //try{Thread.sleep(5000);}catch(InterruptedException e){}
        
            synchronized(key)
	    {
	        loaded = true;
	    }
        
	    updateList(pf.removeExtension(6));
	});
	t1.start();
    }

    private void showError(String error)
    {
        Platform.runLater(() ->
	{
            Alert alert = new Alert(AlertType.ERROR, error, ButtonType.OK);
	    alert.showAndWait();
	    if(alert.getResult() == ButtonType.OK)
	    {

	    }
	});
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
