package Tiles;

import Trains.Train;
import javafx.scene.paint.Color;

public class StationTile extends Tile implements TrainPassable
{
    private Color stationColor = Color.ORANGE;

    public StationTile(String tileContent, double x, double y, double width, double height)
    {
        super(tileContent, x, y, width, height);
        rectangle.setStroke(stationColor);
        rectangle.setFill(stationColor);
    }
    public void setElectricityOn()
    {
        rectangle.setFill(Color.LIGHTGOLDENRODYELLOW);
    }
    public void setElectricityOff()
    {
        rectangle.setFill(stationColor);
    }
}
