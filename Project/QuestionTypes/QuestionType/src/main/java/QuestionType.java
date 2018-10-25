/***************************************************************************
* Author: Janek Joyce
* Last Updated: 24/10/2018
* Purpose: Abstract class used by question types, e.g multiple choice, short answer etc
*          This code is intended for the 2018 semester 1 SEC assignment
***************************************************************************/

import javafx.scene.layout.GridPane;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.scene.control.Label;

public abstract class QuestionType
{
    private String name;
   // public LinkedBlockingQueue<Integer> score = new LinkedBlockingQueue<Integer>();

    public QuestionType(String inName)
    {
        this.name = inName;
    }

    public String getName()
    {
        return this.name;
    }

    /*public LinkedBlockingQueue<Integer> getScore()
    {
        return score;
    }*/

    public abstract Question makeQuestion(Object... args);
    protected abstract GridPane makePreview(String desc);
}
