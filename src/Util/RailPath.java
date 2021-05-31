package Util;

import Tiles.Tile;
import Trains.Train;

import java.util.LinkedList;

public class RailPath
{
    LinkedList<Tile> tilesOnPath;
    String stationsConnected;
    Tile[][] map;

    public RailPath(String stationsConnected)
    {
        this.stationsConnected = stationsConnected;
        tilesOnPath = new LinkedList<>();
    }

    public void addTile(Tile tile)
    {
        tilesOnPath.add(tile);
    }
    public boolean isPathClear()
    {
        synchronized (Train.class)
        {
            for(Tile x: tilesOnPath)
            {
                if(x.isTaken())
                    return false;
            }
            return true;
        }
    }
}
