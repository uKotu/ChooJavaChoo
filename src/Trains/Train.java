package Trains;

import Main.Main;
import Tiles.StationTile;
import Tiles.Tile;
import Tiles.TrainPassable;
import Tiles.TrainTrackTile;
import Trains.Locomotives.Locomotive;
import Util.Coordinates;
import Util.MovementHistory;
import Util.MovementHistoryUnit;
import Util.RailroadStation;
import javafx.application.Platform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.logging.Level;

public class Train implements Runnable
{
    private final LinkedList<Connectable> parts;
    private final double trainSpeed;
    private final String route;
    private int positionOnRoute;
    private final String trainDescription;
    private RailroadStation currentStation;
    private TrainState currentState;
    private final Tile[][] map;
    private final LinkedList<RailroadStation> stations;
    private boolean trainAlive;
    private int trainPartsWhichHaveLeftThePlatform, trainPartsWhichHaveEnteredThePlatform;
    private final Power powerType;

    private final String movementFolder;
    private final LinkedList<MovementHistoryUnit> movementHistoryList;

    public Train(LinkedList<Connectable> trainPieces, double trainSpeed, String route, Tile[][] map, LinkedList<RailroadStation> stations, String movementFolder, String trainDescription)
    {
        if(trainPieces==null || trainSpeed < 1 || route.equals(""))
            throw new IllegalArgumentException("Cant create a train, illegal arguments given");
        this.trainAlive = true;
        this.parts = trainPieces;
        this.trainSpeed = trainSpeed;
        this.route = route;
        this.currentState = TrainState.Parked;
        this.positionOnRoute = 0;
        this.stations = stations;
        this.trainDescription = trainDescription;
        this.trainPartsWhichHaveLeftThePlatform = 0;
        this.trainPartsWhichHaveEnteredThePlatform = 0;
        this.map = map;
        this.movementHistoryList = new LinkedList<>();
        this.movementFolder = movementFolder;

        this.powerType = ((Locomotive)trainPieces.getFirst()).getPowerType();

        for(var x : stations) //initialize first station
        {
            if(x.getName().equals(route.charAt(0)+""))
            {
                currentStation=x;
                x.addTrainToQueue(this);
                break;
            }
        }

        if(powerType == Power.ELECTRIC)
        {
            this.parts.addFirst(new ElectricBarrier());
            this.parts.addLast(new ElectricBarrier());
        }
    }

    private LinkedList<TrainTrackTile> getAdjacentFreeTracks()
    {
        LinkedList<TrainTrackTile> adjacentTracks = new LinkedList<>();
        Connectable trainLeadingPart = parts.get(0);

        if (currentState==TrainState.Parked || this.trainPartsWhichHaveLeftThePlatform==0)
        {
            //if the train is parked or is just starting to move,
            // its adjacent tracks are the current stations exitTracks
            return currentStation.getPossibleExitCoordinates();
        }
        else
        {
            synchronized (map)
            {
            //check all adjacent tracks to its current position, find ones which are traversable by train and are not taken
            if (map[trainLeadingPart.getxCoordinate() + 1][trainLeadingPart.getyCoordinate()] instanceof TrainPassable
                    && !map[trainLeadingPart.getxCoordinate() + 1][trainLeadingPart.getyCoordinate()].isTaken())
                adjacentTracks.add((TrainTrackTile) map[trainLeadingPart.getxCoordinate() + 1][trainLeadingPart.getyCoordinate()]);

            if (map[trainLeadingPart.getxCoordinate()][trainLeadingPart.getyCoordinate() + 1] instanceof TrainPassable
                    && !map[trainLeadingPart.getxCoordinate()][trainLeadingPart.getyCoordinate() + 1].isTaken())
                adjacentTracks.add((TrainTrackTile) map[trainLeadingPart.getxCoordinate()][trainLeadingPart.getyCoordinate() + 1]);

            if (map[trainLeadingPart.getxCoordinate() - 1][trainLeadingPart.getyCoordinate()] instanceof TrainPassable
                    && !map[trainLeadingPart.getxCoordinate() - 1][trainLeadingPart.getyCoordinate()].isTaken())
                adjacentTracks.add((TrainTrackTile) map[trainLeadingPart.getxCoordinate() - 1][trainLeadingPart.getyCoordinate()]);

            if (map[trainLeadingPart.getxCoordinate()][trainLeadingPart.getyCoordinate() - 1] instanceof TrainPassable
                    && !map[trainLeadingPart.getxCoordinate()][trainLeadingPart.getyCoordinate() - 1].isTaken())
                adjacentTracks.add((TrainTrackTile) map[trainLeadingPart.getxCoordinate()][trainLeadingPart.getyCoordinate() - 1]);
            }
        }
            return adjacentTracks;
    }

