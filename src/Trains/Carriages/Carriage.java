package Trains.Carriages;

import Trains.Connectable;

public abstract class Carriage implements Connectable
{
    private static int ID;

    private int carriageID;
    private int carriageLength;

    protected Carriage(int carriageLength)
    {
        carriageID=ID++;
        this.carriageLength = carriageLength;
    }

    public int getCarriageID()
    {
        return carriageID;
    }

    public int getCarriageLength()
    {
        return carriageLength;
    }
}
