import javafx.scene.layout.GridPane;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.scene.control.Label;

public abstract class QuestionType
{
    private String name;
    public LinkedBlockingQueue<Integer> score = new LinkedBlockingQueue<Integer>();

    public QuestionType(String inName)
    {
        this.name = inName;
    }

    public String getName()
    {
        return this.name;
    }

    public LinkedBlockingQueue<Integer> getScore()
    {
        return score;
    }

    public abstract Question makeQuestion(Object... args);
    protected abstract GridPane makePreview(String desc);
 //   public abstract void restartQuiz();
    //public abstract void returnToQuizSelection();
}
