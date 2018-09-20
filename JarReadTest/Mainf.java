import java.io.IOException;
import java.util.List;

public class Mainf
{
    public static void main(String[] args)
    {
        JarReader jreader = new JarReader();
	try
	{
	    jreader.readJar("DoctorWhoQuiz.jar");
	    List<String> names = jreader.getNames();
	    for(int i=0;i<names.size();i++)
	    {
	        System.out.println(names.get(i));
	    }
	}
	catch(IOException ex)
	{
	    System.out.println(ex.getMessage());
	}
    }
}
