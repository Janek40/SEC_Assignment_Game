import java.nio.file.*;
import java.util.*;

public class PluginLoader extends ClassLoader
{
    private Map<String, ArithmeticPlugin> plugins = new HashMap<String, ArithmeticPlugin>(16);


    public PluginLoader(){}
    
    public Map<String, ArithmeticPlugin> loadPlugins(String[] args) throws ClassNotFoundException
    {
        for(String arg : args)
	{
	    ArithmeticPlugin plugin;
	    try
	    {
	        plugin = loadPlugin(arg);
		System.out.println("Plugin: " + plugin.getName());
	    }
	    catch(ClassNotFoundException ex)
	    {
	        throw new ClassNotFoundException(ex.getMessage(), ex);
	    }

	    if(plugins.containsKey(plugin.getName()))
	    {
	         System.out.println("A plugin with that name is already loaded! " + plugin.getName());
	    }

	    plugins.put(plugin.getName(), plugin);
	}
	return plugins;
    }

    public ArithmeticPlugin loadPlugin(String fname) throws ClassNotFoundException
    {
        try
	{
	    byte[] classData = Files.readAllBytes(Paths.get(fname));
	    Class<?> cls = defineClass(null, classData, 0, classData.length);
	    return (ArithmeticPlugin)cls.newInstance();
	}
	catch(Exception ex)
	{
	    throw new ClassNotFoundException(String.format("Could not load '%s' : %s", fname, ex.getMessage()), ex);
	}
    }

    public Map<String, ArithmeticPlugin> getPlugins()
    {
        return plugins;
    }
}
