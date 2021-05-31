package Trains;

import Main.Main;
import Tiles.Tile;
import Tiles.TrainPassable;
import Tiles.TrainTrack;
import Trains.Carriages.Carriage;
import Trains.Locomotives.Locomotive;
import Util.Coordinates;
import Util.RailroadStation;
import javafx.application.Platform;

import java.util.*;
import java.util.logging.Level;

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
    boolean trainAlive;
    int trainPartsWhichHaveLeftThePlatform;

    public Train(LinkedList<Connectable> trainPieces, double trainSpeed, String route, Tile[][] map, LinkedList<RailroadStation> stations)
    {
        trainAlive = true;
        parts = trainPieces;
        this.trainSpeed = trainSpeed;
        this.route = route;
        currentState = TrainState.Parked;
        positionOnRoute = 0;
        this.stations = stations;
        trainPartsWhichHaveLeftThePlatform = 0;
        this.map = map;

        for(var x : stations) //initialize first station
        {
            if(x.getName().equals(route.charAt(0)+""))
            {
                currentStation=x;
                x.addTrainToQueue(this);
                break;

            }
        }
    }

    private List<TrainTrack> getAdjacentTracks()
    {
        LinkedList<TrainTrack> adjacentTracks = new LinkedList<>();
        Locomotive trainHead = (Locomotive) parts.get(0); //first part of the train must be a locomotive
        if (currentState==TrainState.Parked || this.trainPartsWhichHaveLeftThePlatform==0) //if the train is parked, its adjacent tracks are the stations exitTracks
            return currentStation.getPossibleExitCoordinates();
        else
            if(map[trainHead.getxCoordinate() + 1 ][trainHead.getyCoordinate()] instanceof TrainPassable)
                adjacentTracks.add((TrainTrack) map[trainHead.getxCoordinate() + 1 ][trainHead.getyCoordinate()]);
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
        while(trainAlive)
        {
            switch (currentState)
            {
                case Parked ->
                {
                    if (currentStation.nextInQueue(this) && currentStation.greenLight())
                    {
                        try
                        {
                            Thread.sleep((long)trainSpeed*1000);
                            this.currentState=TrainState.ExitingParking;
                        }
                        catch (Exception ex)
                        {
                            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
                        }
                    }

                }
                case Normal -> {
                    synchronized (Train.class)
                    {
                            try
                            {
                                Thread.sleep(((long)trainSpeed*1000));
                                move();
                            } catch (Exception ex)
                            {
                                Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
                            }
                    }
                }
                case ExitingParking ->
                {
                    synchronized (Train.class)
                    {
                        try
                        {
                            Thread.sleep((long)trainSpeed*1000);
                            exitParking();
                        }
                        catch (Exception ex)
                        {
                            Main.logger.log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    }

                }
            }
        }
    }
    private synchronized void exitParking()
    {
        int newX, newY;
        Locomotive trainHead = (Locomotive) parts.get(0);
        RailroadStation nextStation = null;
        for (var x: stations)
        {
            if (x.getName().equals(nextStationName() + ""))
            {
                nextStation = x;
                break;
            }
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

        if(trainPartsWhichHaveLeftThePlatform==0)
        {
            var front = parts.getFirst();
            front.setxCoordinate(newX); front.setyCoordinate(newY);
            Platform.runLater(() -> map[front.getxCoordinate()][front.getyCoordinate()].putContent(front.toString()));
            trainPartsWhichHaveLeftThePlatform++;
        }

        else
        {
            for (int i = 0; i < trainPartsWhichHaveLeftThePlatform; i--)
            {

                var front = parts.get(trainPartsWhichHaveLeftThePlatform - i - 1);
                var rear = parts.get(trainPartsWhichHaveLeftThePlatform -i);


                //clear the rear tile
                Platform.runLater(() -> map[rear.getxCoordinate()][rear.getyCoordinate()].putContent(""));

                //set taken to false;

                rear.setxCoordinate(front.getxCoordinate());
                rear.setyCoordinate(front.getyCoordinate());

                front.setxCoordinate(newX);
                front.setyCoordinate(newY);
                Platform.runLater(() -> map[front.getxCoordinate()][front.getyCoordinate()].putContent(front.toString()));
                Platform.runLater(() -> map[rear.getxCoordinate()][rear.getyCoordinate()].putContent(rear.toString()));
                trainPartsWhichHaveLeftThePlatform++;
                if(trainPartsWhichHaveLeftThePlatform==parts.size())
                {
                    currentState = TrainState.Normal;
                    break;
                }

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
            if (x.getName().equals(nextStationName()+""))
            {
                nextStation = x;
                break;
            }
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


        for(int i = parts.size();i>1;i--)
        {
            var rear = parts.get(i-1);
            var front = parts.get(i-2);

            //clear the rear tile
            Platform.runLater(() ->
                    {
                        map[rear.getxCoordinate()][rear.getyCoordinate()].putContent("");
                        map[front.getxCoordinate()][front.getyCoordinate()].putContent("");
                    });
            rear.setxCoordinate(front.getxCoordinate());
            rear.setyCoordinate(front.getyCoordinate());
            Platform.runLater(() -> map[rear.getxCoordinate()][rear.getyCoordinate()].putContent(rear.toString()));
        }
        //move the train leading part to new coordinates
        var front = parts.get(0);
        front.setxCoordinate(newX); front.setyCoordinate(newY);
        Platform.runLater(() -> map[front.getxCoordinate()][front.getyCoordinate()].putContent(front.toString()));
    }
}
