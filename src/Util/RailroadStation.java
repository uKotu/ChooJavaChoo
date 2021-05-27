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

    public String getName()
    {
        return name;
    }
    public void addTrainToQueue(Train train)
    {
        this.trainQueue.add(train);
    }
}
