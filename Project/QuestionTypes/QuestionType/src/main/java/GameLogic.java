/***************************************************************************
* Author: Janek Joyce
* Last Updated: 24/10/2018
* Purpose: Used to keep track of which question's turn it is, I am using this to remove complexity from the GUI builder's side
*          This code is intended for the 2018 semester 1 SEC assignment
***************************************************************************/

import java.util.concurrent.LinkedBlockingQueue;

public class GameLogic
{
    public GameLogic(){}
    public static volatile LinkedBlockingQueue<Integer> turn = new LinkedBlockingQueue<Integer>();
    public static volatile LinkedBlockingQueue<Integer> score = new LinkedBlockingQueue<Integer>();
    public static volatile LinkedBlockingQueue<Integer> endGame = new LinkedBlockingQueue<Integer>();
    public static volatile Object key = new Object();
}
