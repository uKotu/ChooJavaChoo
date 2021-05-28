package Trains.Locomotives;

import Trains.Connectable;
import Trains.Power;

public abstract class Locomotive implements Connectable
{
    static int locomotiveCount;
    int locomotiveID;
    int power;
    Power powerType;
    int xCoordinate, yCoordinate;

    Locomotive(int power, Power powerType)
    {
        this.locomotiveID=++locomotiveCount;
        this.power = power;


    }

    public int getxCoordinate()
    {
        return xCoordinate;
    }

    public int getyCoordinate()
    {
        return yCoordinate;
    }

    @Override
    public void setxCoordinate(int xCoordinate)
    {
        this.xCoordinate = xCoordinate;
    }

    @Override
    public void setyCoordinate(int yCoordinate)
    {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public String toString()
    {
        return locomotiveID+"";
    }
}
