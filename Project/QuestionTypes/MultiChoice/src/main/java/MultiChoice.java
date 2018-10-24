import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.application.Platform;
import javafx.scene.Node;

public class MultiChoice extends QuestionType
{
    public MultiChoice()
    {
        super("MultiChoice");
    }
    

    @Override
    @SuppressWarnings("unchecked")
    public Question makeQuestion(Object... args)
    {
	String desc = (String)args[0];
	String[] choices = (String[])args[1];
	int correct = ((Integer)args[args.length-1]).intValue();
                 
	GridPane regularRoot = makeQuestionActual(desc, choices, correct);
	GridPane previewRoot = makePreview(desc);
	return new Question(regularRoot, previewRoot, super.getScore());
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
    
    private volatile int myScore = -1;

    private GridPane makeQuestionActual(String desc, String[] choices, int correctIdx)
    {
        //main grid
	GridPane root = new GridPane();
	//Submit button
	Button submitBtn = new Button();
	//end message
	Label endMessage = new Label();
	Button nextBtn = new Button();
	//description
	Label descLabel = new Label(desc);
        //choices
	ToggleGroup choicesGroup = new ToggleGroup();
	VBox vbox = new VBox();
	int i;
	for(i=0;i<choices.length;i++)
	{
	    RadioButton button = new RadioButton(choices[i]);
	    button.setUserData(String.valueOf(i));
	    //when selected, make the submit button enabled
	    button.setOnAction((ActionEvent e) ->
	    {
	        submitBtn.setDisable(false);
	    });
	    button.setToggleGroup(choicesGroup);
	    vbox.getChildren().add(button);
	}
        
        
	submitBtn.setText("Submit");
	submitBtn.setDisable(true);
	submitBtn.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent event)
	    {
                Toggle selected = choicesGroup.getSelectedToggle();
		if(selected!=null)
		{
		    String message;
		    if(selected.getUserData().toString().equals(String.valueOf(correctIdx)))
		    {
			message = "Correct!";
			myScore = 1;
		    }
		    else
		    {
			message = "Wrong!";
			myScore = 0;
		    }
                    Platform.runLater(() ->
		    {
		        choicesGroup.getToggles().forEach(toggle ->
			{
			    Node node = (Node)toggle;
			    node.setDisable(true);
			});
			endMessage.setText(message);
			submitBtn.setDisable(true);
			nextBtn.setDisable(false);
		    });
		}
		else
		{
		    myScore = 0;
		}
	    }
	});


	nextBtn.setText("Next");
	nextBtn.setDisable(true);
	nextBtn.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent event)
	    {
		try
		{
		    score.put(myScore);
		    myScore = -1;
		}
		catch(InterruptedException e)
		{
		    System.out.println("Interrupted!");
		}
	    }
	});

	Button restartBtn = new Button();
	restartBtn.setText("Exit Quiz");
	restartBtn.setDisable(false);
        restartBtn.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override
	    public void handle(ActionEvent event)
	    {
	        try
		{
		    score.put(-1);
		}
		catch(InterruptedException e)
		{
		    System.out.println("interrupted");
		}
	    }
	});

        submitBtn.setUserData("SUBMIT");
	nextBtn.setUserData("NEXT");
	
	//description
	root.add(descLabel, 0,0);
	//options
	root.add(vbox, 0,1);
	//submit button
	root.add(submitBtn, 0,2);
	//end message
        root.add(endMessage, 0, 3);
	//next button
	root.add(nextBtn, 0, 4);
	//restart
	root.add(restartBtn, 0, 5);
        
	return root;
    }
}
