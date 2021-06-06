package Vehicles;

import Main.Main;
import Tiles.CarPassable;
import Tiles.MovementSide;
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
    private LinkedList<Tile> pastLocations;

    volatile double speed;
    private boolean isAlive;


    Vehicle(String type, String model, int buildYear, Tile[][] map, Tile entryPoint, Tile exitPoint)
    {
        this.manufacturer = type;
        this.model = model;
        this.buildYear = buildYear;
        this.map = map;
        this.VehicleID=++ID;
        this.entryPoint=entryPoint;
        this.exitPoint = exitPoint;
        this.currentPosition = entryPoint;
        this.xCoordinate = currentPosition.getxCoordinate();
        this.yCoordinate = currentPosition.getyCoordinate();
        speed = 1;
        pastLocations = new LinkedList<>();


    }
    public void updateSpeed(double newSpeedLimit)
    {
        Random rnd = new Random();

        speed = rnd.nextDouble()*newSpeedLimit;
    }

    private LinkedList<CarPassable> getAdjacentTracks()
    {
        LinkedList<CarPassable> adjacentTracks;
        synchronized (map)
        {
            adjacentTracks = new LinkedList<>();

            int upperX = currentPosition.getxCoordinate();
            int upperY = currentPosition.getyCoordinate() + 1;
            if ((upperX >= 0 && upperX < 30) && (upperY >= 0 && upperY < 30))
            {
                if (map[upperX][upperY] instanceof CarPassable
                        && (((CarPassable) map[upperX][upperY]).getMovementSide() == this.getMovementSide())
                        && !map[upperX][upperY].isTaken() && !pastLocations.contains(map[upperX][upperY]))
                    adjacentTracks.add((CarPassable) map[upperX][upperY]);
            }

            int rightX = currentPosition.getxCoordinate() + 1;
            int rightY = currentPosition.getyCoordinate();
            if ((rightX >= 0 && rightX < 30) && (rightY >= 0 && rightY < 30))
            {
                if (map[rightX][rightY] instanceof CarPassable
                        && (((CarPassable) map[rightX][rightY]).getMovementSide() == this.getMovementSide())
                        && !map[rightX][rightY].isTaken() && !pastLocations.contains(map[rightX][rightY]))
                    adjacentTracks.add((CarPassable) map[rightX][rightY]);
            }

            int leftX = currentPosition.getxCoordinate() - 1;
            int leftY = currentPosition.getyCoordinate();
            if ((leftX >= 0 && leftX < 30) && (leftX >= 0 && leftY < 30))
            {
                if (map[leftX][leftY] instanceof CarPassable
                        && (((CarPassable) map[leftX][leftY]).getMovementSide() == this.getMovementSide())
                        && !map[leftX][leftY].isTaken() && !pastLocations.contains(map[leftX][leftY]))
                    adjacentTracks.add((CarPassable) map[leftX][leftY]);
            }

            int bottomX = currentPosition.getxCoordinate();
            int bottomY = currentPosition.getyCoordinate() - 1;
            if ((bottomX >= 0 && bottomX < 30) && (bottomY >= 0 && bottomY < 30))
            {
                if (map[bottomX][bottomY] instanceof CarPassable
                        && (((CarPassable) map[bottomX][bottomY]).getMovementSide()) == this.getMovementSide()
                        && !map[bottomX][bottomY].isTaken() && !pastLocations.contains(map[bottomX][bottomY]))
                    adjacentTracks.add((CarPassable) map[bottomX][bottomY]);
            }
        }
        return adjacentTracks;

    }

    public void setAlive(boolean alive)
    {
        isAlive = alive;
    }

    @Override
    public void run()
    {
        while(isAlive)
        {
            try
            {
                Thread.sleep((long) speed*1000);
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

    private synchronized void move()
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
        int oldX = currentPosition.getxCoordinate(); int oldY = currentPosition.getyCoordinate();



        Platform.runLater(() -> map[oldX][oldY].putContent(""));
        this.xCoordinate = newX; this.yCoordinate = newY;
        this.currentPosition = map[newX][newY];
        pastLocations.add(currentPosition);
        Platform.runLater(() -> map[xCoordinate][yCoordinate].putContent(this.toString()));

        if(exitPoint.getxCoordinate()==newX && exitPoint.getyCoordinate()==newY)
        {
            //TODO make it draw itself on the last tile
            //TODO make cars on railway crossings check whether there is an incoming train
            isAlive = false;
            Platform.runLater(() -> map[newX][newY].putContent(""));
        }
    }

    public MovementSide getMovementSide()
    {
        return ((CarPassable)this.entryPoint).getMovementSide();
    }
}
