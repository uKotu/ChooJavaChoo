package Util;

import Tiles.Tile;
import Trains.Train;

import java.util.LinkedList;

public class RailwayCrossing
{
    private LinkedList<Train> trains;
    private final RailPath railpath;
    private final LinkedList<Tile> tilesTaken;
    private final int carTrackNumber;

    public RailwayCrossing(RailPath railpath, LinkedList<Tile> tilesTaken, int carTrackNumber)
    {
        this.railpath = railpath;
        this.tilesTaken = tilesTaken;
        this.carTrackNumber = carTrackNumber;
    }

    public boolean isGreenLight()
    {
        return railpath.isPathClear() || noIncomingTrains();

    }

    public LinkedList<Tile> getTilesTaken()
    {
        return tilesTaken;
    }

    boolean noIncomingTrains()
    {
        LinkedList<Train> trainsOnRailPath = new LinkedList<>();

        synchronized (railpath.map)
        {
            for (Train train : trains)
            {
                //if the train is on our railpath
                if (railpath.containsTile(railpath.map[train.getTrainHeadXCoordinate()][train.getTrainHeadYCoordinate()]))
                {
                    trainsOnRailPath.add(train);
                }
            }

            for(Train train : trainsOnRailPath)
            {
                //for each train on the railpath we get the number of tiles it has passed in the current railpath
                int numberOfTilesCrossedInCurrentRailPath = 0;
                for(int i = train.getMovementHistoryList().size()-1; i>0; i--)
                {
                    if(railpath.containsTile(railpath.map[train.getMovementHistoryList().get(i).getxCoordinate()][train.getMovementHistoryList().get(i).getyCoordinate()]))
                        numberOfTilesCrossedInCurrentRailPath++;
                    else
                        break;
                }


                boolean hasPassedTheCrossing = false;

                for(int i = 0;i<numberOfTilesCrossedInCurrentRailPath;i++)
                {
                    //we check has the entire section of the train passed the railway crossing
                    int sizeOfList = train.getMovementHistoryList().size() -1;//because the last position is at the end of the movement history list
                    if (tilesTaken.contains(railpath.map[train.getMovementHistoryList().get(sizeOfList-i).getxCoordinate()][train.getMovementHistoryList().get(sizeOfList-i).getyCoordinate()]))
                    {
                        if (i < train.getTrainLength())
                            return false;
                        hasPassedTheCrossing = true;
                    }
                }
                if(hasPassedTheCrossing)
                    continue;
                return false;

            }

            return true;
        }
    }

    public void giveTrainInfo(LinkedList<Train> trains)
    {
        this.trains = trains;
    }

}
