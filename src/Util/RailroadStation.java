package Util;

import Tiles.StationTile;
import Tiles.TrainTrack;
import Trains.Train;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class RailroadStation
{
    LinkedList<StationTile> tilesTaken;
    PriorityQueue<Train> trainQueue;
    LinkedList<TrainTrack> possibleExitCoordinates;
    LinkedList<RailPath> railPaths;
    String name;

    RailroadStation(String name, LinkedList<StationTile> tilesTaken, LinkedList<TrainTrack> possibleExitCoordinates)
    {
        this.name = name;
        this.tilesTaken = tilesTaken;
        this.possibleExitCoordinates = possibleExitCoordinates;
        trainQueue = new PriorityQueue<>();
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
                    synchronized (x.map)
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

    public LinkedList<TrainTrack> getPossibleExitCoordinates()
    {
        return possibleExitCoordinates;
    }

    public String getName()
    {
        return name;
    }
    public void addTrainToQueue(Train train)
    {
        this.trainQueue.add(train);
    }

}
