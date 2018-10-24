/***************************************************************************
* Author: Janek Joyce
* Last Updated: 22/09/2018
* Purpose: To show error messages
*          This code is intended for the 2018 semester 1 SEC assignment
***************************************************************************/

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class ErrorGUI
{
    public ErrorGUI()
    {
    }
    
	//Shows the given string as an error to the user. 
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
