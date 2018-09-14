public class AddPlugin implements ArithmeticPlugin
{
    public AddPlugin(){}
    
    public String getName()
    {
        return "AddPlugin";
    }

    public float operate(float x, float y)
    {
        return x+y;
    }
}
