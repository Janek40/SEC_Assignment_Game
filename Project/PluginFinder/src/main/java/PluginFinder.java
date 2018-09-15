import java.util.LinkedList;

public class PluginFinder
{
    private List<String> places;

    public PluginFinder(List<String> places)
    {
        this.places = places;
    }

    public void find()
    {
    }

    @Override
    //O(n^2)
    public String toString()
    {
        String ans="";
	int length = places.size();
	for(int i=0;i<length;i++)
	{
	    ans = ans + ", " + places.get(i);
	}
	return ans;
    }
}
