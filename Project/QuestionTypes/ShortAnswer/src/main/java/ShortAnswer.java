import javafx.scene.layout.GridPane;

public class ShortAnswer extends QuestionType
{
    public ShortAnswer()
    {
        super("ShortAnswer");
    }
    
    protected GridPane makePreview(String desc)
    {
         return null;
    }

    @Override
    public Question makeQuestion(Object... args)
    {
        return null;
    }
}
