/***************************************************************************
* Author: Janek Joyce
* Last Updated: 25/10/2018
* Purpose: To create a short answer question
*          This code is intended for the 2018 semester 1 SEC assignment
***************************************************************************/


import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ShortAnswer extends QuestionType
{
    private final int WINDOW_X = 500;
    private final int WINDOW_Y = 500;

    public ShortAnswer()
    {
        super("ShortAnswer");
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Question makeQuestion(Object... args)
    {
        String desc = (String)args[0];
	String correct = (String)args[1];

	GridPane regularRoot = makeQuestionActual(desc, correct);
	GridPane previewRoot = makePreview(desc);
	return new Question(regularRoot, previewRoot/*, super.getScore()*/);
    }

    @Override
    protected GridPane makePreview(String desc)
    {
         GridPane root = new GridPane();
	 Label descLabel = new Label(desc);
	 descLabel.setText(desc);
	 root.add(descLabel, 0, 0);
	 return root;
    }

    private volatile Object scoreKey = new Object();
    private volatile int myScore = -1;

    private GridPane makeQuestionActual(String desc, String answer)
    {
        GridPane root = new GridPane();
	Button submitBtn = new Button();
	Button nextBtn = new Button();
	Button restartBtn = new Button();
	Label descLabel = new Label(desc);
        Label endMessage = new Label();

        TextField textField = new TextField();
	HBox hb = new HBox();
	hb.getChildren().addAll(new Label("Answer..."), textField);
	hb.setSpacing(10);

	//dont forget a text field!
        submitBtn.setText("Submit");
	submitBtn.setUserData("SUBMIT");
	submitBtn.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent event)
	    {
	        String input = textField.getCharacters().toString();
		String message = "";
		if(input.equals(answer))
		{
		    myScore = 1;
		    message = "Correct!";
		}
		else
		{
		    myScore = 0;
		    message = "Wrong!";
		}
		endMessage.setText(message);
		submitBtn.setDisable(true);
		nextBtn.setDisable(false);
	    }
	});

        nextBtn.setText("Next");
	nextBtn.setUserData("NEXT");
	nextBtn.setDisable(true);
	nextBtn.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent event)
	    {
	        try
		{
	            GameLogic.score.put(myScore);
		}
		catch(InterruptedException e)
		{
		    System.out.println("Interrupted!");
		}
	    }
	});

	restartBtn.setText("Exit Quiz");
	restartBtn.setDisable(false);
	restartBtn.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent event)
	    {
	        try
		{
		    GameLogic.score.put(0);
		    GameLogic.endGame.put(1);
		}
		catch(InterruptedException e)
		{
		    System.out.println("Unable to exit quiz, because I was exited?");
		}
	    }
	});
        
	root.add(descLabel,  0,0);
	root.add(hb,         0,1);
	root.add(submitBtn,  0,2);
	root.add(nextBtn,    0,3);
	root.add(endMessage, 0,4);
	root.add(restartBtn, 0,5);
        return root;
    }


}
