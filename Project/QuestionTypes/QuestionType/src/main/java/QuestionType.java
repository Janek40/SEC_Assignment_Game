public abstract class QuestionType
{
    public QuestionType(String inName)
    {
        name = inName;
    }
    public String name;
    public String getName()
    {
        return name;
    }
    
    public abstract Question makeQuestion(Object... args);

}
