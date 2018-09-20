import java.nio.file.*;
import java.util.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class PluginLoader<E> extends ClassLoader
{
    public PluginLoader(){}
    
    
    @SuppressWarnings("unchecked")
    public Map<String, E> loadPlugins(List<String> locations) throws ClassNotFoundException
    {
        Map<String, E> plugins = new HashMap<String, E>();
	int length = locations.size();
        E plugin;
	String location;
	for(int i=0;i<length;i++)
	{
            location = locations.get(i);
	    plugin = loadPlugin(location);
	    String className = getClassName(plugin);
	    plugins.put(className, plugin);
	}
	return plugins;
    }

    
    @SuppressWarnings("unchecked")
    public E loadPlugin(String fname) throws ClassNotFoundException
    {
        try
	{
	    byte[] classData = Files.readAllBytes(Paths.get(fname));
	    Class<?> cls = defineClass(null, classData, 0, classData.length);
	    return (E)cls.getDeclaredConstructor().newInstance();
	}
	//trying to load the abstract base class
	catch(InstantiationException ex)
	{
	    throw new ClassNotFoundException("You cannot load the base class!");
	}
	catch(IOException ex)
	{
	    throw new ClassNotFoundException("Unable to load the given plugin");
	}
	catch(NoSuchMethodException ex)
	{
	    throw new ClassNotFoundException("Unable to load, is it the base class?");
	}
	catch(IllegalAccessException ex)
	{
	    throw new ClassNotFoundException("You do not have the permissions to load this plugin");
	}
	catch(InvocationTargetException ex)
	{
	    throw new ClassNotFoundException("Unable to invoke the given plugin");
	}
    }

    public String getClassName(E plugin)
    {
        String ans = "";
	int cutOff = 7;
	String currName = plugin.getClass().toString();
        int length = currName.length();
	for(int i=cutOff-1;i<length;i++)
	{
	    ans += currName.charAt(i);
	}
	return ans;
    }

}
