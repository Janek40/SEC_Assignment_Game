/***************************************************************************
* Author: Janek Joyce
* Last Updated: 23/09/2018
* Purpose: Holds the a name and result object, this is used by Timeouts and quiz questions - so we can tell them apart 
*          This code is intended for the 2018 semester 1 SEC assignment
***************************************************************************/

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