    private boolean isStuckInTraffic()
    {
        synchronized (map)
        {
            LinkedList<Tile> adjacentTakenTracks = new LinkedList<>();
            Connectable trainHead = parts.get(0);

            if (!(map[trainHead.getxCoordinate() + 1][trainHead.getyCoordinate()] instanceof StationTile)
                    && map[trainHead.getxCoordinate() + 1][trainHead.getyCoordinate()] != null
                    && map[trainHead.getxCoordinate() + 1][trainHead.getyCoordinate()].isTaken())
                adjacentTakenTracks.add(map[trainHead.getxCoordinate() + 1][trainHead.getyCoordinate()]);

            if (!(map[trainHead.getxCoordinate()][trainHead.getyCoordinate() + 1] instanceof StationTile)
                    && map[trainHead.getxCoordinate()][trainHead.getyCoordinate() + 1] != null
                    && map[trainHead.getxCoordinate()][trainHead.getyCoordinate() + 1].isTaken())
                adjacentTakenTracks.add(map[trainHead.getxCoordinate()][trainHead.getyCoordinate() + 1]);

            if (!(map[trainHead.getxCoordinate() - 1][trainHead.getyCoordinate()] instanceof StationTile)
                    && map[trainHead.getxCoordinate() - 1][trainHead.getyCoordinate()] != null
                    && map[trainHead.getxCoordinate() - 1][trainHead.getyCoordinate()].isTaken())
                adjacentTakenTracks.add(map[trainHead.getxCoordinate() - 1][trainHead.getyCoordinate()]);

            if (!(map[trainHead.getxCoordinate()][trainHead.getyCoordinate() - 1] instanceof StationTile)
                    && map[trainHead.getxCoordinate()][trainHead.getyCoordinate() - 1] != null
                    && map[trainHead.getxCoordinate()][trainHead.getyCoordinate() - 1].isTaken())
                adjacentTakenTracks.add(map[trainHead.getxCoordinate()][trainHead.getyCoordinate() - 1]);

            int numberOfHits = 0;
            for (var track : adjacentTakenTracks)
            {
                for (var part : parts)
                {
                    if (track.getContent().equals(part.toString()))
                    {
                        numberOfHits++;
                    }
                }
            }
            return !(numberOfHits==adjacentTakenTracks.size());
        }

    }

