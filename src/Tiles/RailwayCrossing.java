package Tiles;

import javafx.scene.paint.Color;

public class RailwayCrossing extends TrainTrack implements TrainPassable, CarPassable
{
    Color carTrackColor = Color.BLACK;
    MovementSide movementSide;

    public RailwayCrossing(String tileContent, double x, double y, double width, double height, MovementSide movementSide)
    {
        super(tileContent, x, y, width, height);
        rectangle.setStroke(carTrackColor);
        rectangle.setFill(carTrackColor);
        this.movementSide = movementSide;
    }
}