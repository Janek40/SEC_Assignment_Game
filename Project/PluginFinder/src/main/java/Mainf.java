import java.util.LinkedList;

public class Mainf
{
    public static void main(String[] args)
    {
        List<String> places = new LinkedList<String>();
	places.add("plugins/");
	PluginFinder pf = new PluginFinder(places);
	pf.find();
	System.out.println(pf.toString());
    }
}
