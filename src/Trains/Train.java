package Trains;

import Tiles.Tile;
import Trains.Locomotives.Locomotive;
import Util.Coordinates;
import Util.RailroadStation;

import java.util.LinkedList;

public class Train implements Runnable
{
    LinkedList<Connectable> parts;
    double trainSpeed;
    String route;
    RailroadStation startingPosition;
    TrainState currentState;
    Tile[][] map;

    public Train(LinkedList<Connectable> trainPieces, double trainSpeed, String route, Tile[][] map, LinkedList<RailroadStation> stations)
    {
        parts = trainPieces;
        this.trainSpeed = trainSpeed;
        this.route = route;
        currentState = TrainState.Parked;

        for(var x : stations) //initialize first station
        {
            if(x.getName()==(route.charAt(0)+""))
            {
                startingPosition=x;
                break;

            }
        }





    }

    @Override
    public void run()
    {
        switch (currentState)
        {
            case Parked ->
                    {
                        synchronized (this.startingPosition)
                        {

                        }
                    }
        }

    }
}
