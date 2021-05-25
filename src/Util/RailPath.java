package Util;

import Tiles.Tile;

import java.util.LinkedList;

public class RailPath
{

    LinkedList<Tile> tilesOnPath;
    String stationsConnected;
    Tile[][] map;

    RailPath(String stationsConnected)
    {
        this.stationsConnected = stationsConnected;
        tilesOnPath = new LinkedList<>();
    }

    public void addTile(Tile tile, Tile[][] map)
    {
        tilesOnPath.add(tile);
    }
    public boolean isPathClear()
    {
        synchronized (map)
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
