package Trains;

public class ElectricBarrier implements Connectable
{
    private int xCoordinate, yCoordinate;

    @Override
    public int getxCoordinate()
    {
        return xCoordinate;
    }

    @Override
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
        return "!";
    }
}
