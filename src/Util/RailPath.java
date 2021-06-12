package Util;

import Tiles.Tile;
import Trains.Train;

import java.util.LinkedList;

public class RailPath
{
    LinkedList<Tile> tilesOnPath;
    String stationsConnected;
    final Tile[][] map;

    public RailPath(String stationsConnected, Tile[][] map)
    {
        this.stationsConnected = stationsConnected;
        tilesOnPath = new LinkedList<>();
        this.map = map;
    }

    public void addTile(Tile tile)
    {
        tilesOnPath.add(tile);
    }
    public boolean isPathClear()
    {
        synchronized (map) // sync over train.class
        {
            for(Tile x: tilesOnPath)
            {
                if(x.isTaken())
                    return false;
            }
            return true;
        }
    }

    public boolean containsTile(Tile tile)
    {
        return tilesOnPath.contains(tile);
    }
}
