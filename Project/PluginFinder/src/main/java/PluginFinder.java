import java.util.List;
import java.util.LinkedList;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class PluginFinder
{
    private List<String> searchLocations;
    private List<String> mustContain;
    private List<String> fileNames = new LinkedList<String>();
    private List<String> locations = new LinkedList<String>();

    public PluginFinder(List<String> searchLocations, List<String> mustContain)
    {
	this.searchLocations = searchLocations;
	this.mustContain = mustContain;
    }

    public List<String> getfileNames()
    {
        return fileNames;
    }

    public List<String> getLocations()
    {
        return locations;
    }
    
    //Function removes 'length' number of characters off the end of each fileName
    //entry, and returns this as a new list - leaving the original fileNames List
    //unchanged
    public List<String> removeExtension(int length)
    {
        List<String> fOnly = new LinkedList<String>();
	for(int i=0;i<fileNames.size();i++)
	{
	    String temp="";
	    String curr = fileNames.get(i);
	    for(int j=0;j<curr.length()-length;j++)
	    {
	        temp += curr.charAt(j);
	    }
	    fOnly.add(temp);
	}
	return fOnly;
    }
    
    /*
        Function iterates through a set of directories recursively (via walk())
        and adds to the files List any files that contain the strings present in
        mustContain
    */
    //I use this to find the plugins, given starting directory(s) search them for
    //anything containing the worlds Plugin and .class - as it needs to be a .class file
    public void find() throws IOException
    { 
        int length = searchLocations.size();
	for(int i=0;i<length;i++)
	{
	    //try-with-resource, if it throws an exception the resource is 
	    //automatically closed, instead of using the finally{} block
	    //This is a new feature
	    String directory = searchLocations.get(i);
	    try (Stream<Path> paths = Files.walk(Paths.get(directory)))
	    {
	        paths.filter(Files::isRegularFile).forEach( filePath -> 
		{
                    boolean contains = true;
		    String fName = filePath.getFileName().toString();
		    for(int j=0;j<mustContain.size();j++)
		    {
		        String curr = mustContain.get(j);
			if(!fName.contains(curr))
			{
			    contains = false;
			    break;
			}
		    }
		    
		    if(contains)
		    {
		        fileNames.add(filePath.getFileName().toString());
			locations.add(/*System.getProperty("user.dir") + "/" + */filePath.toString());
		    }
		});
	    }
	    catch(IOException e)
	    {
	        throw new IOException("Unable to read given directory", e);
	    }
	}
    }



}
