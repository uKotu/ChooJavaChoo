package Tiles;

import javafx.scene.paint.Color;

public class RailwayCrossing extends Tile
{


    Color carTrackColor = Color.BLACK;

    public RailwayCrossing(String tileContent, double x, double y, double width, double height)
    {
        super(tileContent, x, y, width, height);
        rectangle.setStroke(carTrackColor);
        rectangle.setFill(carTrackColor);
    }
}
