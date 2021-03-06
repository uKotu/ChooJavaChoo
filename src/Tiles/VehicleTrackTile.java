package Tiles;

import javafx.scene.paint.Color;

public class VehicleTrackTile extends Tile implements VehiclePassable
{
    private final MovementSide movementSide;
    private Color carTrackColor = Color.LIGHTBLUE;

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

}
