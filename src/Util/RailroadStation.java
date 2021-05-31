package Util;

import Tiles.StationTile;
import Tiles.TrainTrack;
import Trains.Train;

import java.util.ArrayDeque;
import java.util.LinkedList;

public class RailroadStation
{
    LinkedList<StationTile> tilesTaken;
    ArrayDeque<Train> trainQueue;
    LinkedList<TrainTrack> possibleExitCoordinates;
    LinkedList<RailPath> railPaths;
    String name;

    RailroadStation(String name, LinkedList<StationTile> tilesTaken, LinkedList<TrainTrack> possibleExitCoordinates)
    {
        this.name = name;
        this.tilesTaken = tilesTaken;
        this.possibleExitCoordinates = possibleExitCoordinates;
        this.trainQueue = new ArrayDeque<>();
        this.railPaths = new LinkedList<>();

    }
    public boolean nextInQueue(Train train)
    {
        return trainQueue.peek() == train;
    }
    public boolean greenLight()
    {
        Train nextTrain = trainQueue.peek();
        for(var x : railPaths)
        {

                if (x.stationsConnected.contains(nextTrain.nextStationName() + ""))
                {
                    synchronized (Train.class)
                    {
                        if (x.isPathClear()) //|| x.trainIsMovingAway()
                        {
                            return true;
                        }
                    }
                }
        }
        return false;
    }
    public int getxCoordinate()
    {
        return tilesTaken.get(0).getxCoordinate();
    }
    public int getyCoordinate()
    {
        return tilesTaken.get(0).getyCoordinate();
    }

    public LinkedList<TrainTrack> getPossibleExitCoordinates()
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

}
