package Util;

import Tiles.StationTile;
import Tiles.Tile;
import Tiles.TrainTrackTile;
import Trains.Train;

import java.util.*;

public class RailroadStation
{
    private final LinkedList<StationTile> tilesTaken;
    private ArrayDeque<Train> trainQueue;
    private final LinkedList<TrainTrackTile> possibleExitCoordinates;
    LinkedList<RailPath> railPaths;
    private final String name;
    private LinkedList<Train> trains;


    RailroadStation(String name, LinkedList<StationTile> tilesTaken, LinkedList<TrainTrackTile> possibleExitCoordinates, LinkedList<Train> trains)
    {
        this.name = name;
        this.tilesTaken = tilesTaken;
        this.possibleExitCoordinates = possibleExitCoordinates;
        this.trainQueue = new ArrayDeque<>();
        this.railPaths = new LinkedList<>();
        this.trains = trains;

    }
    public boolean nextInQueue(Train train)
    {
        return trainQueue.peek() == train;
    }
    public synchronized boolean greenLight()
    {
        Train nextTrain = trainQueue.peek();
        for(var x : railPaths)
        {
                if (x.stationsConnected.contains(nextTrain.nextStationName() + ""))
                {
                    synchronized (Train.class)
                    {
                        if (x.isPathClear() || noIncomingTrainsOnTheRailPath(x)) //|| x.trainIsMovingAway()
                        {
                            trainQueue.removeFirst();
                            return true;
                        }
                    }
                }
        }
        return false;
    }
    private boolean noIncomingTrainsOnTheRailPath(RailPath railPath)
    {
        //check all trains which are on the railpath for their next station
        //if their next station is the current station, there is an incoming train
        synchronized (railPath.map)
        {
            for (Train train : trains)
            {
                for (Tile tile : railPath.tilesOnPath)
                {
                    if (tile.getxCoordinate() == train.getTrainHeadXCoordinate() && tile.getyCoordinate() == train.getTrainHeadYCoordinate())
                    {
                        if ((train.nextStationName() + "").equals(this.name))
                        {
                            return false;
                        }
                    }

                }
            }
        }
        return true;
    }
    public int getxCoordinate()
    {
        return tilesTaken.get(0).getxCoordinate();
    }
    public int getyCoordinate()
    {
        return tilesTaken.get(0).getyCoordinate();
    }

    public LinkedList<TrainTrackTile> getPossibleExitCoordinates()
    {
        return possibleExitCoordinates;
    }


    public String getName() {
        return name;
    }
    public void addTrainToQueue(Train train)
    {
        this.trainQueue.add(train);
    }

    public Tile findClosestEntrance(Train train)
    {
        HashMap<Integer, Tile> tileDistanceMap = new HashMap<>();
        for (var track : tilesTaken)
        {
            int distance = Coordinates.calculateDistance(
                    new Coordinates(track.getxCoordinate(),track.getyCoordinate()),
                    new Coordinates(train.getTrainHeadXCoordinate(), train.getTrainHeadYCoordinate()));
            tileDistanceMap.put(distance,track);
        }
        var nextTileValueEntry = Collections.min(tileDistanceMap.entrySet(), Map.Entry.comparingByKey());
        return nextTileValueEntry.getValue();
    }

}
