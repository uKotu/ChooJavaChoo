package Vehicles;

import Main.Main;
import Tiles.CarPassable;
import Tiles.Tile;
import Util.Coordinates;
import javafx.application.Platform;

import java.util.*;
import java.util.logging.Level;

public abstract class Vehicle implements Runnable
{
    private static int ID = 0;
    private final String manufacturer, model;
    private final int buildYear;
    private Tile entryPoint, exitPoint, currentPosition;
    private Tile[][] map;
    protected final int VehicleID;
    private int xCoordinate, yCoordinate;


    volatile double speed;

    private boolean isAlive;

    Vehicle(String type, String model, int buildYear, Tile[][] map)
    {
        this.manufacturer = type;
        this.model = model;
        this.buildYear = buildYear;
        this.map = map;
        this.VehicleID=++ID;

    }
    public void updateSpeed(double newSpeedLimit)
    {
        Random rnd = new Random();

        speed = rnd.nextDouble()*newSpeedLimit;
    }

    private LinkedList<CarPassable> getAdjacentTracks()
    {
        LinkedList<CarPassable> adjacentTracks = new LinkedList<>();
            if (map[currentPosition.getxCoordinate() + 1][currentPosition.getyCoordinate()] instanceof CarPassable
                    && !map[currentPosition.getxCoordinate() + 1][currentPosition.getyCoordinate()].isTaken())
                adjacentTracks.add((CarPassable) map[currentPosition.getxCoordinate() + 1][currentPosition.getyCoordinate()]);

            if (map[currentPosition.getxCoordinate()][currentPosition.getyCoordinate() + 1] instanceof CarPassable
                    && !map[currentPosition.getxCoordinate()][currentPosition.getyCoordinate() + 1].isTaken())
                adjacentTracks.add((CarPassable) map[currentPosition.getxCoordinate()][currentPosition.getyCoordinate() + 1]);

            if (map[currentPosition.getxCoordinate() - 1][currentPosition.getyCoordinate()] instanceof CarPassable
                    && !map[currentPosition.getxCoordinate() - 1][currentPosition.getyCoordinate()].isTaken())
                adjacentTracks.add((CarPassable) map[currentPosition.getxCoordinate() - 1][currentPosition.getyCoordinate()]);

            if (map[currentPosition.getxCoordinate()][currentPosition.getyCoordinate() - 1] instanceof CarPassable
                    && !map[currentPosition.getxCoordinate()][currentPosition.getyCoordinate() - 1].isTaken())
                adjacentTracks.add((CarPassable) map[currentPosition.getxCoordinate()][currentPosition.getyCoordinate() - 1]);

        return adjacentTracks;

}


    @Override
    public void run()
    {
        while(isAlive)
        {
            try
            {
                synchronized (Vehicle.class)
                {
                    move();
                }

            }
            catch (Exception ex)
            {
                Main.logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    private void move()
    {
        int newX, newY;
        HashMap<Integer,Tile> tileDistanceMap = new HashMap<>();
        var freeAdjacentTracks = getAdjacentTracks();
        if(freeAdjacentTracks.size()==0)
            return;
        for (var track : getAdjacentTracks())
        {
            int distance = Coordinates.calculateDistance(
                    new Coordinates(((Tile)track).getxCoordinate(),((Tile)track).getyCoordinate()),
                    new Coordinates(exitPoint.getxCoordinate(), exitPoint.getyCoordinate()));
            tileDistanceMap.put(distance, (Tile) track);
        }
        var nextTileValueEntry = Collections.min(tileDistanceMap.entrySet(), Map.Entry.comparingByKey());
        var nextTile = nextTileValueEntry.getValue();

        newX = nextTile.getxCoordinate(); newY = nextTile.getyCoordinate();

        if(exitPoint.getxCoordinate()==newX && exitPoint.getyCoordinate()==newY)
        {
            isAlive = false;
        }
        Platform.runLater(() -> map[xCoordinate][yCoordinate].putContent(""));
        this.xCoordinate = newX; this.yCoordinate = newY;
        Platform.runLater(() -> map[xCoordinate][yCoordinate].putContent(this.toString()));

    }
}
