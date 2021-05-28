package Trains;

import Tiles.Tile;
import Tiles.TrainPassable;
import Tiles.TrainTrack;
import Trains.Carriages.Carriage;
import Trains.Locomotives.Locomotive;
import Util.Coordinates;
import Util.RailroadStation;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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


    @Override
    public void run()
    {
        switch (currentState)
        {
            case Parked ->
                    {

                    }
            case Normal ->
                    {
                        synchronized (map)
                        {
                            if(currentStation.nextInQueue(this) && currentStation.greenLight())
                            {
                                move();
                            }

                        }
                    }
            case ExitingParking ->
                    {

                    }
        }

    }
    private synchronized void move()
    {
        int newX, newY;
        Locomotive trainHead = (Locomotive) parts.get(0);
        RailroadStation nextStation = null;
        for (var x: stations)
        {
            if (x.getName()==nextStationName()+"")
                nextStation = x;
            break;
        }

        HashMap<Integer,Tile> tileDistanceMap = new HashMap<>();
        for (var track : getAdjacentTracks())
        {
            int distance = Coordinates.calculateDistance(
                    new Coordinates(track.getxCoordinate(),track.getyCoordinate()),
                    new Coordinates(nextStation.getxCoordinate(), nextStation.getyCoordinate()));
            tileDistanceMap.put(distance,track);
        }
        var nextTileValueEntry = Collections.min(tileDistanceMap.entrySet(), Map.Entry.comparingByKey());
        var nextTile = nextTileValueEntry.getValue();
        newX = nextTile.getxCoordinate(); newY = nextTile.getyCoordinate();


        for(int i = parts.size();i>0;i--)
        {
            var rear = parts.get(i-1);
            var front = parts.get(i-2);

            //clear the rear tile
            map[rear.getxCoordinate()][rear.getyCoordinate()].putContent("");

            rear.setxCoordinate(front.getxCoordinate());
            rear.setyCoordinate(front.getyCoordinate());
            map[rear.getxCoordinate()][rear.getyCoordinate()].putContent(rear.toString());

        }
        //move the train leading part to new coordinates
        var front = parts.getFirst();
        front.setxCoordinate(newX); front.setyCoordinate(newY);
        map[front.getxCoordinate()][front.getyCoordinate()].putContent(front.toString());

    }
}
