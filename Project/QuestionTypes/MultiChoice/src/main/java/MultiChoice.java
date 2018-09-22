import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class MultiChoice extends QuestionType
{
    public MultiChoice()
    {
        super("MultiChoice");
    }

    @Override
    public Question makeQuestion(Object... args)
    {
        String desc = (String)args[0];
	String[] choices = (String[])args[1];
	int correct = ((Integer)args[args.length-1]).intValue();

	return makeQuestionActual(desc, choices, correct);
    }

    private Question makeQuestionActual(String desc, String[] choices, int correctIdx)
    {
        GridPane root = new GridPane();
	
	//description
	Label label = new Label(desc);
	root.add(label, 0,0);
        //choices
	int i;
	for(i=0;i<choices.length;i++)
	{
	    root.add(new Label(choices[i]), i, 1);    
	}
        //input box
	/*
	Label labelIn = new Label("Answer:");
	TextField input = new TextField();
	input.setOnAction(new EventHandler<ActionEvent>()
	{
	    @Override 
	    public void handle(ActionEvent event)
	    {
	        if(choices[correctIdx].equals(input.getCharacters().toString()))
		{
		    System.out.println("correct!");
		}
		else
		{
		    System.out.println("Noooo");
		}
	    }
	});
	
	HBox hb = new HBox();
	hb.getChildren().addAll(labelIn, input);
	hb.setSpacing(10);

	root.add(hb, 0, i);
        */
	return new Question(root);


    }
}
