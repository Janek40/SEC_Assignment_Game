import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class ErrorGUI
{
    public ErrorGUI()
    {
    }

    public void showError(String error)
    {
        System.out.println(error);
	Platform.runLater(() ->
	{
	    Alert alert = new Alert(AlertType.ERROR, error, ButtonType.OK);
	    alert.showAndWait();
	});
    }
}
