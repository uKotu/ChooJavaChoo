package Util;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class MovementHistoryUnit implements Serializable
{
    LocalDateTime date;
    int xCoordinate, yCoordinate;
    public MovementHistoryUnit(int xCoordinate, int yCoordinate)
    {
        date = LocalDateTime.now();
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public String toString()
    {
        return
                "date=" + date +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate + "\n";
    }

    public int getxCoordinate()
    {
        return xCoordinate;
    }

    public int getyCoordinate()
    {
        return yCoordinate;
    }
}
