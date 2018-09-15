import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class Mainf
{
    public static void main(String[] args)
    {
        //directories to search
        List<String> places = new ArrayList<String>(10);
	places.add("plugins/");
        
	//strings that must be present 
	List<String> contains = new ArrayList<String>(10);
	contains.add("Plugin");
	contains.add(".class");

	PluginFinder pf = new PluginFinder(places, contains);
	try
	{  
	    //find them
	    pf.find();
	    System.out.println(pf.toString());
	}
	catch(IOException e)
	{
	    System.out.println(e);
	}
    }
}
