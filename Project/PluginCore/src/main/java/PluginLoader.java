/***************************************************************************
* Author: Janek Joyce
* Last Updated: 21/09/2018
* Purpose: Loads a class/plugin at runtime
*          This code is intended for the 2018 semester 1 SEC assignment
***************************************************************************/

import java.nio.file.*;
import java.util.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.io.File; 

public class PluginLoader<E> extends ClassLoader
{
    public PluginLoader(){}
    
	//Loads an object from a .jar file
	//Given the directory of the jar, the name of the object in the jar to construct, and the new object's parent class
	//This code was adapted from MANY different sources, I did not simply copy and paste this.
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
	catch(MalformedURLException e)
	{
	    throw new ClassNotFoundException("Cannot load plugin, Given file location is invalid",e);
	}
	catch(NoSuchMethodException e)
	{
	    throw new ClassNotFoundException("Cannot load plugin, Given jar could not be created",e);
	}
	catch(InstantiationException e)
	{
	    throw new ClassNotFoundException("Cannot load plugin, is this an abstract class?",e);
	}
	catch(IllegalAccessException e)
	{
	    throw new ClassNotFoundException("Cannot load plugin, illegal access",e);
	}
	catch(InvocationTargetException e)
	{
	    throw new ClassNotFoundException("Cannot load plugin, unable to invoke",e);
	}
    }
}