    public char nextStationName()
    {
        try
        {
            if (positionOnRoute + 1 < route.length())
                return route.toCharArray()[positionOnRoute + 1];
        }
        catch(Exception ex)
        {
            Main.logger.log(Level.SEVERE, ex.getMessage(),ex);
        }
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
                    else
                    {
                        try
                        {
                            Thread.sleep(1000);
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
                            synchronized (map)
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
                            synchronized (map)
                            {
                                exitParking();
                            }
                        }
                        catch (Exception ex)
                        {
                            Main.logger.log(Level.SEVERE, ex.getMessage(), ex);
                        }

                }
                case EnteringParking ->
                {
                    try
                    {
                        Thread.sleep((long)trainSpeed*1000);
                        synchronized (map)
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
            movementHistoryList.add(new MovementHistoryUnit(getTrainHeadXCoordinate(),getTrainHeadYCoordinate()));
        }
        serializeMovementHistory();

    }
    /* movement logic

    train acts like a caterpillar:
    the last section takes the coordinates of the section in front of it,
    and so on until we reach the head of the train

    new location of the train leading part is calculated by using its adjacent train tracks
    and moving it to the one whose distance is the closest to its next station
     */


    private synchronized void enterParking()
    {
        int newX, newY;

        RailroadStation nextStation = getNextStation();


        Tile nextTile = nextStation.findClosestEntrance(this);
        newX = nextTile.getxCoordinate(); newY = nextTile.getyCoordinate();

        int oldX, oldY;
        oldX = parts.get(parts.size()-1).getxCoordinate();
        oldY = parts.get(parts.size()-1).getyCoordinate();

        for (int i = parts.size()-1; i > 0; i--)
        {
            var front = parts.get(i-1);
            var rear = parts.get(i);

            rear.setxCoordinate(front.getxCoordinate()); rear.setyCoordinate(front.getyCoordinate());
        }
        Platform.runLater(() ->
        {

            map[oldX][oldY].putContent("");
            if(powerType == Power.ELECTRIC)
            {
                ((TrainPassable)map[oldX][oldY]).setElectricityOff();
            }
        });


        var front = parts.get(trainPartsWhichHaveEnteredThePlatform);
        front.setxCoordinate(newX); front.setyCoordinate(newY);
        trainPartsWhichHaveEnteredThePlatform++;

        // entire train is parked, position it inside the new station
        // restart the parking counters, set it to a new state and exit function
        if(trainPartsWhichHaveEnteredThePlatform == parts.size())
        {
            currentState = TrainState.Parked;

            trainPartsWhichHaveEnteredThePlatform = 0;
            trainPartsWhichHaveLeftThePlatform = 0;
            positionOnRoute++;
            currentStation=nextStation;

            if(positionOnRoute+1 == route.length())// +1 because the train had to start at a certain location
                trainAlive = false;
            if(trainAlive)
                nextStation.addTrainToQueue(this);
            return;
        }

        //train is not yet parked, draw the train parts which have not yet entered the platform
        Platform.runLater(() ->
        {
            for(int i = 0; i < parts.size()-trainPartsWhichHaveEnteredThePlatform;i++)
            {

                map[parts.get(parts.size()-1-i).getxCoordinate()][parts.get(parts.size()-1-i).getyCoordinate()].putContent(parts.get(parts.size()-1-i).toString());
            }
        });


    }
    private synchronized void exitParking()
    {
        int newX, newY;

        RailroadStation nextStation = getNextStation();


        HashMap<Integer, Tile> tileDistanceMap = new HashMap<>();
        for (var track : getAdjacentFreeTracks())
        {
            int distance = Coordinates.calculateDistance(
                    new Coordinates(track.getxCoordinate(), track.getyCoordinate()),
                    new Coordinates(nextStation.getxCoordinate(), nextStation.getyCoordinate()));
            tileDistanceMap.put(distance, track);
        }
        if (tileDistanceMap.size() == 0)
        {
            //if there are no empty tracks to move the train to
            //slow down, aka wait
            return;
        }
        var nextTileValueEntry = Collections.min(tileDistanceMap.entrySet(), Map.Entry.comparingByKey());
        var nextTile = nextTileValueEntry.getValue();
        newX = nextTile.getxCoordinate();
        newY = nextTile.getyCoordinate();


        for (int i = trainPartsWhichHaveLeftThePlatform; i > 0; i--)
        {
            var front = parts.get(i - 1);
            var rear = parts.get(i);

            // why wasnt it working?
            // Platform.runLater(() -> map[rear.getxCoordinate()][rear.getyCoordinate()].putContent(""));
            rear.setxCoordinate(front.getxCoordinate());
            rear.setyCoordinate(front.getyCoordinate());
            Platform.runLater(() ->
            {
                map[rear.getxCoordinate()][rear.getyCoordinate()].putContent(rear.toString());
                if (powerType == Power.ELECTRIC)
                {
                    ((TrainPassable) map[rear.getxCoordinate()][rear.getyCoordinate()]).setElectricityOn();
                }
            });
        }

        parts.getFirst().setxCoordinate(newX);
        parts.getFirst().setyCoordinate(newY);
        Platform.runLater(() ->
        {
            map[parts.getFirst().getxCoordinate()][parts.getFirst().getyCoordinate()].putContent(parts.getFirst().toString());
            if(powerType== Power.ELECTRIC)
            {
                ((TrainPassable) map[parts.getFirst().getxCoordinate()][parts.getFirst().getyCoordinate()]).setElectricityOn();
            }
        });
        trainPartsWhichHaveLeftThePlatform++;

        if(trainPartsWhichHaveLeftThePlatform==parts.size())
        {
            currentState = TrainState.Normal;
        }
    }

    private synchronized void move()
    {
        int newX, newY;
        RailroadStation nextStation = getNextStation();

        if(getAdjacentFreeTracks().size()==0)
        {
            //if there are no adjacent free tracks and the train is not stuck in traffic
            //that means that it must be entering parking -> change its state
            if(isStuckInTraffic())
            {
                //if it is stuck in traffic, wait
                return;
            }
            currentState = TrainState.EnteringParking;
            return;
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

            oldX= rear.getxCoordinate(); oldY = rear.getyCoordinate();
            rear.setxCoordinate(front.getxCoordinate()); rear.setyCoordinate(front.getyCoordinate());

            Platform.runLater(() ->
            {
                map[oldX][oldY].putContent("");
                if(powerType == Power.ELECTRIC)
                {
                    ((TrainPassable)map[oldX][oldY]).setElectricityOff();
                }
            });

        }

        //move the train leading part to new coordinates
        var front = parts.get(0);
        front.setxCoordinate(newX); front.setyCoordinate(newY);
        Platform.runLater(() ->
        {
            for(var part :parts)
            {
                //draw all train parts
                map[part.getxCoordinate()][part.getyCoordinate()].putContent(part.toString());
                //((TrainPassable) map[part.getxCoordinate()][part.getyCoordinate()]).setElectricityOn();
                if(powerType == Power.ELECTRIC)
                {
                    ((TrainPassable) map[part.getxCoordinate()][part.getyCoordinate()]).setElectricityOn();
                }
            }
        });

    }

    private RailroadStation getNextStation()
    {
        RailroadStation nextStation = null;
        for (var x: stations)
        {
            if (x.getName().equals(nextStationName() + ""))
            {
                nextStation = x;
                break;
            }
        }
        return nextStation;
    }
    public int getTrainHeadXCoordinate()
    {
        return parts.get(0).getxCoordinate();
    }
    public int getTrainHeadYCoordinate()
    {
        return parts.get(0).getyCoordinate();
    }

    private void serializeMovementHistory()
    {
        try
        {
            String newFileName = movementFolder + "\\"  + this.trainDescription;
            File file = new File(newFileName);
            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(newFileName);
            ObjectOutputStream streamOut = new ObjectOutputStream(fout);

            MovementHistory movementHistory  = new MovementHistory(movementHistoryList, this.trainDescription);
            streamOut.writeObject(movementHistory);
            streamOut.flush();
            streamOut.close();

        }
        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
        }
    }
    public int getTrainLength()
    {
        return parts.size();
    }
    public LinkedList<MovementHistoryUnit> getMovementHistoryList()
    {
        return movementHistoryList;
    }
}
