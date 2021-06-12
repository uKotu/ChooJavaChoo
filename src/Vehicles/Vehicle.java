package Vehicles;
import Main.Main;
import Tiles.VehiclePassable;
import Tiles.MovementSide;
import Tiles.Tile;
import Util.Coordinates;
import Util.RailwayCrossing;
import javafx.application.Platform;
import java.util.*;
import java.util.logging.Level;

public abstract class Vehicle implements Runnable
{
    private static int ID = 0;
    private final String manufacturer, model;
    private final int buildYear;
    private Tile entryPoint, exitPoint, currentPosition;
    private final Tile[][] map;
    protected final int VehicleID;
    private int xCoordinate, yCoordinate;
    private LinkedList<Tile> pastLocations;
    private RailwayCrossing railwayCrossingOnPath;

    volatile double speed;
    private boolean isAlive;


    Vehicle(String type, String model, int buildYear, Tile[][] map, Tile entryPoint, Tile exitPoint, double allowedSpeedOnRoad, RailwayCrossing railwayCrossing)
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
        this.railwayCrossingOnPath = railwayCrossing;

        Random rnd = new Random();
        speed = 1 + rnd.nextDouble()*allowedSpeedOnRoad;
        pastLocations = new LinkedList<>();
    }

    private LinkedList<VehiclePassable> getAdjacentTracks()
    {
        LinkedList<VehiclePassable> adjacentTracks;
        synchronized (map)
        {
            adjacentTracks = new LinkedList<>();

            int upperX = currentPosition.getxCoordinate();
            int upperY = currentPosition.getyCoordinate() + 1;
            if ((upperX >= 0 && upperX < 30) && (upperY >= 0 && upperY < 30))
            {
                if (map[upperX][upperY] instanceof VehiclePassable
                        && (((VehiclePassable) map[upperX][upperY]).getMovementSide() == this.getMovementSide())
                        && !map[upperX][upperY].isTaken() && !pastLocations.contains(map[upperX][upperY]))
                    adjacentTracks.add((VehiclePassable) map[upperX][upperY]);
            }

            int rightX = currentPosition.getxCoordinate() + 1;
            int rightY = currentPosition.getyCoordinate();
            if ((rightX >= 0 && rightX < 30) && (rightY >= 0 && rightY < 30))
            {
                if (map[rightX][rightY] instanceof VehiclePassable
                        && (((VehiclePassable) map[rightX][rightY]).getMovementSide() == this.getMovementSide())
                        && !map[rightX][rightY].isTaken() && !pastLocations.contains(map[rightX][rightY]))
                    adjacentTracks.add((VehiclePassable) map[rightX][rightY]);
            }

            int leftX = currentPosition.getxCoordinate() - 1;
            int leftY = currentPosition.getyCoordinate();
            if ((leftX >= 0 && leftX < 30) && (leftX >= 0 && leftY < 30))
            {
                if (map[leftX][leftY] instanceof VehiclePassable
                        && (((VehiclePassable) map[leftX][leftY]).getMovementSide() == this.getMovementSide())
                        && !map[leftX][leftY].isTaken() && !pastLocations.contains(map[leftX][leftY]))
                    adjacentTracks.add((VehiclePassable) map[leftX][leftY]);
            }

            int bottomX = currentPosition.getxCoordinate();
            int bottomY = currentPosition.getyCoordinate() - 1;
            if ((bottomX >= 0 && bottomX < 30) && (bottomY >= 0 && bottomY < 30))
            {
                if (map[bottomX][bottomY] instanceof VehiclePassable
                        && (((VehiclePassable) map[bottomX][bottomY]).getMovementSide()) == this.getMovementSide()
                        && !map[bottomX][bottomY].isTaken() && !pastLocations.contains(map[bottomX][bottomY]))
                    adjacentTracks.add((VehiclePassable) map[bottomX][bottomY]);
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
        pastLocations.add(currentPosition);
        Platform.runLater(() -> map[xCoordinate][yCoordinate].putContent(this.toString()));
        while(isAlive)
        {
            try
            {
                Thread.sleep((long) speed*1000);
                synchronized (map)
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

    //almost the same logic as the train movement; the car gets spawned at a location
    //checks its adjacent tiles to ones to which it can move, if any are available
    //AND IT GETS THEM CLOSER TO THE EXIT, then move to that tile
    private synchronized void move()
    {
        if(currentPosition==exitPoint)
        {
            isAlive = false;
            Platform.runLater(() -> map[currentPosition.getxCoordinate()][currentPosition.getyCoordinate()].putContent(""));
            return;
        }
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

        if(railwayCrossingOnPath.getTilesTaken().contains(nextTile))
        {
            if(!railwayCrossingOnPath.isGreenLight())
            {
                return;
            }
        }

        newX = nextTile.getxCoordinate(); newY = nextTile.getyCoordinate();
        int oldX = currentPosition.getxCoordinate(); int oldY = currentPosition.getyCoordinate();

        Platform.runLater(() -> map[oldX][oldY].putContent(""));
        this.xCoordinate = newX; this.yCoordinate = newY;
        this.currentPosition = map[newX][newY];
        pastLocations.add(currentPosition);
        Platform.runLater(() -> map[xCoordinate][yCoordinate].putContent(this.toString()));

    }

    public Tile getEntryPoint()
    {
        return entryPoint;
    }

    public MovementSide getMovementSide()
    {
        return ((VehiclePassable)this.entryPoint).getMovementSide();
    }
}
