package Tiles;

import javafx.scene.paint.Color;

public class CarTrack extends Tile
{
    //insert movement direction
    Color carTrackColor = Color.LIGHTBLUE;

    public CarTrack(String tileContent, double x, double y, double width, double height)
    {
        super(tileContent, x, y, width, height);
        rectangle.setStroke(carTrackColor);
        rectangle.setFill(carTrackColor);
    }
}
