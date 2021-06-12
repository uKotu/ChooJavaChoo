package Util;

public class Coordinates
{
    int x,y;

    public Coordinates(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public static int calculateDistance(Coordinates currentCoordinates, Coordinates destinationCoordinates)
    {
        return Math.abs(currentCoordinates.getX()-destinationCoordinates.getX())
                + Math.abs(currentCoordinates.getY()-destinationCoordinates.getY());
    }


}
