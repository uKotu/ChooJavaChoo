package Trains;

import Main.Main;
import Tiles.Tile;
import Tiles.TrainPassable;
import Tiles.TrainTrack;
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
    int trainPartsWhichHaveLeftThePlatform, trainPartsWhichHaveEnteredThePlatform;

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
        trainPartsWhichHaveEnteredThePlatform = 0;
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

    private List<TrainTrack> getAdjacentFreeTracks()
    {
        LinkedList<TrainTrack> adjacentTracks = new LinkedList<>();
        Locomotive trainHead = (Locomotive) parts.get(0); //first part of the train must be a locomotive
        if (currentState==TrainState.Parked || this.trainPartsWhichHaveLeftThePlatform==0) //if the train is parked, its adjacent tracks are the stations exitTracks
            return currentStation.getPossibleExitCoordinates();
        else
            if(map[trainHead.getxCoordinate() + 1 ][trainHead.getyCoordinate()] instanceof TrainPassable && !map[trainHead.getxCoordinate() + 1 ][trainHead.getyCoordinate()].isTaken())
                adjacentTracks.add((TrainTrack) map[trainHead.getxCoordinate() + 1 ][trainHead.getyCoordinate()]);
            if(map[trainHead.getxCoordinate()][trainHead.getyCoordinate() + 1] instanceof TrainPassable && !map[trainHead.getxCoordinate()][trainHead.getyCoordinate() + 1].isTaken())
                adjacentTracks.add((TrainTrack)map[trainHead.getxCoordinate()][trainHead.getyCoordinate() + 1]);
            if(map[trainHead.getxCoordinate() - 1][trainHead.getyCoordinate()] instanceof TrainPassable && !map[trainHead.getxCoordinate() - 1][trainHead.getyCoordinate()].isTaken())
                adjacentTracks.add((TrainTrack)map[trainHead.getxCoordinate() - 1][trainHead.getyCoordinate()]);
            if(map[trainHead.getxCoordinate()][trainHead.getyCoordinate() - 1] instanceof TrainPassable && !map[trainHead.getxCoordinate()][trainHead.getyCoordinate() - 1].isTaken())
                adjacentTracks.add((TrainTrack)map[trainHead.getxCoordinate()][trainHead.getyCoordinate() - 1]);

            return adjacentTracks;
    }



    public char nextStationName() throws ArrayIndexOutOfBoundsException
    {
        if(positionOnRoute+1<route.length())
            return route.toCharArray()[positionOnRoute + 1];
        else
            return '!';
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
                            this.currentState=TrainState.ExitingParking;
                        }
                        catch (Exception ex)
                        {
                            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
                        }
                    }

                }
                case Normal ->
                {
                        try
                        {
                            Thread.sleep(((long)trainSpeed*1000));
                            synchronized (Train.class)
                            {
                                move();
                            }
                        }
                        catch (Exception ex)
                        {
                            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
                        }

                }
                case ExitingParking ->
                {
                        try
                        {
                            Thread.sleep((long)trainSpeed*1000);
                            synchronized (Train.class)
                            {
                                exitParking();
                            }
                        }
                        catch (Exception ex)
                        {
                            Main.logger.log(Level.SEVERE, ex.getMessage(), ex);
                        }

                }
                case EnteringParking -> //must restart number of parked parts, increment routePos
                {
                    try
                    {
                        Thread.sleep((long)trainSpeed*1000);
                        synchronized (Train.class)
                        {
                            enterParking();
                        }
                    }
                    catch (Exception ex)
                    {
                        Main.logger.log(Level.SEVERE, ex.getMessage(), ex);
                    }

                }
            }
        }
    }
    private synchronized  void enterParking()
    {
        int newX, newY;

        RailroadStation nextStation = null;
        for (var x: stations)
        {
            if (x.getName().equals(nextStationName() + ""))
            {
                nextStation = x;
                break;
            }
        }


        Tile nextTile = nextStation.findClosestEntrance(this);
        newX = nextTile.getxCoordinate(); newY = nextTile.getyCoordinate();

        int oldX, oldY;
        oldX = parts.get(parts.size()-1).getxCoordinate();
        oldY = parts.get(parts.size()-1).getyCoordinate();

        for (int i = parts.size()-1; i > 0; i--)
        {
            var front = parts.get(i-1);
            var rear = parts.get(i);

            //int oldX, oldY;
            //Platform.runLater(() -> map[rear.getxCoordinate()][rear.getyCoordinate()].putContent(""));
           // oldX = rear.getxCoordinate(); oldY = rear.getyCoordinate();
            rear.setxCoordinate(front.getxCoordinate()); rear.setyCoordinate(front.getyCoordinate());

            //Platform.runLater(() -> map[oldX][oldY].putContent(""));
        }
        Platform.runLater(() -> map[oldX][oldY].putContent(""));

        var front = parts.get(trainPartsWhichHaveEnteredThePlatform);
        front.setxCoordinate(newX); front.setyCoordinate(newY);
        trainPartsWhichHaveEnteredThePlatform++;

        if(trainPartsWhichHaveEnteredThePlatform == parts.size())
        {
            currentState = TrainState.Parked;
            nextStation.addTrainToQueue(this);
            trainPartsWhichHaveEnteredThePlatform = 0;
            trainPartsWhichHaveLeftThePlatform = 0;
            positionOnRoute++;
            currentStation=nextStation;

            if(positionOnRoute+1 == route.length())//+1 because the train started at a certain location
                trainAlive = false;
            return;
        }
        Platform.runLater(() ->
        {
            for(int i = 0; i < parts.size()-trainPartsWhichHaveEnteredThePlatform;i++)
            {
                //draw all trainparts
                map[parts.get(parts.size()-1-i).getxCoordinate()][parts.get(parts.size()-1-i).getyCoordinate()].putContent(parts.get(parts.size()-1-i).toString());
            }
        });


    }
    private synchronized void exitParking()
    {
        int newX, newY;

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
        for (var track : getAdjacentFreeTracks())
        {
            int distance = Coordinates.calculateDistance(
                    new Coordinates(track.getxCoordinate(),track.getyCoordinate()),
                    new Coordinates(nextStation.getxCoordinate(), nextStation.getyCoordinate()));
            tileDistanceMap.put(distance,track);
        }
        var nextTileValueEntry = Collections.min(tileDistanceMap.entrySet(), Map.Entry.comparingByKey());
        var nextTile = nextTileValueEntry.getValue();
        newX = nextTile.getxCoordinate(); newY = nextTile.getyCoordinate();


        for (int i = trainPartsWhichHaveLeftThePlatform; i > 0; i--)
        {
            var front = parts.get(i-1);
            var rear = parts.get(i);

            //Platform.runLater(() -> map[rear.getxCoordinate()][rear.getyCoordinate()].putContent(""));
            rear.setxCoordinate(front.getxCoordinate()); rear.setyCoordinate(front.getyCoordinate());
            Platform.runLater(() -> map[rear.getxCoordinate()][rear.getyCoordinate()].putContent(rear.toString()));
        }

        parts.getFirst().setxCoordinate(newX); parts.getFirst().setyCoordinate(newY);
        Platform.runLater(() -> map[parts.getFirst().getxCoordinate()][parts.getFirst().getyCoordinate()].putContent(parts.getFirst().toString()));
        trainPartsWhichHaveLeftThePlatform++;

        if(trainPartsWhichHaveLeftThePlatform==parts.size())
        {
            currentState = TrainState.Normal;

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
        var freeAdjacentTracks = getAdjacentFreeTracks();
        if(freeAdjacentTracks.size()==0)
        {
            currentState = TrainState.EnteringParking;
            return;
        }
        for (var track : getAdjacentFreeTracks())
        {
            int distance = Coordinates.calculateDistance(
                    new Coordinates(track.getxCoordinate(),track.getyCoordinate()),
                    new Coordinates(nextStation.getxCoordinate(), nextStation.getyCoordinate()));
            tileDistanceMap.put(distance,track);
        }
        var nextTileValueEntry = Collections.min(tileDistanceMap.entrySet(), Map.Entry.comparingByKey());
        var nextTile = nextTileValueEntry.getValue();

        newX = nextTile.getxCoordinate(); newY = nextTile.getyCoordinate();
        if(nextStation.getxCoordinate()==newX && nextStation.getyCoordinate()==newY)
        {
            //we have reached the next station, transfer control to enteringParking function
            currentState = TrainState.EnteringParking;
            return;
        }


        for (int i = parts.size()-1; i > 0; i--)
        {
            var front = parts.get(i-1);
            var rear = parts.get(i);

            int oldX, oldY;
            //Platform.runLater(() -> map[rear.getxCoordinate()][rear.getyCoordinate()].putContent(""));
            oldX= rear.getxCoordinate(); oldY = rear.getyCoordinate();
            rear.setxCoordinate(front.getxCoordinate()); rear.setyCoordinate(front.getyCoordinate());

            Platform.runLater(() -> map[oldX][oldY].putContent(""));
        }

        //move the train leading part to new coordinates
        var front = parts.get(0);
        front.setxCoordinate(newX); front.setyCoordinate(newY);
        Platform.runLater(() ->
        {
            for(var x :parts)
            {
                //draw all trainparts
                map[x.getxCoordinate()][x.getyCoordinate()].putContent(x.toString());
            }
        });

    }

    public int getTrainHeadXCoordinate()
    {
        return parts.get(0).getxCoordinate();
    }
    public int getTrainHeadYCoordinate()
    {
        return parts.get(0).getyCoordinate();
    }
}
