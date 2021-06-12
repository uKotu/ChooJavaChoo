package Tiles;

import javafx.scene.paint.Color;

public class RailwayCrossingTile extends TrainTrackTile implements TrainPassable, VehiclePassable
{
    private Color carTrackColor = Color.BLACK;
    private final MovementSide movementSide;

    public RailwayCrossingTile(String tileContent, double x, double y, double width, double height, MovementSide movementSide)
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

    public void setElectricityOn()
    {
        rectangle.setFill(Color.LIGHTGOLDENRODYELLOW);
    }
    public void setElectricityOff()
    {
        rectangle.setFill(carTrackColor);
    }
}