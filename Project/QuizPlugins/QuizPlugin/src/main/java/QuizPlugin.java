public abstract class QuizPlugin
{
    private String name;
    
    public QuizPlugin(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public abstract void runQuiz();
}
