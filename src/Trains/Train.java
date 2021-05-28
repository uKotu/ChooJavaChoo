package Trains;

import Tiles.Tile;
import Tiles.TrainPassable;
import Tiles.TrainTrack;
import Trains.Locomotives.Locomotive;
import Util.Coordinates;
import Util.RailroadStation;

import java.util.LinkedList;

public class Train implements Runnable
{
    LinkedList<Connectable> parts;
    double trainSpeed;
    String route;
    int positionOnRoute;
    RailroadStation currentStation;
    TrainState currentState;
    Tile[][] map;
    LinkedList<RailroadStation> stations;

    public Train(LinkedList<Connectable> trainPieces, double trainSpeed, String route, Tile[][] map, LinkedList<RailroadStation> stations)
    {
        parts = trainPieces;
        this.trainSpeed = trainSpeed;
        this.route = route;
        currentState = TrainState.Parked;
        positionOnRoute = 0;
        this.stations = stations;

        for(var x : stations) //initialize first station
        {
            if(x.getName()==(route.charAt(0)+""))
            {
                currentStation=x;
                break;

            }
        }
    }

    private LinkedList<TrainTrack> getAdjacentTracks()
    {
        LinkedList<TrainTrack> adjacentTracks = new LinkedList<>();
        Locomotive trainHead = (Locomotive) parts.get(0); //first part of the train must be a locomotive
        if (currentState==TrainState.Parked) //if the train is parked, its adjacent tracks are the stations exitTracks
            return currentStation.getPossibleExitCoordinates();
        else
            if(map[trainHead.getxCoordinate() + 1 ][trainHead.getyCoordinate()] instanceof TrainPassable)
                adjacentTracks.add((TrainTrack)map[trainHead.getxCoordinate() + 1 ][trainHead.getyCoordinate()]);
            if(map[trainHead.getxCoordinate()][trainHead.getyCoordinate() + 1] instanceof TrainPassable)
                adjacentTracks.add((TrainTrack)map[trainHead.getxCoordinate()][trainHead.getyCoordinate() + 1]);
            if(map[trainHead.getxCoordinate() - 1][trainHead.getyCoordinate()] instanceof TrainPassable)
                adjacentTracks.add((TrainTrack)map[trainHead.getxCoordinate() - 1][trainHead.getyCoordinate()]);
            if(map[trainHead.getxCoordinate()][trainHead.getyCoordinate() - 1] instanceof TrainPassable)
                adjacentTracks.add((TrainTrack)map[trainHead.getxCoordinate()][trainHead.getyCoordinate() - 1]);

            return adjacentTracks;
    }

    public char nextStationName() throws ArrayIndexOutOfBoundsException
    {
        return route.toCharArray()[positionOnRoute + 1];
    }

    private synchronized void move()
    {
        int newX, newY;
    }
    @Override
    public void run()
    {
        switch (currentState)
        {
            case Parked ->
                    {
                        synchronized (map)
                        {
                            if(currentStation.nextInQueue(this) && currentStation.greenLight())
                            {
                                move();
                            }

                        }
                    }
            case Normal ->
                    {
                        move();
                    }
            case ExitingParking ->
                    {

                    }
        }

    }
}
