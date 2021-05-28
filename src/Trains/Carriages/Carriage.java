package Trains.Carriages;

import Trains.Connectable;

public abstract class Carriage implements Connectable
{
    private static int ID;

    private int carriageID;
    private int carriageLength;


    private int xCoordinate, yCoordinate;


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

    public int getxCoordinate() { return xCoordinate;}
    public int getyCoordinate() { return yCoordinate;}

}
