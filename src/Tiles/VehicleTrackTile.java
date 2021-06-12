package Tiles;

import javafx.scene.paint.Color;

public class VehicleTrackTile extends Tile implements CarPassable
{
    //insert movement direction
    MovementSide movementSide;
    Color carTrackColor = Color.LIGHTBLUE;

    private double allowedSpeed;


    public VehicleTrackTile(String tileContent, double x, double y, double width, double height, MovementSide movementSide)
    {
        super(tileContent, x, y, width, height);
        rectangle.setStroke(carTrackColor);
        rectangle.setFill(carTrackColor);
        this.movementSide = movementSide;
    }

    @Override
    public MovementSide getMovementSide()
    {
        return movementSide;
    }

    public double getAllowedSpeed()
    {
        return allowedSpeed;
    }

    public void setAllowedSpeed(double allowedSpeed)
    {
        this.allowedSpeed = allowedSpeed;
    }

}
