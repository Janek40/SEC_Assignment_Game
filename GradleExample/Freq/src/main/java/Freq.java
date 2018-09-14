import com.google.common.collect.*;

public class Freq
{
    public static void main(String[] args)
    {
        System.out.println(getFreq(args));
    }

    public static String getFreq(String[] args)
    {
        Multiset<String> multiset = HashMultiset.create();
	for(String arg : args)
	{
	    multiset.add(arg);
	}

	String result="";
	for(String arg : multiset.elementSet())
	{
	    result += arg + ':' + multiset.count(arg) + '\n';
	}
	return result;
    }
}
