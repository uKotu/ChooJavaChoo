package Tiles;

import javafx.scene.paint.Color;

public class TrainTrackTile extends Tile implements TrainPassable
{
    Color trainTrackColor = Color.LIGHTGRAY;

    public TrainTrackTile(String tileContent, double x, double y, double width, double height)
    {
        super(tileContent, x, y, width, height);
        rectangle.setStroke(trainTrackColor);
        rectangle.setFill(trainTrackColor);
    }
}