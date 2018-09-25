public class SafeBoolean
{
    private boolean val;
    public SafeBoolean(boolean val)
    {
        this.val = val;
    }
    
    public void setValue(boolean val)
    {
        this.val = val;
    }

    public boolean getValue()
    {
        return this.val;
    }
}
