import java.nio.file.*;
import java.util.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.File; 

public class PluginLoader<E> extends ClassLoader
{
    public PluginLoader(){}
    
    @SuppressWarnings("unchecked")
    public E loadPlugin(String loc, String name, Class<E> parentClass) throws ClassNotFoundException
    {
	try
	{
	System.out.println("Loading: " + loc);
	File jar = new File(loc);
        ClassLoader loader = URLClassLoader.newInstance(
	    new URL[] {jar.toURI().toURL()},
	    getClass().getClassLoader()
	);
        Class<?> clazz = Class.forName(name, true, loader);
	Class<? extends E> newClass = clazz.asSubclass(parentClass);
	return (E)newClass.getDeclaredConstructor().newInstance();
	}
	catch(Exception e)
	{
	    System.out.println(e.toString());
	    throw new ClassNotFoundException("fuck", e);
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
