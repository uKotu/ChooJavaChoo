package Util;

import java.io.Serializable;
import java.util.LinkedList;

public class MovementHistory implements Serializable
{
    private LinkedList<MovementHistoryUnit> movementHistoryList;
    private final String trainDescription;

    public MovementHistory( LinkedList<MovementHistoryUnit> movementHistoryList, String trainDescription)
    {
        this.movementHistoryList =  movementHistoryList;
        this.trainDescription = trainDescription;
    }

    @Override
    public String toString()
    {
        String outputString = trainDescription + "\n";
        for(var x: movementHistoryList)
        {
            outputString+=x;
        }

        return outputString;
    }
}
