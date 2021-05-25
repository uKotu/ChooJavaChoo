package Tiles;

import javafx.scene.paint.Color;

public class TrainTrack extends Tile
{
    Color trainTrackColor = Color.LIGHTGRAY;

    public TrainTrack(String tileContent, double x, double y, double width, double height)
    {
        super(tileContent, x, y, width, height);
        rectangle.setStroke(trainTrackColor);
        rectangle.setFill(trainTrackColor);
    }
}
