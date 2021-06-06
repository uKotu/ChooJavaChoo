package Tiles;

import javafx.scene.paint.Color;

public class CarTrack extends Tile implements CarPassable
{
    //insert movement direction
    MovementSide movementSide;
    Color carTrackColor = Color.LIGHTBLUE;


    public CarTrack(String tileContent, double x, double y, double width, double height, MovementSide movementSide)
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
}
