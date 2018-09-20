import java.nio.file.*;
import java.util.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class PluginLoader<E> extends ClassLoader
{
    public PluginLoader(){}
    
    
    @SuppressWarnings("unchecked")
    public E loadPlugin(String loc) throws ClassNotFoundException
    {
        try
	{
	    JarReader jreader = new JarReader();
	    jreader.readJar(loc);
	    List<byte[]> contents = jreader.getContents();
	    List<String> names = jreader.getNames();
	    
	    int i;
	    String base=null;
	    for(i=0;i<names.size();i++)
	    {
	        base = names.get(i);
	        if(!base.contains("$"))
		{
		    break;
		}
	    }
            
	    if(base==null)
	    {
	        return null;
	    }

            byte[] basePlugin = contents.remove(i);
	    Class<?> cls = defineClass(null, basePlugin, 0, basePlugin.length);
	    E obj = (E)cls.getDeclaredConstructor().newInstance();
            
	    for(i=0;i<contents.size();i++)
	    {
	        byte[] innerClass = contents.remove(i);
	        Class<?> cls2 = defineClass(null, innerClass, 0, innerClass.length);
                resolveClass(cls);
	    }
            System.out.println("    Loaded: " + obj.toString());
	    return obj;
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
	catch(java.lang.LinkageError ex)
	{
	    return null;
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
