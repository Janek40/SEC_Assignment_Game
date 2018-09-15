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
    private List<String> files = new LinkedList<String>();

    public PluginFinder(List<String> searchLocations, List<String> mustContain)
    {
	this.searchLocations = searchLocations;
	this.mustContain = mustContain;
    }

    public List<String> getFiles()
    {
        return files;
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
	        paths.filter(Files::isRegularFile).forEach( file -> 
		{
		    String myFile = file.toString();
		    boolean contains = true;
		    for(int j=0;j<mustContain.size();j++)
		    {
		        String curr = mustContain.get(j);
			if(!myFile.contains(curr))
			{
			    contains = false;
			    break;
			}
		    }
		    if(contains)
		    {
		        files.add(file.toString());
		    }
		});
	    }
	    catch(IOException e)
	    {
	        throw new IOException("Unable to read given directory", e);
	    }
	}
    }


    @Override
    //O(n^2)
    public String toString()
    {
        String ans="";
	int length = files.size();
	for(int i=0;i<length;i++)
	{
	    if(i==0)
	    {
	        ans = ans + files.get(i);
	    }
	    else
	    {
	        ans = ans + '\n' + files.get(i);
	    }
	}
	return ans;
    }
}
