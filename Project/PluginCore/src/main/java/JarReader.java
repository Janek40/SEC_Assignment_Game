import java.util.jar.JarFile;
import java.util.Enumeration;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

public class JarReader
{
    private List<byte[]> contents = new ArrayList<byte[]>();
    private List<String> names = new ArrayList<String>();

    public JarReader(){}

    public List<byte[]> getContents()
    {
        return contents;
    }

    public List<String> getNames()
    {
        return names;
    }

    public void readJar(String file) throws IOException
    {
        JarFile jfile = new JarFile(file);
        Enumeration<JarEntry> entries = jfile.entries();
        while(entries.hasMoreElements())
	{
	    JarEntry entry = entries.nextElement();
	    if(entry.getName().contains(".class"))
	    {
		InputStream input = jfile.getInputStream(entry);
		process(entry.getName(), input);
	    }
	}
	jfile.close();
    }

    public void process(String name, InputStream file) throws IOException
    {
        try
	{
            byte[] content = file.readAllBytes();
	    contents.add(content);
	    names.add(name);
	}
	catch(IOException ex)
	{
	    throw new IOException("Unable to read file to array", ex);
	}
    }
    
}
