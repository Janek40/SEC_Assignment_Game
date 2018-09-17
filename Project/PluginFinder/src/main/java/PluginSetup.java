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

    public PluginSetup(GridPane root)
    {
        this.root = root;

        List<String> loadingList = new ArrayList<String>(1);
	loadingList.add("loading...");
	updateList(loadingList);
    }

    public void updatePluginsList() throws IOException
    {
        List<String> places = new ArrayList<String>(1);
	places.add("plugins/");
	List<String> contains = new ArrayList<String>(2);
	contains.add("Plugin");
	contains.add(".class");
	PluginFinder pf = new PluginFinder(places, contains);
        try
	{
	    pf.find();
	}
	catch(IOException e)
	{
	    throw new IOException("Unable to load plugins", e);
	}

	updateList(pf.removeExtension(6));
    }
    
    public ListView<String> getList()
    {
        return this.list;
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
