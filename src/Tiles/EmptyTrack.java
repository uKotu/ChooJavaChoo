package Tiles;

import javafx.scene.paint.Color;

public class EmptyTrack extends Tile
{
    Color emptyTrackColor = Color.WHITE;

    public EmptyTrack(String tileContent, double x, double y, double width, double height)
    {
        super(tileContent, x, y, width, height);
        rectangle.setStroke(emptyTrackColor);
        rectangle.setFill(emptyTrackColor);
    }
}
