import java.util.concurrent.LinkedBlockingQueue;

public class GameLogic
{
    public GameLogic(){}
    public static volatile LinkedBlockingQueue<Integer> turn = new LinkedBlockingQueue<Integer>();
    public static volatile Object key = new Object();
}
