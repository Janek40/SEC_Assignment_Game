import java.nio.file.*;
import java.util.*;


public class PluginLoader<E> extends ClassLoader
{
    public PluginLoader(){}
    
    @SuppressWarnings("unchecked")
    public E loadPlugin(String fname) throws ClassNotFoundException
    {
        try
	{
	    byte[] classData = Files.readAllBytes(Paths.get(fname));
	    Class<?> cls = defineClass(null, classData, 0, classData.length);
	    return (E)cls.getDeclaredConstructor().newInstance();
	}
	catch(Exception ex)
	{
	    throw new ClassNotFoundException(String.format("Could not load '%s' : %s", fname, ex.getMessage()), ex);
	}
    }
}
