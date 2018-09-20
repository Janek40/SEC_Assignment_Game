import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Map;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DoctorWhoQuiz extends QuizPlugin
{
    private final int WINDOW_X = 500;
    private final int WINDOW_Y = 500;
    
    public DoctorWhoQuiz()
    {
        super("DoctorWhoQuiz");
    }

    @Override
    public void runQuiz(Scene prevScene, Stage primaryStage)
    {
	System.out.println("Doctor who Quiz running");
	Thread t1 = new Thread(() ->
	{
	    GridPane newRoot = new GridPane();
	    Platform.runLater(() ->
	    {
	        primaryStage.setTitle("Doctor who quiz");
	        primaryStage.setScene(new Scene(newRoot, WINDOW_X, WINDOW_Y));
	        primaryStage.show();
	    });
	    Button btn = new Button();
	    btn.setText("Back");
	    btn.setOnAction(new EventHandler<ActionEvent>()
	    {
	        @Override
		public void handle(ActionEvent event)
		{
		    Platform.runLater(() ->
		    {
		        primaryStage.setScene(prevScene);
		    });
		}
	    });
	    Platform.runLater(() ->
	    {
	        newRoot.add(btn, 0, 0);
	    });

	    try
	    {
	        super.loadPlugins();
	        QuestionType mcp = super.get("MultiChoicePlugin");
		QuestionType sa = super.get("ShortAnswerPlugin");
		System.out.println(mcp.getName());
		System.out.println(sa.getName());
		System.out.println("Done");
	    }
            catch(IOException e)
	    {
	        ErrorGUI err = new ErrorGUI();
		err.showError(e.getMessage());
		return;
	    }
            catch(ClassNotFoundException e)
	    {
	        ErrorGUI err = new ErrorGUI();
		err.showError(e.getMessage());
		return;
	    }
	});
	t1.start();
    }
}
