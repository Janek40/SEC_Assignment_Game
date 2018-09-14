import java.util.Scanner;
import java.util.*;

public class MainF
{
    public static void main(String[] args)
    {
        Map<String, ArithmeticPlugin> plugins;
	PluginLoader pLoader = new PluginLoader();
	try
	{
	    if(args.length==0)
	    {
	        System.out.println("Don't forget plugins!");
		return;
	    }
	    //Load plugins
	    plugins = pLoader.loadPlugins(args);
            System.out.println("Plugins loaded");

            //get two floats and a plugin name
	    Scanner sc = new Scanner(System.in);
	    String line = sc.nextLine();
	    String[] parts = line.split(" ");
	    if(parts.length!=3)
	    {
	        System.out.println("Invalid format! float float plugin"); 
		return;
	    }
	    float val1 = Float.parseFloat(parts[0]);
	    float val2 = Float.parseFloat(parts[1]);
	    ArithmeticPlugin selected;
	    if((selected=plugins.get(parts[2]))==null)
	    {
	        System.out.println("Plugin does not exist!");
		return;
	    }
	    float ans = selected.operate(val1, val2);
	    System.out.println(ans);
	}
	catch(NumberFormatException ex)
	{
	    System.out.println("Given string not a float!");
	}
	catch(NullPointerException ex)
	{
	    System.out.println("Given value was null!");
	}
	catch(ClassNotFoundException ex)
	{
	    System.out.println(ex.getMessage());
	}
	catch(Exception ex)
	{
	}
    }
}
