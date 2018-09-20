import java.nio.file.*;
import java.util.*;


public class PluginLoader extends ClassLoader
{
    private Map<String, QuestionType> plugins = new HashMap<String, QuestionType>(16);


    public PluginLoader(){}
    
    public Map<String, QuestionType> loadPlugins(List<String> args) throws ClassNotFoundException
    {
        String[] arr = new String[args.size()];
	for(int i=0;i<args.size();i++)
	{
	    arr[i] = args.get(i);
	}
	return loadPlugins(arr);
    }


    public Map<String, QuestionType> loadPlugins(String[] args) throws ClassNotFoundException
    {
        for(String arg : args)
	{
	    QuestionType plugin;
	    try
	    {
	        plugin = loadPlugin(arg);
	    }
	    catch(ClassNotFoundException ex)
	    {
	        throw new ClassNotFoundException(ex.getMessage(), ex);
	    }

	    if(plugins.containsKey(plugin.getName()))
	    {
	         System.out.println("Two plugins with the same name is present");
	    }

	    plugins.put(plugin.getName(), plugin);
	}
	return plugins;
    }

    public QuestionType loadPlugin(String fname) throws ClassNotFoundException
    {
        try
	{
	    byte[] classData = Files.readAllBytes(Paths.get(fname));
	    Class<?> cls = defineClass(null, classData, 0, classData.length);
	    return (QuestionType)cls.newInstance();
	}
	catch(Exception ex)
	{
	    throw new ClassNotFoundException(String.format("Could not load '%s' : %s", fname, ex.getMessage()), ex);
	}
    }

    public Map<String, QuestionType> getPlugins()
    {
        return plugins;
    }
}
