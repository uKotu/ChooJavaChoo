package Tiles;

import javafx.scene.paint.Color;

public class TrainTrackTile extends Tile implements TrainPassable
{
    private final Color trainTrackColor = Color.LIGHTGRAY;

    public TrainTrackTile(String tileContent, double x, double y, double width, double height)
    {
        super(tileContent, x, y, width, height);
        rectangle.setStroke(trainTrackColor);
        rectangle.setFill(trainTrackColor);
    }

    public void setElectricityOn()
    {
        rectangle.setFill(Color.LIGHTGOLDENRODYELLOW);
    }
    public void setElectricityOff()
    {
        rectangle.setFill(trainTrackColor);
    }

}