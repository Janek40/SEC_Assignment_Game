public abstract class QuestionType
{
    private String name;
 //   protected GridPane root;
    
    public QuestionType(String inName)
    {
        this.name = inName;
//	this.root = new GridPane();
    }

    public String getName()
    {
        return this.name;
    }

  //  public GridPane getRoot()
    //{
      //  return this.root;
    //}

    public abstract Question makeQuestion(Object... args);
    //public abstract void restartQuiz();
    //public abstract void returnToQuizSelection();
}
