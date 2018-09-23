public class Result
{
    private String name;
    private Object result;
    public Result(String name, Object result)
    {
        this.name = name;
	this.result = result;
    }

    public String getName()
    {
        return this.name;
    }

    public Object getResult()
    {
        return this.result;
    }
}
