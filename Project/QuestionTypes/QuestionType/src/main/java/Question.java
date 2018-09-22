import javafx.scene.layout.GridPane;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Question
{
    private GridPane root;
    
    public Question(GridPane root)
    {
        this.root = root;
    }

    public void invoke(int time, Stage primaryStage)
    {
        Platform.runLater(() ->
	{
	    primaryStage.setScene(new Scene(root, 500, 500));
	    primaryStage.show();
	});
    }
}
