public class MultiplyPlugin implements ArithmeticPlugin
{
    public MultiplyPlugin(){}
    
    public String getName()
    {
        return "MultiplyPlugin";
    }

    public float operate(float x, float y)
    {
        return x*y;
    }
}
